/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DaoImplOrders;
import dao.DaoImplProducts;
import dao.DaoImplTaxes;
import dao.DaoPersistanceException;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Order;
import model.Product;
import model.State;

/**
 *
 * @author Dan
 */
public class Service {

    private final DaoImplOrders dio;
    private final DaoImplProducts dip;
    private final DaoImplTaxes dit;
    private final String mode;

    public Service(DaoImplOrders dio, DaoImplProducts dip, DaoImplTaxes dit, String mode) {
	this.dio = dio;
	this.dip = dip;
	this.dit = dit;
	this.mode = mode;
    }
    
    public void replaceOrderInList(LocalDate date, Order order1, Order order2) throws DaoPersistanceException {
	Map<String, List<Order>> map = dio.getAllOrders();
	
	//do caculations on newly edited order
	order2 = caculateOrder(order2);
	
	String dateKey = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
	
	int index = map.get(dateKey).indexOf(order1);
	map.get(dateKey).remove(index);
	map.get(dateKey).add(order2);
    }
    
    private Order caculateOrder(Order order) throws DaoPersistanceException {
	//aquired tax information
	String stateName = order.getState().getName();
	State state = dit.getState(stateName);

	order.setState(state);

	//aquires product information
	String productType = order.getProduct().getType();
	Product product = dip.getProduct(productType);

	order.setProduct(product);

	//caculates cost fields
	order.getState().setTaxRate(convertTaxRateToDecimal(order));
	order.setTotalCostMaterial(caculateTotalCostMaterial(order));
	order.setTotalCostLabor(caculateTotalCostLabor(order));
	order.setTotalCostTax(caculateTotalTax(order));
	order.setOrderTotal(caculateTotal(order));
	order.getState().setTaxRate(convertTaxRateToPercent(order));
	
	return order;
    }

    public Order addOrder(Order order) throws DaoPersistanceException {
	
	order = caculateOrder(order);
	
	return dio.addOrder(order);
    }

    public Order removeOrder(LocalDate date, int orderNumber) throws DaoPersistanceException {	
	return dio.removeOrder(date, orderNumber);
    }

    public Order getOrder(LocalDate date, int orderNumber) throws DaoPersistanceException {
	return dio.getOrder(date, orderNumber);
    }

    public List<Order> getOrders(LocalDate date) throws DaoPersistanceException, DataValidationException {
	if(checkDateExists(date) == false) {
	    throw new DataValidationException("Date not found.");
	}
	
	return dio.getOrders(date);
    }

    public void save() throws DaoPersistanceException {
	if(mode.equalsIgnoreCase("Training")) {
	    throw new DaoPersistanceException("Training Mode active. Data will not be saved.");
	}
	dio.saveAll();
    }

    //==========================================================================
    //		Math Caculations
    //==========================================================================
    /*
    //taxRate
    //area
    //costPerSquareFootMaterial
    //costPerSquareFootLabor
    //totalCostMaterial
    //totalCostLabor
    //total
     */
    private BigDecimal convertTaxRateToDecimal(Order order) {
	return order.getState().getTaxRate().movePointLeft(2);
    }

    private BigDecimal convertTaxRateToPercent(Order order) {
	return order.getState().getTaxRate().movePointRight(2);
    }

    private BigDecimal caculateTotalCostMaterial(Order order) {
	return order.getArea().multiply(order.getProduct().getCostPerSqFtMaterial());
    }

    private BigDecimal caculateTotalCostLabor(Order order) {
	return order.getArea().multiply(order.getProduct().getCostPerSqFtLabor());
    }

    private BigDecimal caculateTotalTax(Order order) {
	BigDecimal totalTax = new BigDecimal("0");

	totalTax = totalTax.add(caculateTotalCostMaterial(order));
	totalTax = totalTax.add(caculateTotalCostLabor(order));
	totalTax = totalTax.multiply(order.getState().getTaxRate());

	return totalTax;
    }

    private BigDecimal caculateTotal(Order order) {
	BigDecimal total = BigDecimal.ZERO;

	total = total.add(caculateTotalCostMaterial(order));
	total = total.add(caculateTotalCostLabor(order));
	total = total.add(caculateTotalTax(order));

	return total;
    }

    //==========================================================================
    //		Helper methods
    //==========================================================================
    /*
    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    
    private State state;
    private Product product;
     */
    
    public List<State> getStates() throws DaoPersistanceException {
	return dit.getStates();
    }
    
    public List<Product> getProducts() throws DaoPersistanceException {
	return dip.getProducts();
    }
    
    public void checkStateExists(String name) throws DaoPersistanceException{
	dit.getState(name);
    }

    public void checkProductExists(String type) throws DaoPersistanceException{
	dip.getProduct(type);
    }

    public boolean checkDateExists(LocalDate date) {
	List<String> listOfFiles = new ArrayList<>();

	File folder = new File("data" + File.separator + "orders");
	File[] files = folder.listFiles();

	for (File f : files) {
	    listOfFiles.add(f.getName());
	}

	List<LocalDate> listOfDates = listOfFiles.stream()
		.map((s) -> s.substring(s.indexOf("_") + 1, s.indexOf(".")))
		.map((s) -> LocalDate.parse(s, DateTimeFormatter.ofPattern("MMddyyyy")))
		.collect(Collectors.toList());

	if (listOfDates.stream()
		.anyMatch(d -> d.equals(date))) {
	  return true;
	}
	
	return date.equals(LocalDate.now())
		&& dio.getOrdersListForToday().size() > 0;
    }  
}
