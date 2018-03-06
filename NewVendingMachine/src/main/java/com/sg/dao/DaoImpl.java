/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import java.io.File;
import com.sg.model.VendableItem;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dan
 */
@Component
public class DaoImpl implements Dao {
    
    private static final String s = File.separator;

    private static final String FILE_NAME = "C:" + s 
	    + "Users" + s 
	    + "Dan" + s 
	    + "Documents" + s 
	    + "Software Guild" + s 
	    + "Projects Web" + s 
	    + "NewVendingMachine" + s 
	    + "src" + s 
	    + "main" + s 
	    + "resources" + s
	    + "items.txt";

    private File FILE = new File(FILE_NAME);
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
	
	String test = "~~[zoOoz]~~";

	try {
	    scanner = new Scanner(new FileReader(FILE));

	    test = "SUCCEED";
	    
	    while (scanner.hasNextLine()) {
		String line = scanner.nextLine();
		String[] lineParts = line.split(DELIMITER);
		VendableItem item = new VendableItem();

		try {
		    item.setId(Integer.parseInt(lineParts[0]));
		    item.setName(lineParts[1]);
		    item.setCost(new BigDecimal(lineParts[2]));
		    item.setQuanity(Integer.parseInt(lineParts[3]));

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
	    test = "FAIL: \n ERROR MESSAGE:" + ex.getMessage() + "";
	}

//	throw new RuntimeException(test + "\n PATH: " + file.getAbsolutePath());
    }

    private void writeFile() {
	PrintWriter writer;
	List<VendableItem> items = new ArrayList<>(itemsMap.values());

	try {
	    writer = new PrintWriter(new FileWriter(FILE));

	    for (VendableItem i : items) {
		writer.println(
			i.getId() + DELIMITER
			+ i.getName() + DELIMITER
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
