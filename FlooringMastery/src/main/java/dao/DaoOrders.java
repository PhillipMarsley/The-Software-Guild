/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import model.Order;

/**
 *
 * @author Dan
 */
//read file into collection object
//generate OrderNumber
//generate Date for file name
//write file
//--------------------------------
//	Getters and Setters
//customerName
//state
//taxRate
//productType
//area
//costPerSquareFootMaterial
//costPerSquareFootLabor
//totalCostMaterial
//totalCostLabor
//totalTax
//total
public interface DaoOrders {
    
    /**
     * 
     * @param order
     * @return 
     * @throws DaoPersistanceException 
     */
    public Order addOrder(Order order) throws DaoPersistanceException;
    
    /**
     * 
     * @param date
     * @param orderNumber
     * @return
     * @throws DaoPersistanceException 
     */
    public Order removeOrder(LocalDate date, int orderNumber) throws DaoPersistanceException;
        
    /**
     * 
     * @param date
     * @param orderNumber
     * @return
     * @throws DaoPersistanceException 
     */
    public Order getOrder(LocalDate date, int orderNumber) throws DaoPersistanceException;
    
    /**
     * 
     * @param date
     * @return
     * @throws DaoPersistanceException 
     */
    public List<Order> getOrders(LocalDate date) throws DaoPersistanceException;
}
