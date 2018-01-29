/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.DVD;

/**
 *
 * @author Dan
 */
public interface Dao {

    //Adds a dvd to the a list
    public void addDVD(String title, DVD dvd) throws DaoException;

    //Removes a dvd from a list
    public void removeDVD(String title) throws DaoException;

    //Edits a dvd in a list
    public void editDVDInfo(String title) throws DaoException;

    //Returns a dvd in a list
    public DVD getDVD(String title) throws DaoException;

    //Returns a list of DVDs
    public List<DVD> getDVDlist() throws DaoException;
    
    //Returns a list of DVD titles
    public List<String> getDVDTitles() throws DaoException;
}
