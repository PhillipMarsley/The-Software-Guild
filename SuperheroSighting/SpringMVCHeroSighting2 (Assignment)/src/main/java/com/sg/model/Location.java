/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Dan
 */
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location implements TableObject {

//    @Id
//    @GeneratedValue
    private int id;

//    @Column(nullable = false)
//    @Size(min = 1, max = 50, message = "Name must be between 1- 50")
    private String name;

//    @Column(nullable = true)
//    @Size(min = 0, max = 500, message = "Description must be between 0- 500")
    private String description;

//    @Column(nullable = false)
//    @Size(min = 1, max = 50, message = "Country must be between 1- 50")
    private String country;

//    @Column(nullable = false)
//    @Size(min = 1, max = 50, message = "State must be between 1- 50")
    private String state;

//    @Column(nullable = false)
//    @Size(min = 1, max = 50, message = "City must be between 1- 128")
    private String city;

//    @Column(nullable = false)
//    @Size(min = 1, max = 50, message = "Street must be between 1- 50")
    private String street;

//    @Column(nullable = false)
//    @Size(min = 1, max = 20, message = "Zip must be between 1- 20")
    private String zip;

//    @Column(nullable = false)
//    @Size(min = 9, max = 9, message = "Latitude must be 9 percision with 6 decimal places")
    private BigDecimal latitude;

//    @Column(nullable = false)
//    @Size(min = 9, max = 9, message = "Longitude must be 9 percision with 6 decimal places")
    private BigDecimal longitude;

    public Location() {
    }

    @Override
    public int getId() {
	return id;
    }

    @Override
    public void setId(int id) {
	this.id = id;
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

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public BigDecimal getLatitude() {
	return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
	this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
	return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
	this.longitude = longitude;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 83 * hash + this.id;
	hash = 83 * hash + Objects.hashCode(this.name);
	hash = 83 * hash + Objects.hashCode(this.description);
	hash = 83 * hash + Objects.hashCode(this.country);
	hash = 83 * hash + Objects.hashCode(this.state);
	hash = 83 * hash + Objects.hashCode(this.city);
	hash = 83 * hash + Objects.hashCode(this.street);
	hash = 83 * hash + Objects.hashCode(this.zip);
	hash = 83 * hash + Objects.hashCode(this.latitude);
	hash = 83 * hash + Objects.hashCode(this.longitude);
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
	final Location other = (Location) obj;
	if (this.id != other.id) {
	    return false;
	}
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	if (!Objects.equals(this.description, other.description)) {
	    return false;
	}
	if (!Objects.equals(this.country, other.country)) {
	    return false;
	}
	if (!Objects.equals(this.state, other.state)) {
	    return false;
	}
	if (!Objects.equals(this.city, other.city)) {
	    return false;
	}
	if (!Objects.equals(this.street, other.street)) {
	    return false;
	}
	if (!Objects.equals(this.zip, other.zip)) {
	    return false;
	}
	if (!Objects.equals(this.latitude, other.latitude)) {
	    return false;
	}
	if (!Objects.equals(this.longitude, other.longitude)) {
	    return false;
	}
	return true;
    }

}
