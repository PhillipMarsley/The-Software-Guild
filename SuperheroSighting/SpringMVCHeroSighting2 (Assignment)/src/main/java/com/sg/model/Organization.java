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
public class Organization implements TableObject {

    private int organizationId;
    private int locationId;
    private Location location;
    private String name;
    private String description;
    private String phone;
    private String email;
    
    public Organization() {
    }

    @Override
    public int getId() {
	return organizationId;
    }

    @Override
    public void setId(int id) {
	this.organizationId = id;
    }

    public int getLocationId() {
	return locationId;
    }

    public void setLocationId(int locationId) {
	this.locationId = locationId;
    }

    public Location getLocation() {
	return location;
    }

    public void setLocation(Location location) {
	this.location = location;
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

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 31 * hash + this.organizationId;
	hash = 31 * hash + this.locationId;
	hash = 31 * hash + Objects.hashCode(this.location);
	hash = 31 * hash + Objects.hashCode(this.name);
	hash = 31 * hash + Objects.hashCode(this.description);
	hash = 31 * hash + Objects.hashCode(this.phone);
	hash = 31 * hash + Objects.hashCode(this.email);
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
	final Organization other = (Organization) obj;
	if (this.organizationId != other.organizationId) {
	    return false;
	}
	if (this.locationId != other.locationId) {
	    return false;
	}
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	if (!Objects.equals(this.description, other.description)) {
	    return false;
	}
	if (!Objects.equals(this.phone, other.phone)) {
	    return false;
	}
	if (!Objects.equals(this.email, other.email)) {
	    return false;
	}
	if (!Objects.equals(this.location, other.location)) {
	    return false;
	}
	return true;
    }

    
    
    
}
