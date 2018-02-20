/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import model.Order;
import model.Product;
import model.State;

/**
 *
 * @author Dan
 */
public class DaoImplOrders implements DaoOrders {

    private static final String todaysDate = LocalDateTime.now()
	    .toLocalDate()
	    .format(DateTimeFormatter.ofPattern("MMddyyyy"));

    static final String FILE_NAME_FOR_TODAY = "data"
	    + File.separator + "orders"
	    + File.separator + "Orders_" + todaysDate + ".txt";

    private static final String DELIMITER = ",";
    private static final String REMOVE_ORDER_KEY = "[CANCELED] ";

    private Map<String, List<Order>> allOrders = new TreeMap<>();
    private List<Order> ordersListForToday = new ArrayList<>();

    public DaoImplOrders() {
    }

    //==========================================================================
    //	    Getters and Setters
    //==========================================================================
    public String getRemoveOrderKey() {
	return REMOVE_ORDER_KEY;
    }

    public Map<String, List<Order>> getAllOrders() {
	return allOrders;
    }

    public List<Order> getOrdersListForToday() {
	return ordersListForToday;
    }

    //==========================================================================
    //	    Overrides
    //==========================================================================
    @Override
    public Order addOrder(Order order) throws DaoPersistanceException {
	String dateKey = parseFileNameForDate(FILE_NAME_FOR_TODAY);
	readFile(FILE_NAME_FOR_TODAY);

	ordersListForToday.add(order);
	allOrders.put(dateKey, ordersListForToday);

	int orderNumber = allOrders.get(dateKey).size();
	order.setOrderNumber(orderNumber);

	return order;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws DaoPersistanceException {
	String dateKey = convertDateToString(date);
	readFile(buildFileName(dateKey));

	for (Order o : allOrders.get(dateKey)) {
	    if (o.getOrderNumber() == orderNumber) {
		o.setCustomerName(REMOVE_ORDER_KEY + o.getCustomerName());
		return o;
	    }
	}
	throw new DaoPersistanceException("Unable to find order");
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws DaoPersistanceException {
	String dateKey = convertDateToString(date);
	readFile(buildFileName(dateKey));

	Optional<Order> result = getOrders(date).stream()
		.filter(o -> !o.getCustomerName().contains(REMOVE_ORDER_KEY))
		.filter(o -> o.getOrderNumber() == orderNumber)
		.findFirst();

	Order order = result.get();

	if (order != null) {
	    return order;
	}
	throw new DaoPersistanceException("Order not found in file");
    }

    @Override
    public List<Order> getOrders(LocalDate date) throws DaoPersistanceException {
	String dateKey = convertDateToString(date);
	readFile(buildFileName(dateKey));

	List<Order> filteredList = allOrders.get(dateKey).stream()
		.filter(o -> !o.getCustomerName().contains(REMOVE_ORDER_KEY))
		.collect(Collectors.toList());

	return filteredList;
    }

    //==========================================================================
    //	    File Reading
    //==========================================================================
    private void readFile(String fileName) throws DaoPersistanceException {
	Scanner scanner;
	File file = new File(fileName);
	List<Order> orders = new ArrayList();

	int currentLineNum = 0;

	//if the file doesn't exist, we don't want to read it
	if (file.exists() == false) {
	    return;
	}

	try {
	    scanner = new Scanner(new FileReader(fileName));

	    //skips the first line of the file
	    //first line of file is 'catagory/column' names
	    scanner.nextLine();
	    currentLineNum++;

	    while (scanner.hasNext()) {
		String line = scanner.nextLine();
		String[] lineParts = line.split(DELIMITER);

		Order order = new Order();
		State state = new State();
		Product product = new Product();

		order.setOrderNumber(Integer.parseInt(lineParts[0]));
		order.setCustomerName(lineParts[1]);
		state.setName(lineParts[2]);
		state.setTaxRate(new BigDecimal(lineParts[3]));
		product.setType(lineParts[4]);
		order.setArea(new BigDecimal(lineParts[5]));
		product.setCostPerSqFtMaterial(new BigDecimal(lineParts[6]));
		product.setCostPerSqFtLabor(new BigDecimal(lineParts[7]));
		order.setTotalCostMaterial(new BigDecimal(lineParts[8]));
		order.setTotalCostLabor(new BigDecimal(lineParts[9]));
		order.setTotalCostTax(new BigDecimal(lineParts[10]));
		order.setOrderTotal(new BigDecimal(lineParts[11]));

		order.setState(state);
		order.setProduct(product);
		orders.add(order);

		currentLineNum++;
	    }

	    scanner.close();
	}
	catch (FileNotFoundException ex) {
	    String message = "Could not load: \n"
		    + fileName;

	    throw new DaoPersistanceException(message);
	}
	catch (NumberFormatException ex) {
	    String message = "Monitary Data Unable to be parsed in\n"
		    + fileName
		    + "\non line "
		    + currentLineNum;

	    throw new DaoPersistanceException(message);
	}
	catch (IndexOutOfBoundsException ex) {
	    String message = "Unable to be parsed correctly in \n"
		    + fileName
		    + "\non line "
		    + currentLineNum;

	    throw new DaoPersistanceException(message);
	}

	String dateKey = parseFileNameForDate(fileName);

	//check if my list of every orders contains the same date
	//that I am trying to put in
	if (allOrders.containsKey(dateKey)) {

	    //store each the order lists to use
	    Iterator itr1 = allOrders.get(dateKey).iterator();
	    Iterator itr2 = orders.iterator();

	    //compare to the two order's order numbers
	    while (itr1.hasNext()) {
		while (itr2.hasNext()) {
		    Order order1 = (Order) itr1.next();
		    Order order2 = (Order) itr2.next();
		    if (order1.getOrderNumber() != order2.getOrderNumber()) {
			allOrders.get(dateKey).add(order2);
		    }
		}
	    }
	}
	else {
	    allOrders.put(dateKey, orders);
	}
    }

    //==========================================================================
    //	    File Writing
    //==========================================================================
    public void saveAll() throws DaoPersistanceException {
	PrintWriter writer;
	String fileName = "";

	try {
	    for (String key : allOrders.keySet()) {

		fileName = buildFileName(key);
		writer = new PrintWriter(new File(fileName));
		writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

		for (Order order : allOrders.get(key)) {

		    String line = order.getOrderNumber()
			    + DELIMITER + order.getCustomerName()
			    + DELIMITER + order.getState().getName()
			    + DELIMITER + order.getState().getTaxRate()
			    + DELIMITER + order.getProduct().getType()
			    + DELIMITER + order.getArea()
			    + DELIMITER + order.getProduct().getCostPerSqFtMaterial()
			    + DELIMITER + order.getProduct().getCostPerSqFtLabor()
			    + DELIMITER + order.getTotalCostMaterial()
			    + DELIMITER + order.getTotalCostLabor()
			    + DELIMITER + order.getTotalCostTax()
			    + DELIMITER + order.getOrderTotal();

		    writer.println(line);
		    writer.flush();
		}

		writer.close();
	    }
	}
	catch (FileNotFoundException ex) {
	    throw new DaoPersistanceException("Could not find \n" + fileName);
	}
    }

    //==========================================================================
    //		Helper methods
    //==========================================================================
    private String convertDateToString(LocalDate date) {
	return date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    private String buildFileName(String date) {
	String mainPath = "data"
		+ File.separator + "orders"
		+ File.separator + "Orders_";
	String dateKey = date;
	String extention = ".txt";

	return mainPath + dateKey + extention;
    }

    private String parseFileNameForDate(String fileName) {
	//dd MM YYYY = 8
	int charPosition = fileName.indexOf('_') + 1;
	return fileName.substring(charPosition, charPosition + 8);
    }
}
