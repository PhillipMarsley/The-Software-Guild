/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.VendableItem;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Dan
 */
public class DaoImpl implements Dao {

    private static final String FILE_NAME = "items.txt";
    private static final String DELIMITER = "::";

    public Map<Integer, VendableItem> itemsMap;

    public DaoImpl() {
	//default no-args constructor
    }

    @Override
    public int getQuanity(VendableItem item) {
	loadFile();
	return item.getQuanity();
    }

    @Override
    public void updateQuanity(VendableItem item, int count) {
	loadFile();
	item.setQuanity(item.getQuanity() + count);
	writeFile();
    }

    @Override
    public VendableItem addItem(int itemKey, VendableItem item) {
	loadFile();
	VendableItem newItem = itemsMap.put(itemKey, item);
	writeFile();
	return newItem;
    }

    @Override
    public VendableItem removeItem(int itemKey) {
	loadFile();
	VendableItem item = itemsMap.remove(itemKey);
	writeFile();
	return item;
    }

    @Override
    public VendableItem getItem(int itemKey) {
	loadFile();
	return itemsMap.get(itemKey);
    }

    @Override
    public List<VendableItem> getItemList() {
	loadFile();
	return new ArrayList<>(itemsMap.values());
    }

    private void loadFile() {
	if (itemsMap != null) {
	    return;
	}

	itemsMap = new TreeMap<>();

	Scanner scanner;
	int lineCountAndItemKey = 1;

	try {
	    scanner = new Scanner(new FileReader(FILE_NAME));

	    while (scanner.hasNextLine()) {
		String line = scanner.nextLine();
		String[] lineParts = line.split(DELIMITER);
		VendableItem item = new VendableItem();

		try {
		    item.setName(lineParts[0]);
		    item.setCost(new BigDecimal(lineParts[1]));
		    item.setQuanity(Integer.parseInt(lineParts[2]));

		    itemsMap.put(lineCountAndItemKey, item);
		    lineCountAndItemKey++;
		}
		catch (NumberFormatException ex) {
		    ex.getMessage();
		}
	    }

	    scanner.close();
	}
	catch (FileNotFoundException ex) {
	    ex.getMessage();
	}
    }

    private void writeFile() {
	PrintWriter writer;
	List<VendableItem> items = new ArrayList<>(itemsMap.values());

	try {
	    writer = new PrintWriter(new FileWriter(FILE_NAME));

	    for (VendableItem i : items) {
		writer.println(
			i.getName() + DELIMITER
			+ i.getCost() + DELIMITER
			+ i.getQuanity() + DELIMITER);
		writer.flush();
	    }

	    writer.close();
	}
	catch (IOException ex) {
	    ex.getMessage();
	}
    }
}
