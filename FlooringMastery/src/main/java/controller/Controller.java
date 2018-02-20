/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DaoPersistanceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import service.DataValidationException;
import service.Service;
import ui.View;

/**
 *
 * @author Dan
 */
public class Controller {

    private View view;
    private Service service;

    public Controller(View view, Service service) {
	this.view = view;
	this.service = service;
    }

    public void run() {
	view.displayBannerProgramStart();

	while (true) {
	    view.displayBannerMenu();
	    view.displayMenu();

	    int userInput = 0;
	    int incorrectInputCounter = 0;

	    while (true) {
		if (incorrectInputCounter >= 5) {
		    view.displayBannerMenu();
		    view.displayMenu();
		    incorrectInputCounter = 0;
		}

		try {
		    userInput = view.getInputMenu();
		    if (userInput > 0 && userInput < 7) {
			break;
		    }
		}
		catch (NumberFormatException ex) {
		    //fall through
		}
		view.messageInputIncorrect();
		incorrectInputCounter++;
	    }

	    switch (userInput) {
		case 1:
		    ordersList();
		    break;
		case 2:
		    orderAdd();
		    break;
		case 3:
		    orderEdit();
		    break;
		case 4:
		    orderRemove();
		    break;
		case 5:
		    save();
		    break;
		case 6:
		    view.displayBannerProgramEnd();
		    System.exit(0);
		    break;
		default:
		    view.messageInputIncorrect();
		    break;
	    }
	}
    }

    public void ordersList() {
	List<Order> list;
	LocalDate date = LocalDate.MAX;

	view.displayBannerOrders();

	do {
	    try {
		date = view.getInputDate();
		list = service.getOrders(date);
		break;
	    }
	    catch (DaoPersistanceException ex) {
		//file not found
	    }
	    catch (DataValidationException ex) {
		view.messageDateNotFound(date);
	    }
	    catch (DateTimeParseException ex) {
		view.messageInputIncorrect();
	    }
	}
	while (true);

	view.displayBannerOrdersList(date);
	view.displayOrders(list);
    }

    public void orderAdd() {
	String customerName;
	String stateAbbrivation;
	String productType;
	BigDecimal area = BigDecimal.ZERO;

	view.displayBannerOrderAdd();

	customerName = view.getInputCustomerName();

	while (true) {
	    stateAbbrivation = view.getInputStateAbbrivation();

	    try {
		service.checkStateExists(stateAbbrivation);
		break;
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }
	}

	while (true) {
	    try {
		view.displayAviableProducts(service.getProducts());
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }

	    productType = view.getInputProductType();

	    try {
		service.checkProductExists(productType);
		break;
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }
	}

	while (true) {
	    try {
		area = view.getInputArea();
		if (area.compareTo(new BigDecimal("0.99")) == 1) {
		    break;
		}
		view.messageError("Input must be greater than 1");
	    }
	    catch (NumberFormatException ex) {
		view.messageInputIncorrect();
	    }
	}

	Order order = view.OrderAdd(customerName, stateAbbrivation, productType, area);

	view.displayBannerCurrentData();

	view.displayCustomerName(order);
	view.displayStateAbbrivation(order);
	view.displayProductType(order);
	view.displayArea(order);

	if (view.getInputConfirmationAdd().equalsIgnoreCase("y")) {
	    try {
		service.addOrder(order);
		view.displayBannerSuccessOrderAdd();
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }
	}
    }

    public void orderEdit() {
	LocalDate date = LocalDate.MAX;
	Order order = new Order();
	Order newOrder;
	int orderNumber = 0;
	int incorrectInputCounter = 0;

	view.displayBannerOrderEdit();

	//ask for date
	while (true) {
	    if (incorrectInputCounter >= 5) {
		incorrectInputCounter = 0;
		return;
	    }

	    try {
		date = view.getInputDate();
		if (service.checkDateExists(date)) {
		    break;
		}
	    }
	    catch (DateTimeParseException ex) {
		view.messageInputIncorrect();
	    }
	    view.messageDateNotFound(date);
	    incorrectInputCounter++;
	}

	//ask for oder number
	while (true) {
	    orderNumber = view.getInputOrderNumber();

	    try {
		order = service.getOrder(date, orderNumber);
		break;
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }
	}

	view.displayCustomerName(order);
	String customerName = view.getInputCustomerName();

	view.displayStateAbbrivation(order);
	String stateAbbrivation = view.getInputStateAbbrivation();

	view.displayProductType(order);
	String productType = view.getInputProductType();

	view.displayArea(order);

	String stringArea;
	BigDecimal area = BigDecimal.ZERO;

	while (true) {
	    stringArea = view.getInputAreaString();

	    if (stringArea.isEmpty()) {
		break;
	    }
	    else {
		try {
		    area = new BigDecimal(stringArea);
		    break;
		}
		catch (NumberFormatException ex) {
		    view.messageInputIncorrect();
		}
	    }
	}

	newOrder = view.OrderEditAll(order, customerName, stateAbbrivation, productType, area);

	newOrder.setOrderNumber(order.getOrderNumber());

	try {
	    service.replaceOrderInList(date, order, newOrder);
	}
	catch (DaoPersistanceException ex) {
	    view.messageError(ex.getMessage());
	}
    }

    public void orderRemove() {
	LocalDate date = LocalDate.MAX;
	Order order = new Order();
	int orderNumber = 0;
	int incorrectInputCounter = 0;

	view.displayBannerOrderRemove();

	//ask for date
	while (true) {
	    if (incorrectInputCounter >= 5) {
		incorrectInputCounter = 0;
		return;
	    }

	    try {
		date = view.getInputDate();
		if (service.checkDateExists(date)) {
		    break;
		}
	    }
	    catch (DateTimeParseException ex) {
		view.messageInputIncorrect();
	    }
	    view.messageDateNotFound(date);
	    incorrectInputCounter++;
	}

	//ask for oder number
	while (true) {
	    orderNumber = view.getInputOrderNumber();

	    try {
		order = service.getOrder(date, orderNumber);
		break;
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }
	}

	//display order
	view.displayOrder(order);

	//confirm
	if (view.getInputConfirmationRemove().equals("y")) {
	    try {
		service.removeOrder(date, orderNumber);
	    }
	    catch (DaoPersistanceException ex) {
		view.messageError(ex.getMessage());
	    }
	}
    }

    public void save() {
	try {
	    service.save();
	    view.displayBannerSave();
	}
	catch (DaoPersistanceException ex) {
	    view.messageError(ex.getMessage());
	}
    }
}

