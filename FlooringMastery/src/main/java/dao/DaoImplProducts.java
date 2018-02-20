/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Product;

/**
 *
 * @author Dan
 */
//read file collection object
//---------------------------
//Get productType
//Get costPerSqFootMaterial
//Get CostPerSqFootLabor
public class DaoImplProducts implements DaoProducts {

    private static final String DELIMITER = ",";
    static final String FILE_NAME = "data"
	    + File.separator + "products"
	    + File.separator + "Products.txt";

    public List<Product> products = new ArrayList<>();
    
    public String getFilePath() {
	return FILE_NAME;
    }
    
    public List<Product> getProducts() throws DaoPersistanceException {
	readFile(FILE_NAME);
	
	return products;
    }
    
    @Override
    public Product getProduct(String type) throws DaoPersistanceException {
	readFile(FILE_NAME);
	
	for (int i = 0; i < products.size(); i++) {
	    if (products.get(i).getType().equalsIgnoreCase(type)) {
		return products.get(i);
	    }
	}
	throw new DaoPersistanceException("Not a product in file");
    }

    public List<Product> readFile(String fileName) throws DaoPersistanceException {
	if(!products.isEmpty()) {
	    return products;
	}
	
	Scanner scanner;
	int currentLineNum = 0;

	try {
	    scanner = new Scanner(new FileReader(fileName));

	    //skips the first line of the file
	    //first line of file is 'catagory/column' names
	    scanner.nextLine();
	    currentLineNum++;

	    while (scanner.hasNext()) {
		String line = scanner.nextLine();
		String[] lineParts = line.split(DELIMITER);

		Product product = new Product();

		product.setType(lineParts[0]);
		product.setCostPerSqFtMaterial(new BigDecimal(lineParts[1]));
		product.setCostPerSqFtLabor(new BigDecimal(lineParts[2]));

		products.add(product);
		currentLineNum++;
	    }
	    scanner.close();
	}
	catch (FileNotFoundException ex) {
	    String message = "Could not load: \n"
		    + fileName;

	    throw new DaoPersistanceException(message);
	}
	catch (NumberFormatException ex) {
	    String message = "Monitary Data Unable to be parsed in\n"
		    + fileName
		    + "\non line "
		    + currentLineNum;

	    throw new DaoPersistanceException(message);
	}
	catch (IndexOutOfBoundsException ex) {
	    String message = "Unable to be parsed correctly in \n"
		    + fileName
		    + "\non line "
		    + currentLineNum;

	    throw new DaoPersistanceException(message);
	}

	return products;
    }
}
