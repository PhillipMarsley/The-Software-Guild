/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Dao.Dao;
import Model.VendableItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Dan
 */
public class Service {

    private Dao dao;

    public Service(Dao dao) {
	this.dao = dao;
    }

    public BigDecimal convertToBigDecimal(String input) {
	try {
	    return new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
	}
	catch (NumberFormatException ex) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
    }

    public BigDecimal convertToBigDecimal(int input) {
	return new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getDenomination(int input) {
	BigDecimal result = BigDecimal.ZERO;
	switch (input) {
	    case 1:
		result = Denomination.PENNY.getValue();
		break;
	    case 2:
		result = Denomination.NICKLE.getValue();
		break;
	    case 3:
		result = Denomination.DIME.getValue();
		break;
	    case 4:
		result = Denomination.QUARTER.getValue();
		break;
	    case 5:
		result = Denomination.LOONIE.getValue();
		break;
	    case 6:
		result = Denomination.TOONIE.getValue();
		break;
	    case 7:
		result = Denomination.FIVE_DOLLAR_BILL.getValue();
		break;
	    case 8:
		result = Denomination.TEN_DOLLAR_BILL.getValue();
		break;
	    case 9:
		result = Denomination.TWENTY_DOLLAR_BILL.getValue();
		break;
	    default:
		break;
	}
	return result.setScale(2, RoundingMode.HALF_UP);
    }

    public int getQuanity(VendableItem item) {
	return dao.getQuanity(item);
    }

    public void updateQuanity(VendableItem item, int count) throws NoItemInventoryException {
	if (item.getQuanity() < 1 && count < 1) {
	    throw new NoItemInventoryException("No more of that item in vending machine.");
	}
	dao.updateQuanity(item, count);
    }

    public VendableItem addItem(int itemKey, VendableItem item) {
	return dao.addItem(itemKey, item);
    }

    public VendableItem removeItem(int itemKey) {
	return dao.removeItem(itemKey);
    }

    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public Map<Integer, VendableItem> getItemList() {
	int[] i = new int[1];
	Map<Integer, VendableItem> newList
		= dao.getItemList().stream()
			.filter((item) -> dao.getQuanity(item) > 0)
			.collect(Collectors.toMap((item) -> i[0]++, (item) -> item));
	return newList;
    }

    public VendableItem getItem(int itemKey) throws NoItemInventoryException {
	if (getItemList().get(itemKey).getQuanity() < 1) {
	    throw new NoItemInventoryException("No more of " + getItemList().get(itemKey).getName() + " in vending machine.");
	}
	return getItemList().get(itemKey);
    }
    
   //used by logging system
   public String getItemName(int itemKey) {
       return getItemList().get(itemKey).getName();
   }

    public int[] checkFunds(BigDecimal userMoney, int itemKey) throws InsufficientFundsException {

	if (userMoney.compareTo(getItemList().get(itemKey).getCost()) < 0) {
	    throw new InsufficientFundsException("Not enough funds.");
	}

	BigDecimal userChange = userMoney.subtract(getItemList().get(itemKey).getCost());
	BigDecimal[] changeSplit;
	int[] changeAmounts = new int[6];

	if (userChange.compareTo(Denomination.TOONIE.getValue()) >= 0) {
	    changeSplit = userChange.divideAndRemainder(Denomination.TOONIE.getValue());
	    userChange = changeSplit[1];
	    changeAmounts[0] += changeSplit[0].intValue();
	}

	if (userChange.compareTo(Denomination.LOONIE.getValue()) >= 0) {
	    changeSplit = userChange.divideAndRemainder(Denomination.LOONIE.getValue());
	    userChange = changeSplit[1];
	    changeAmounts[1] += changeSplit[0].intValue();
	}

	if (userChange.compareTo(Denomination.QUARTER.getValue()) >= 0) {
	    changeSplit = userChange.divideAndRemainder(Denomination.QUARTER.getValue());
	    userChange = changeSplit[1];
	    changeAmounts[2] += changeSplit[0].intValue();
	}

	if (userChange.compareTo(Denomination.DIME.getValue()) >= 0) {
	    changeSplit = userChange.divideAndRemainder(Denomination.DIME.getValue());
	    userChange = changeSplit[1];
	    changeAmounts[3] += changeSplit[0].intValue();
	}

	if (userChange.compareTo(Denomination.NICKLE.getValue()) >= 0) {
	    changeSplit = userChange.divideAndRemainder(Denomination.NICKLE.getValue());
	    userChange = changeSplit[1];
	    changeAmounts[4] += changeSplit[0].intValue();
	}

	if (userChange.compareTo(Denomination.PENNY.getValue()) >= 0) {
	    changeSplit = userChange.divideAndRemainder(Denomination.PENNY.getValue());
	    userChange = changeSplit[0];
	    changeAmounts[5] += changeSplit[0].intValue();
	}

	return changeAmounts;
    }
}
