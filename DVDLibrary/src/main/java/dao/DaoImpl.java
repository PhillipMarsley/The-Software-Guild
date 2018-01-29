/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import model.DVD;

/**
 *
 * @author Dan
 */
public class DaoImpl implements Dao {

    private static final String DELIMITER = "::";
    private static final String FILE_NAME = "DVDLibrary.txt";

    private Map<String, DVD> DVDCollection;

    public DaoImpl() {
	//Default no-args constructor
    }

    @Override
    public void addDVD(String title, DVD dvd) throws DaoException {
	loadDVDLibrary();
	DVDCollection.put(title, dvd);
	writeDVDLibrary();
    }

    @Override
    public void removeDVD(String title) throws DaoException {
	loadDVDLibrary();
	DVDCollection.remove(title);
	writeDVDLibrary();
    }

    @Override
    public void editDVDInfo(String title) throws DaoException {
	loadDVDLibrary();
	DVDCollection.get(title);
	writeDVDLibrary();
    }

    @Override
    public DVD getDVD(String title) throws DaoException {
	loadDVDLibrary();
	return DVDCollection.get(title);
    }

    @Override
    public List<DVD> getDVDlist() throws DaoException {
	loadDVDLibrary();
	return new ArrayList<>(DVDCollection.values());
    }
    
    @Override
    public List<String> getDVDTitles() throws DaoException {
	loadDVDLibrary();
	return new ArrayList<>(DVDCollection.keySet());
    }

    private void loadDVDLibrary() throws DaoException {
	if (DVDCollection != null) {
	    return;
	}
	
	DVDCollection = new TreeMap<>();
	Scanner scanner;

	try {
	    scanner = new Scanner(new FileReader(FILE_NAME));
	}
	catch (FileNotFoundException ex) {
	    throw new DaoException("Could not load data from " + FILE_NAME, ex);
	}

	String currentLine;
	String[] currentTokens;

	while (scanner.hasNextLine()) {
	    currentLine = scanner.nextLine();
	    currentTokens = currentLine.split(DELIMITER);

	    DVD dvd = new DVD(currentTokens[0]);
	    dvd.setReleaseDate(currentTokens[1]);
	    dvd.setMPAARating(currentTokens[2]);
	    dvd.setDrictorName(currentTokens[3]);
	    dvd.setStudio(currentTokens[4]);
	    dvd.setUserNote(currentTokens[5]);

	    DVDCollection.put(dvd.getTitle(), dvd);
	}

	scanner.close();
    }

    private void writeDVDLibrary() throws DaoException {
	PrintWriter out;
	List<DVD> DVDList = getDVDlist();

	try {
	    out = new PrintWriter(new FileWriter(FILE_NAME));
	}
	catch (IOException ex) {
	    throw new DaoException("Could not save data to " + FILE_NAME, ex);
	}

	for (DVD dvd : DVDList) {
	    out.println(dvd.getTitle() + DELIMITER
		    + dvd.getReleaseDate() + DELIMITER
		    + dvd.getMPAARating() + DELIMITER
		    + dvd.getDrictorName() + DELIMITER
		    + dvd.getStudio() + DELIMITER
		    + dvd.getUserNote() + DELIMITER);
	    out.flush();
	}
	out.close();
    }
}
