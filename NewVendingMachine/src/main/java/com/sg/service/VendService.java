package com.sg.service;

import com.sg.dao.DaoImpl;
import com.sg.model.VendableItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class VendService {

    @Inject
    private DaoImpl dao;

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

    //==========================================================================
    //==========================================================================
    //==========================================================================
    public String getItemName(int itemKey) {
	return getItemList().get(itemKey).getName();
    }

    public List<VendableItem> getItemList() {
	return dao.getItemList();
    }

    public VendableItem getItem(int itemKey) {
	return getItemList().get(itemKey);
    }

    public int[] checkFunds(BigDecimal userMoney, int itemKey) throws InsufficientFundsException {
	BigDecimal userChange = BigDecimal.ZERO;

	if (itemKey == Integer.MAX_VALUE) {
	    userChange = userMoney;
	}
	else {
	    if (userMoney.compareTo(getItemList().get(itemKey).getCost()) <= 0) {
		throw new InsufficientFundsException("Not enough funds.");
	    }
	    userChange = userMoney.subtract(getItemList().get(itemKey).getCost());
	}

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
	    changeAmounts[5] += changeSplit[0].intValue();
	}

	return changeAmounts;
    }
}
