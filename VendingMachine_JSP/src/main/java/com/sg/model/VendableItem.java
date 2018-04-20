/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.math.BigDecimal;

/**
 *
 * @author Dan
 */
public class VendableItem {

    private int id;
    private String name;
    private BigDecimal cost;
    private int quanity;

    public VendableItem() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }
    
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public BigDecimal getCost() {
	return cost;
    }

    public void setCost(BigDecimal cost) {
	this.cost = cost;
    }

    public int getQuanity() {
	return quanity;
    }

    public void setQuanity(int quanity) {
	this.quanity = quanity;
    }
}
