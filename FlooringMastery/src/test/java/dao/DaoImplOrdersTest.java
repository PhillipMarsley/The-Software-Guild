/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Product;
import model.State;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dan
 */
public class DaoImplOrdersTest {

    private static final String DELIMITER = ",";

    private final DaoImplOrders dio = new DaoImplOrders();
    //sets 100 years in the future
    private final LocalDate date = LocalDate.now().plusYears(100);
    private final String dateKey = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));

    private final String fileNameA = "data"
	    + File.separator + "orders"
	    + File.separator + "Orders_" + dateKey + ".txt";

    private final File fileA = new File(fileNameA);

    private Order order1 = new Order();
    private Order order2 = new Order();
    private Order order3 = new Order();

    public DaoImplOrdersTest() {
    }

    private Order createOrder() {
	Order order = new Order();
	State state = new State();
	Product product = new Product();

	order.setOrderNumber(0);
	order.setCustomerName("Phill");
	state.setName("MN");
	state.setTaxRate(new BigDecimal("7.125"));
	product.setType("wood");
	order.setArea(new BigDecimal("100"));
	product.setCostPerSqFtMaterial(new BigDecimal("1.11"));
	product.setCostPerSqFtLabor(new BigDecimal("2.22"));
	order.setTotalCostMaterial(new BigDecimal("3.33"));
	order.setTotalCostLabor(new BigDecimal("4.44"));
	order.setTotalCostTax(new BigDecimal("5.55"));
	order.setOrderTotal(new BigDecimal("6.66"));

	order.setState(state);
	order.setProduct(product);

	return order;
    }

    @Before
    public void setUp() {
	int orderNumber = 9000;
	//int orderNumber = 9001;
	//int orderNumber = 9002;

	order1 = createOrder();
	order2 = createOrder();
	order3 = createOrder();

	order1.setOrderNumber(orderNumber + 0);
	order2.setOrderNumber(orderNumber + 1);
	order3.setOrderNumber(orderNumber + 2);

	dio.getOrdersListForToday().add(order1);
	dio.getOrdersListForToday().add(order2);
	dio.getOrdersListForToday().add(order3);

	dio.getAllOrders().put(dateKey, dio.getOrdersListForToday());
    }

    @After
    public void tearDown() {
	dio.getOrdersListForToday().clear();
	dio.getAllOrders().remove(dateKey);
	fileA.delete();
    }

    @Test
    public void testAddOrder() {
	assertTrue(dio.getOrdersListForToday().contains(order1));
	assertTrue(dio.getOrdersListForToday().contains(order2));
	assertTrue(dio.getOrdersListForToday().contains(order3));

	assertTrue(dio.getAllOrders().get(dateKey).contains(order1));
	assertTrue(dio.getAllOrders().get(dateKey).contains(order2));
	assertTrue(dio.getAllOrders().get(dateKey).contains(order3));

	Order orderA = createOrder();
	Order orderB = createOrder();
	Order orderC = createOrder();

	try {
	    //added order1, order2, and order3
	    //	line 1	line 2	    line 3 of file
	    dio.addOrder(orderA);
	    dio.addOrder(orderB);
	    dio.addOrder(orderC);
	}
	catch (DaoPersistanceException ex) {
	    System.out.println("Unable to load file");
	}

	assertFalse(dio.getOrdersListForToday().isEmpty());
	
	assertEquals(4, orderA.getOrderNumber());
	assertEquals(5, orderB.getOrderNumber());
	assertEquals(6, orderC.getOrderNumber());
    }

    @Test
    public void testRemoveOrder() {
	String removeOrderKey = dio.getRemoveOrderKey();
	String order1OldName = order1.getCustomerName();
	String order2OldName = order2.getCustomerName();
	String order3OldName = order3.getCustomerName();

	try {
	    dio.removeOrder(date, order1.getOrderNumber());
	    dio.removeOrder(date, order2.getOrderNumber());
	    dio.removeOrder(date, order3.getOrderNumber());
	}
	catch (DaoPersistanceException ex) {
	    System.out.println("Unable to remove order");
	}

	assertTrue(dio.getAllOrders().get(dateKey).get(0).getCustomerName()
		.equals(removeOrderKey + order1OldName));
	assertTrue(dio.getAllOrders().get(dateKey).get(1).getCustomerName()
		.equals(removeOrderKey + order2OldName));
	assertTrue(dio.getAllOrders().get(dateKey).get(2).getCustomerName()
		.equals(removeOrderKey + order3OldName));
    }

    @Test
    public void testGetOrder() {
	assertEquals(order1, dio.getAllOrders().get(dateKey).get(0));
	assertEquals(order2, dio.getAllOrders().get(dateKey).get(1));
	assertEquals(order3, dio.getAllOrders().get(dateKey).get(2));
    }

    @Test
    public void testGetOrders() {
	List<Order> tempList = new ArrayList();
	tempList.add(order1);
	tempList.add(order2);
	tempList.add(order3);

	assertEquals(tempList, dio.getAllOrders().get(dateKey));
    }

    @Test
    public void testSaveAll() {
	try {
	    dio.saveAll();
	}
	catch (DaoPersistanceException ex) {
	    System.out.println("Could not write to file");
	}

	assertTrue(dio.getAllOrders().size() > 0);

	for (String key : dio.getAllOrders().keySet()) {
	    //build file path
	    String filePath = "Orders_" + key + ".txt";

	    //collect files
	    List<String> listOfFiles = new ArrayList<>();
	    File folder = new File("data" + File.separator + "orders");
	    File[] files = folder.listFiles();

	    for (File f : files) {
		listOfFiles.add(f.getName());
	    }

	    //check if file exists
	    boolean exists = false;

	    for (int i = 0; i < listOfFiles.size(); i++) {
		if (listOfFiles.get(i).equals(filePath)) {
		    exists = true;
		}
	    }
	    assertTrue(exists);
	}

    }

}
