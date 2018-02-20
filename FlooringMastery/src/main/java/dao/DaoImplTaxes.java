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
import java.util.Map;
import java.util.Scanner;
import model.State;

/**
 *
 * @author Dan
 */
//read file into collection object
//--------------------------------
//Get stateName
//Get taxRate
public class DaoImplTaxes implements DaoTaxes {

    private static final String DELIMITER = ",";
    static final String FILE_NAME = "data"
	    + File.separator + "taxes"
	    + File.separator + "Taxes.txt";

    public List<State> states = new ArrayList<>();

    public String getFilePath() {
	return FILE_NAME;
    }

    public List<State> getStates() throws DaoPersistanceException {
	readFile(FILE_NAME);
	
	return states;
    }

    @Override
    public State getState(String name) throws DaoPersistanceException {
	readFile(FILE_NAME);
	
	for (int i = 0; i < states.size(); i++) {
	    if (states.get(i).getName().equalsIgnoreCase(name)) {
		return states.get(i);
	    }
	}
	throw new DaoPersistanceException("Not a state in file");
    }

    private List<State> readFile(String fileName) throws DaoPersistanceException {
	if(!states.isEmpty()) {
	    return states;
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

		State state = new State();

		state.setName(lineParts[0]);
		state.setTaxRate(new BigDecimal(lineParts[1]));

		states.add(state);
		currentLineNum++;
	    }
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

	return states;
    }
}
