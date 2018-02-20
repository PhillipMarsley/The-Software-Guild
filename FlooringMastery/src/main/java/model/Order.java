/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author Dan
 */
public class Order {

    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    
    private State state;
    private Product product;
    private BigDecimal totalCostMaterial;
    private BigDecimal totalCostLabor;
    private BigDecimal totalCostTax;
    private BigDecimal orderTotal;

    public Order() {
	this.state = new State();
	this.product = new Product();
    }

    public int getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
	this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public BigDecimal getArea() {
	return area;
    }

    public void setArea(BigDecimal area) {
	this.area = area;
    }

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
    }

    public Product getProduct() {
	return product;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public BigDecimal getTotalCostMaterial() {
	return totalCostMaterial;
    }

    public void setTotalCostMaterial(BigDecimal totalCostMaterial) {
	this.totalCostMaterial = totalCostMaterial;
    }

    public BigDecimal getTotalCostLabor() {
	return totalCostLabor;
    }

    public void setTotalCostLabor(BigDecimal totalCostLabor) {
	this.totalCostLabor = totalCostLabor;
    }

    public BigDecimal getTotalCostTax() {
	return totalCostTax;
    }

    public void setTotalCostTax(BigDecimal totalCostTax) {
	this.totalCostTax = totalCostTax;
    }

    public BigDecimal getOrderTotal() {
	return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
	this.orderTotal = orderTotal;
    }
    
}
