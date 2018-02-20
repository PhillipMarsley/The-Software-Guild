/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.State;

/**
 *
 * @author Dan
 */
//read file into collection object
//--------------------------------
//Get stateName
//Get taxRate
public interface DaoTaxes {

    /**
     * 
     * @param name
     * @return 
     * @throws DaoPersistanceException
     */
    public State getState(String name) throws DaoPersistanceException;
}
