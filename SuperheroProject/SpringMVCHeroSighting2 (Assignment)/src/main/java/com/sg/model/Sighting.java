/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Dan
 */
public class Sighting implements TableObject {

    private int sightingId;
    private int LocationId;
    private Location location;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    
    private List<Hero> heros;

    public Sighting() {
    }

    @Override
    public int getId() {
	return sightingId;
    }

    @Override
    public void setId(int id) {
	this.sightingId = id;
    }

    public int getLocationId() {
	return LocationId;
    }

    public void setLocationId(int LocationId) {
	this.LocationId = LocationId;
    }
    
    public Location getLocation() {
	return location;
    }

    public void setLocation(Location location) {
	this.location = location;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public LocalTime getTime() {
	return time;
    }

    public void setTime(LocalTime time) {
	this.time = time;
    }

    public List<Hero> getHeros() {
	return heros;
    }

    public void setHeros(List<Hero> heros) {
	this.heros = heros;
    }
    

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 41 * hash + this.sightingId;
	hash = 41 * hash + this.LocationId;
	hash = 41 * hash + Objects.hashCode(this.location);
	hash = 41 * hash + Objects.hashCode(this.date);
	hash = 41 * hash + Objects.hashCode(this.time);
	hash = 41 * hash + Objects.hashCode(this.heros);
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
	final Sighting other = (Sighting) obj;
	if (this.sightingId != other.sightingId) {
	    return false;
	}
	if (this.LocationId != other.LocationId) {
	    return false;
	}
	if (!Objects.equals(this.location, other.location)) {
	    return false;
	}
	if (!Objects.equals(this.date, other.date)) {
	    return false;
	}
	if (!Objects.equals(this.time, other.time)) {
	    return false;
	}
	if (!Objects.equals(this.heros, other.heros)) {
	    return false;
	}
	return true;
    }


    
}
