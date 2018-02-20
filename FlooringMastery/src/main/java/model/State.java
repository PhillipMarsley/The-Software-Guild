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
public class State {

    private String name;

    private BigDecimal taxRate;

    public State() {
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public BigDecimal getTaxRate() {
	return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
	this.taxRate = taxRate;
    }
}
