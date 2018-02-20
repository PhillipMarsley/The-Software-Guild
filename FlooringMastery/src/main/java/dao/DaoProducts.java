/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public interface DaoProducts {

    /**
     * 
     * @param type
     * @return 
     * @throws DaoPersistanceException 
     */
    public Product getProduct(String type) throws DaoPersistanceException;
}
