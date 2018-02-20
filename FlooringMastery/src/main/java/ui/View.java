/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Order;
import model.Product;

/**
 *
 * @author Dan
 */
public class View {

    UserIO io;

    public View(UserIO io) {
	this.io = io;
    }

    //==========================================================================
    //	    Helper Methods
    //==========================================================================
    private void createBanner(String string) {
	int paddingSize = 10;
	int borderLength = (paddingSize * 2) + string.length() + 1;
	borderLength /= 2;

	String side = "|";
	String border = "";
	String paddingAroundString = "";

	border += " ";
	for (int i = 0; i < borderLength; i++) {
	    border += "- ";
	}

	for (int i = 0; i < paddingSize; i++) {
	    paddingAroundString += " ";
	}

	io.print(border);
	io.print(side
		+ paddingAroundString
		+ string
		+ paddingAroundString
		+ side);
	io.print(border);
    }

    //==========================================================================
    //	    General Methods
    //==========================================================================
    public LocalDate getInputDate() {
	io.print("Format:  month, day, year");
	io.print("Example: 01/11/2018");
	String inputtedDate = io.readString("Enter a date.");

	String pattern = "(\\,)|(\\.)|(\\-)|(\\_)|(\\/)|(\\\\)";
	inputtedDate = inputtedDate.trim().replaceAll(pattern, "");
	io.print(inputtedDate);

	return LocalDate.parse(inputtedDate, DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    public int getInputOrderNumber() {
	return io.readInt("Enter the Order Number.");
    }

    public void displayBannerOrderAdd() {
	createBanner("Display Add Order Option");
    }

    public String getInputCustomerName() {
	return io.readString("Enter the customer's name.");
    }

    public String getInputStateAbbrivation() {
	return io.readString("Enter the state abbrivation.");
    }

    public String getInputProductType() {
	return io.readString("Enter the productType.");
    }

    public BigDecimal getInputArea() {
	return new BigDecimal(io.readString("Enter the area."));
    }
    
    public String getInputAreaString() {
	return io.readString("Enter the area");
    }

    public void displayCustomerName(Order order) {
	io.print("Current Data: " + order.getCustomerName());
    }

    public void displayStateAbbrivation(Order order) {
	io.print("Current Data: " + order.getState().getName());
    }

    public void displayProductType(Order order) {
	io.print("Current Data: " + order.getProduct().getType());
    }

    public void displayArea(Order order) {
	io.print("Current Data: " + order.getArea().toString());
    }

    public void displayOrder(Order o) {
	io.print("Order Number:             " + o.getOrderNumber());
	io.print("Customer Name:            " + o.getCustomerName());
	io.print("State:                    " + o.getState().getName());
	io.print("Tax Rate Of State:        " + o.getState().getTaxRate());
	io.print("Product Type:             " + o.getProduct().getType());
	io.print("Area:                     " + o.getArea());
	io.print("Material Cost:          $ " + o.getProduct().getCostPerSqFtMaterial() + " /per Sq Ft");
	io.print("Labor Cost:             $ " + o.getProduct().getCostPerSqFtLabor() + " /per Sq Ft");
	io.print("Total Material:         $ " + round(o.getTotalCostMaterial()));
	io.print("Total Labor:            $ " + round(o.getTotalCostLabor()));
	io.print("Total Tax:              $ " + round(o.getTotalCostTax()));
	io.print("Order Total:            $ " + round(o.getOrderTotal()));
	io.print("");
	io.print("-------------------------------");
	io.print("");
    }

    //==========================================================================
    //	    Main Menu
    //==========================================================================
    public void displayBannerMenu() {
	createBanner("Main Menu");
    }

    public void displayMenu() {
	io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
	io.print("*  <<Flooring Program>>");
	io.print("* 1. Display Orders");
	io.print("* 2. Add an Order");
	io.print("* 3. Edit an Order");
	io.print("* 4. Remove an Order");
	io.print("* 5. Save Current Work");
	io.print("* 6. Quit");
	io.print("*");
	io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public int getInputMenu() {
	return io.readInt("Enter an option.");
    }

    //==========================================================================
    //	    Display Orders
    //==========================================================================
    public void displayBannerOrders() {
	createBanner("Display Orders Option");
    }

    public void displayBannerOrdersList(LocalDate date) {
	createBanner("Displaying orders for "
		+ date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
    }

    public void displayOrders(List<Order> list) {
	for (Order o : list) {
	    displayOrder(o);
	}
    }

    private BigDecimal round(BigDecimal bigDecimal) {
	return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    //==========================================================================
    //	    Add an Order
    //==========================================================================
    public void displayAviableProducts(List<Product> list) {
	io.print("Avaiable Products");
	
	for(Product p : list) {
	    io.print(p.getType());
	}
    }
    
    public Order OrderAdd(String customerName, String stateAbbrivation, String productType, BigDecimal area) {
	Order order = new Order();

	order.setCustomerName(customerName);
	System.out.println(order.getState()+","+stateAbbrivation);
	order.getState().setName(stateAbbrivation);
	order.getProduct().setType(productType);
	order.setArea(area);

	return order;
    }

    public String getInputConfirmationAdd() {
	return io.readString("Are you sure you want to add this order? y/n");
    }
    
    public void displayBannerSuccessOrderAdd() {
	createBanner("Order added successfully.");
    }
    
    public void displayBannerCurrentData() {
	createBanner("Current Data");
    }

    //==========================================================================
    //	    Edit an Order
    //==========================================================================
    public void displayBannerOrderEdit() {
	createBanner("Display Edit Order Option");
    }

    public void displayMessageEdit() {
	io.print("Order found.");
	io.print("Press enter to skip any part you do not want to edit.");
    }

    public Order OrderEditAll(Order order, String customerName, String stateAbbrivation, String productType, BigDecimal area) {
	Order newOrder = new Order();
	newOrder.setOrderNumber(order.getOrderNumber());
	
	if (customerName.isEmpty()) {
	    newOrder.setCustomerName(order.getCustomerName());
	}
	else {
	    newOrder.setCustomerName(customerName);
	}
	
	if (stateAbbrivation.isEmpty()) {
	    newOrder.getState().setName(order.getState().getName());
	}
	else {
	    newOrder.getState().setName(stateAbbrivation);
	}

	if (productType.isEmpty()) {
	    newOrder.getProduct().setType(order.getProduct().getType());
	}
	else {
	    newOrder.getProduct().setType(productType);
	}

	if (area.equals(BigDecimal.ZERO)) {
	    newOrder.setArea(order.getArea());
	}
	else {
	    newOrder.setArea(area);
	}

	return newOrder;
    }

    //==========================================================================
    //	    Remove an Order
    //==========================================================================
    public void displayBannerOrderRemove() {
	createBanner("Display Remove Order Option");
    }

    public String getInputConfirmationRemove() {
	return io.readString("Are you sure you want to remove this order? y/n");
    }
    
    public void displayBannerSuccessOrderRemove() {
	createBanner("Order removed successfully.");
    }

    //==========================================================================
    //	    Save Current Work
    //==========================================================================
    public void displayBannerSave() {
	createBanner("Current work saved.");
    }

    //==========================================================================
    //	    Program Start
    //==========================================================================
    public void displayBannerProgramStart() {
	io.print(" -----------------------------------------------------");
	io.print("|						   	|");
	io.print("|	Welcome to The SWGCorp's Flooring Program	|");
	io.print("|						   	|");
	io.print(" - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    //==========================================================================
    //	    Program End
    //==========================================================================
    public void displayBannerProgramEnd() {
	io.print(" ---------------------------------------------------------");
	io.print("|							    |");
	io.print("|	Thank you for using SGWCorp's Flooring Program	    |");
	io.print("|							    |");
	io.print(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    //==========================================================================
    //	    Error Messages
    //==========================================================================
    public void messageInputIncorrect() {
	io.print("Incorrect Input");
    }

    public void messageInputUnknown() {
	io.print("Uknown Input");
    }

    public void messageDateNotFound(LocalDate date) {
	io.print("No orders for "
		+ date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
		+ " exist.");
    }

    public void messageOrderNumberNotFound(int orderNumber) {
	io.print("No orders with order Number " + orderNumber + " exist.");
    }
    
    public void messageError(String message) {
	io.print(message);
    }
}
