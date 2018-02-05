/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Service.InsufficientFundsException;
import Service.NoItemInventoryException;
import Service.Service;
import Ui.View;
import java.math.BigDecimal;

/**
 *
 * @author Dan
 */
public class Controller {

    private Service service;
    private View view;

    public Controller(Service service, View view) {
	this.service = service;
	this.view = view;
    }

    public void run() {
	int userInput = 0;

	//=====================================
	//	Porgram Start Text
	//=====================================
	view.messageProgramStart();

	//=====================================
	//	Main Menu Text
	//=====================================
	view.bannerItemList();
	view.displayMenuMainWithoutExit(service.getItemList());
	view.displayBlankConsoleLine();

	//=====================================
	//	Insert Funds Into Machine
	//=====================================
	BigDecimal userFunds = insertFunds(userInput);

	//=====================================
	//	Get Item from Machine
	//=====================================
	int[] userChange;

	while (true) {
	    try {
		view.bannerItemChoose();

		view.displayBlankConsoleLine();
		view.displayMenuMainWithExit(service.getItemList());
		view.displayBlankConsoleLine();

		int wrongTries = 0;
		while (true) {
		    if (wrongTries >= 5) {
			view.displayMenuMainWithoutExit(service.getItemList());
			wrongTries = 0;
		    }

		    try {
			userInput = view.askItemChoose();
			if (userInput > service.getItemList().size()) {
			    view.errorMessageUnknownCommand();
			    wrongTries++;
			    continue;
			}
			break;
		    }
		    catch (NumberFormatException ex) {
			view.errorMessageUnknownCommand();
			wrongTries++;
		    }
		}

		if (userInput == service.getItemList().size()) {
		    view.messageFundsReturned();
		    view.messageProgramEnd();
		    System.exit(0);
		}

		userChange = service.checkFunds(userFunds, userInput);
		break;
	    }
	    catch (InsufficientFundsException ex) {
		view.errorMessageInsufficentFunds();
		view.displayFundsTotal(userFunds);
		userFunds = insertFunds(userInput);
	    }
	}

	view.displayBlankConsoleLine();
	view.bannerItemDispensed();
	try {
	    view.displayItemDispensed(service.getItem(userInput).getName());
	    service.updateQuanity(service.getItem(userInput), -1);
	}
	catch (NoItemInventoryException ex) {
	    //do nothing
	}
	view.displayBlankConsoleLine();

	//=====================================
	//	Dispense Change
	//=====================================
	view.bannerFundsDispensed();
	view.displayFundsDispensed(userChange);
	view.displayBlankConsoleLine();

	//=====================================
	//	Program End Text
	//=====================================
	view.messageProgramEnd();

    } //END RUN

    //=====================================
    //	Insert Funds Into Machine Method
    //=====================================
    public BigDecimal insertFunds(int userInput) {
	String enterMore = "";
	BigDecimal userFunds = BigDecimal.ZERO;
	view.bannerFundsImput();

	do {
	    view.displayMenuFundsImput();

	    int wrongTries = 0;
	    while (true) {
		if (wrongTries >= 5) {
		    view.displayBlankConsoleLine();
		    view.displayMenuFundsImput();
		    wrongTries = 0;
		}

		try {
		    userInput = view.getFundsInput();
		    if (userInput > 10 || userInput < 1) {
			view.errorMessageUnknownCommand();
			wrongTries++;
			continue;
		    }
		    break;
		}
		catch (NumberFormatException ex) {
		    view.errorMessageUnknownCommand();
		    wrongTries++;
		}
	    }

	    if (userInput == 10) {
		if(userFunds.compareTo(BigDecimal.ZERO) > 0) {
		    view.messageFundsReturned();
		    view.messageProgramEnd();
		}
		else {
		    view.displayBlankConsoleLine();
		    view.messageProgramEnd();
		}
		
		System.exit(0);
	    }

	    BigDecimal denominationCalc = service.getDenomination(userInput);
	    userFunds = userFunds.add(denominationCalc);

	    view.displayFundsTotal(userFunds);
	    view.displayBlankConsoleLine();
	    enterMore = view.getFundsInputAgain();
	    view.displayBlankConsoleLine();
	}
	while (enterMore.equalsIgnoreCase("y"));

	return userFunds;
    }
}
