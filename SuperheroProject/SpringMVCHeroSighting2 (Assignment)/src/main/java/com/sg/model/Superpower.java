/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.util.Objects;

/**
 *
 * @author Dan
 */
public class Superpower implements TableObject {

    private int superpowerId;
    private String type;
    
    public Superpower() {
    }

    @Override
    public int getId() {
	return superpowerId;
    }

    @Override
    public void setId(int id) {
	this.superpowerId = id;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 73 * hash + this.superpowerId;
	hash = 73 * hash + Objects.hashCode(this.type);
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
	final Superpower other = (Superpower) obj;
	if (this.superpowerId != other.superpowerId) {
	    return false;
	}
	if (!Objects.equals(this.type, other.type)) {
	    return false;
	}
	return true;
    }
    
    
}
