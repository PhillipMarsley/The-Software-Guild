/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Dan
 */
public class Hero implements TableObject {

    private int heroId;
    private String name;
    private String description;
    private List<Superpower> superpowers;
    private List<Organization> organizations;

    public Hero() {
    }

    @Override
    public int getId() {
	return heroId;
    }

    @Override
    public void setId(int id) {
	this.heroId = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Superpower> getSuperpowers() {
	return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
	this.superpowers = superpowers;
    }

    public List<Organization> getOrganizations() {
	return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
	this.organizations = organizations;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 89 * hash + this.heroId;
	hash = 89 * hash + Objects.hashCode(this.name);
	hash = 89 * hash + Objects.hashCode(this.description);
	hash = 89 * hash + Objects.hashCode(this.superpowers);
	hash = 89 * hash + Objects.hashCode(this.organizations);
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
	final Hero other = (Hero) obj;
	if (this.heroId != other.heroId) {
	    return false;
	}
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	if (!Objects.equals(this.description, other.description)) {
	    return false;
	}
	if (!Objects.equals(this.superpowers, other.superpowers)) {
	    return false;
	}
	if (!Objects.equals(this.organizations, other.organizations)) {
	    return false;
	}
	return true;
    }
    
    
}
