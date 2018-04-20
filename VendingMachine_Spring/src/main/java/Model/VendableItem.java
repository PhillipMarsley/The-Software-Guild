/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Dan
 */
public class VendableItem {

    private String name;
    private BigDecimal cost;
    private int quanity;

    public VendableItem() {
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

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 97 * hash + Objects.hashCode(this.name);
	hash = 97 * hash + Objects.hashCode(this.cost);
	hash = 97 * hash + this.quanity;
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final VendableItem other = (VendableItem) obj;
	if (this.quanity != other.quanity) {
	    return false;
	}
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	if (!Objects.equals(this.cost, other.cost)) {
	    return false;
	}
	return true;
    }

    
    
}
