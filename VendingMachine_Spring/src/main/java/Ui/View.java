/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Model.VendableItem;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Dan
 */
public class View {

    private UserIO io;

    public View(UserIO io) {
	this.io = io;
    }

    //=========================================
    //          Start Program
    //=========================================
    public void messageProgramStart() {
	io.print("::::::::::::::::::::::::::::::::::::::::::::");
	io.print("::                                        ::");
	io.print("::  Welcome to a Vending Machine Program  ::");
	io.print("::                                        ::");
	io.print("::::::::::::::::::::::::::::::::::::::::::::");
    }

    //=========================================
    //          Display Itmes & Price
    //=========================================
    public void bannerItemList() {
	io.print(" ..............................");
	io.print(":                              :");
	io.print(":  Items Avaiable to Purchase  :");
	io.print(":                              :");
	io.print(" ..............................");
    }

    public void displayMenuMainWithoutExit(Map<Integer, VendableItem> itemList) {
	for (int i = 0; i < itemList.size(); i++) {
	    io.print(i + " - $ " + itemList.get(i).getCost() + " - " + itemList.get(i).getName());
	}
    }

    public void displayMenuMainWithExit(Map<Integer, VendableItem> itemList) {
	for (int i = 0; i < itemList.size(); i++) {
	    io.print(i + " - $ " + itemList.get(i).getCost() + " - " + itemList.get(i).getName());
	}
	io.print((itemList.size()) + " - exit");
    }

    //=========================================
    //          Ask User For Funds
    //=========================================
    public void bannerFundsImput() {
	io.print(" ................");
	io.print(":                :");
	io.print(":  Insert Funds  :");
	io.print(":                :");
	io.print(" ................");
    }

    public void displayMenuFundsImput() {
	io.print("1  - $  0.01 - Penny");
	io.print("2  - $  0.05 - Nickle");
	io.print("3  - $  0.10 - Dime");
	io.print("4  - $  0.25 - Quarter");
	io.print("5  - $  1.00 - Loonie");
	io.print("6  - $  2.00 - Toonie");
	io.print("7  - $  5.00 - Five Dollar Bill");
	io.print("8  - $ 10.00 - Ten Dollar Bill");
	io.print("9  - $ 20.00 - Twenty Dollar Bill");
	io.print("10 - exit");
	io.print("");
    }

    public int getFundsInput() {
	return io.readInt("Enter an option.");
    }

    public String getFundsInputAgain() {
	return io.readString("Would you like to enter more? y/n");
    }

    public void displayFundsTotal(BigDecimal funds) {
	io.print("You have $" + funds.toString() + ".");
    }

    //=========================================
    //          Ask User for Item They Want
    //=========================================
    public void bannerItemChoose() {
	io.print(" ..................");
	io.print(":                  :");
	io.print(":  Choose an Item  :");
	io.print(":                  :");
	io.print(" ..................");
    }

    public int askItemChoose() {
	int userChoice = io.readInt("Enter an option.");
	return userChoice;
    }

    //=========================================
    //          Display Item
    //=========================================
    public void bannerItemDispensed() {
	io.print(" .............");
	io.print(":             :");
	io.print(":  Your Item  :");
	io.print(":             :");
	io.print(" .............");
    }

    public void displayItemDispensed(String itemName) {
	io.print("You recieved a " + itemName);
    }

    //=========================================
    //          Display Change
    //=========================================
    public void bannerFundsDispensed() {
	io.print(" ...............");
	io.print(":               :");
	io.print(":  Your Change  :");
	io.print(":               :");
	io.print(" ...............");
    }

    public void displayFundsDispensed(int[] changeArray) {
	int totalChange = 0;

	io.print("Your change is: ");
	io.print("");

	//Toonie, loonie, quarter, dime, nickle, penny
	//2.00,	  1.00	  0.25	   0.10	 0.05	 0.01
	//
	for (int i = 0; i < changeArray.length; i++) {
	    if (changeArray[i] > 0) {
		switch (i) {
		    case 0:
			if (changeArray[i] == 1) {
			    io.print(changeArray[i] + " Toonie");
			}
			else {
			    io.print(changeArray[i] + " Toonies");
			}

			totalChange += 200 * changeArray[i];
			break;
		    case 1:
			if (changeArray[i] == 1) {
			    io.print(changeArray[i] + " Loonie");
			}
			else {
			    io.print(changeArray[i] + " Loonies");
			}

			totalChange += 100 * changeArray[i];
			break;
		    case 2:
			if (changeArray[i] == 1) {
			    io.print(changeArray[i] + " Quarter");
			}
			else {
			    io.print(changeArray[i] + " Quarters");
			}

			totalChange += 25 * changeArray[i];
			break;
		    case 3:
			if (changeArray[i] == 1) {
			    io.print(changeArray[i] + " Dime");
			}
			else {
			    io.print(changeArray[i] + " Dimes");
			}

			totalChange += 10 * changeArray[i];
			break;
		    case 4:
			if (changeArray[i] == 1) {
			    io.print(changeArray[i] + " Nickle");
			}
			else {
			    io.print(changeArray[i] + " Nickles");
			}

			totalChange += 5 * changeArray[i];
			break;
		    case 5:
			if (changeArray[i] == 1) {
			    io.print(changeArray[i] + " penny");
			}
			else {
			    io.print(changeArray[i] + " pennies");
			}

			totalChange += 1 * changeArray[i];
			break;
		}
	    }
	}

	String total = Integer.toString(totalChange);

	if (totalChange > 100) {
	    total = new StringBuilder(total).insert(total.length() - 2, ".").toString();
	}
	else if (totalChange > 10) {
	    total = "0." + total;
	}
	else {
	    total = "0.0" + total;
	}

	io.print("");
	io.print("$" + total);
    }

//=========================================
//          End Program
//=========================================
    public void messageFundsReturned() {
	io.print("");
	io.print("Your money was returned to you.");
    }

    public void messageProgramEnd() {
	io.print(":::::::::::::::::::::::::::::::::::::");
	io.print("::                                 ::");
	io.print("::  Thanks for using this Program  ::");
	io.print("::                                 ::");
	io.print(":::::::::::::::::::::::::::::::::::::");
	io.print("Program Closed");
    }

    //=========================================
    //          Error Messages & Other
    //=========================================
    public void errorMessageUnknownCommand() {
	io.print("Unknown Command");
    }

    public void errorMessageInsufficentFunds() {
	io.print("Insufficent Funds");
    }

    public void errorMessageNotInInventory() {
	io.print("Item no longer in stock");
    }

    public void displayBlankConsoleLine() {
	io.print("");
    }
}
