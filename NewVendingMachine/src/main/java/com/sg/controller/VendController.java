/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.model.VendableItem;
import com.sg.service.InsufficientFundsException;
import com.sg.service.NoItemInventoryException;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.sg.service.VendService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VendController {

    BigDecimal total = BigDecimal.ZERO;
    Integer itemNum = null;
    String message = "";
    String change = "";

    @Inject
    VendService service;

    @GetMapping("/")
    public String loadItems(Model model) {

	List<VendableItem> itemList = service.getItemList();

	model.addAttribute("itemList", itemList);
	model.addAttribute("itemNum", itemNum == null ? "" : itemNum);
	model.addAttribute("total", total == null ? "" : total.setScale(2, RoundingMode.HALF_UP));
	model.addAttribute("total", total.equals(BigDecimal.ZERO) ? "" : total.setScale(2, RoundingMode.HALF_UP));
	model.addAttribute("message", message);
	model.addAttribute("change", change);

	return "index";
    }

    private void clearOnChangeButton() {
	message = "";
	change = "";
    }

    @PostMapping("getItemNum")
    public String getItemNum(Integer num) {
	itemNum = num;
	return "redirect:/";
    }

    @PostMapping("addToonie")
    public String addToonie() {
	total = total.add(service.getDenomination(6));
	clearOnChangeButton();
	return "redirect:/";
    }

    @PostMapping("addLoonie")
    public String addLonie() {
	total = total.add(service.getDenomination(5));
	clearOnChangeButton();
	return "redirect:/";
    }

    @PostMapping("addQuater")
    public String addQuater() {
	total = total.add(service.getDenomination(4));
	clearOnChangeButton();
	return "redirect:/";
    }

    @PostMapping("addDime")
    public String addDime() {
	total = total.add(service.getDenomination(3));
	clearOnChangeButton();
	return "redirect:/";
    }

    @PostMapping("addNickle")
    public String addNickle() {
	total = total.add(service.getDenomination(2));
	clearOnChangeButton();
	return "redirect:/";
    }

    @PostMapping("addPenny")
    public String addPenny() {
	total = total.add(service.getDenomination(1));
	clearOnChangeButton();
	return "redirect:/";
    }

    @PostMapping("makePurchase")
    public String makePurchase() throws NoItemInventoryException {

	if (itemNum == null) {
	    message = "Choose an item.";
	    return "redirect:/";
	}

	//itemNum - 1 to work with arraylist indicies
	if (service.getItem(itemNum - 1).getQuanity() <= 0) {
	    message = "Item out of sock.";
	    return "redirect:/";
	}

	try {
	    int[] returnedChange = service.checkFunds(total, itemNum - 1);

	    message = "Thank you!";
	    total = BigDecimal.ZERO;
	    change = buildChangeString(returnedChange);
	    service.updateQuanity(service.getItem(itemNum - 1), -1);
	    itemNum = null;
	}
	catch (InsufficientFundsException ex) {
	    message = "Insert $" + service.getItem(itemNum - 1).getCost().subtract(total) + " more";
	}

	return "redirect:/";
    }

    @PostMapping("returnChange")
    public String returnChange() throws InsufficientFundsException {

	if (total.equals(BigDecimal.ZERO)) {
	    message = "Nothing to return";
	    return "redirect:/";
	}

	int[] returnedChange = service.checkFunds(total, Integer.MAX_VALUE);
	change = buildChangeString(returnedChange);

	total = BigDecimal.ZERO;
	message = "Change Returned";

	return "redirect:/";
    }

    private String buildChangeString(int[] change) {
	String changeString = " ";

	for (int i = 0; i < change.length; i++) {
	    switch (i) {
		case 0: //toonie
		    changeString += checkQuanity("Toonie", change[i]);
		    break;
		case 1: //loonie
		    changeString += checkQuanity("Loonie", change[i]);
		    break;
		case 2: //quater
		    changeString += checkQuanity("Quater", change[i]);
		    break;
		case 3: //dime
		    changeString += checkQuanity("Dime", change[i]);
		    break;
		case 4: //nickle
		    changeString += checkQuanity("Nickel", change[i]);
		    break;
		case 5: //penny
		    changeString += checkQuanity("Penny", change[i]);
		    break;
		default:
		    changeString += "ERROR";
	    }
	}

	return changeString;
    }

    private String checkQuanity(String s, int num) {
	if (num <= 0) {
	    return "";
	}

	if (num == 1) {
	    return num + " " + s + " \n";
	}

	if (s.equals("Penny")) {
	    return num + " Pennies \n";
	}

	return num + " " + s + "s \n";
    }
}
