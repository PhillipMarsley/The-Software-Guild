/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.Dao;
import com.sg.dao.DaoImpl;
import com.sg.model.Hero;
import com.sg.model.Location;
import com.sg.model.Organization;
import com.sg.model.Sighting;
import com.sg.model.Superpower;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Dan
 */
public class ServiceImpl implements Service {

    Dao dao = new DaoImpl();
    
    @Inject
    public ServiceImpl(Dao dao) {
	this.dao = dao;
    }
    
    //==========================================================================
    //	    Hero
    //==========================================================================
    @Override
    public List<Hero> getAllHeros() {
	return dao.getAllHeros();
    }

    @Override
    public List<Hero> getAllHerosBySuperpowerId(int id) {
	return dao.getAllHerosBySuperpowerId(id);
    }

    @Override
    public List<Hero> getAllHerosByOrganizationId(int id) {
	return dao.getAllHerosByOrganizationId(id);
    }

    @Override
    public List<Hero> getAllHerosBySightingId(int id) {
	return dao.getAllHerosBySightingId(id);
    }

    @Override
    public Hero getHeroById(int id) {
	return dao.getHeroById(id);
    }

    @Override
    public Hero deleteHeroById(int id) {
	return dao.deleteHeroById(id);
    }

    @Override
    public Hero updateHeroById(Hero hero) {
	return dao.updateHeroById(hero);
    }

    @Override
    public Hero addHero(Hero hero) {
	return dao.addHero(hero);
    }

    //==========================================================================
    //	    Superpower
    //==========================================================================
    @Override
    public List<Superpower> getAllSuperpowers() {
	return dao.getAllSuperpowers();
    }

    @Override
    public List<Superpower> getAllSuperpowersByHeroId(int id) {
	return dao.getAllSuperpowersByHeroId(id);
    }

    @Override
    public Superpower getSuperpowerById(int id) {
	return dao.getSuperpowerById(id);
    }

    @Override
    public Superpower deleteSuperpowerById(int id) {
	return dao.deleteSuperpowerById(id);
    }

    @Override
    public Superpower updateSuperpowerById(Superpower superpower) {
	return dao.updateSuperpowerById(superpower);
    }

    @Override
    public Superpower addSuperpower(Superpower superpower) {
	return dao.addSuperpower(superpower);
    }

    //==========================================================================
    //	    Location
    //==========================================================================
    @Override
    public List<Location> getAllLocations() {
	return dao.getAllLocations();
    }

    @Override
    public Location getLocationById(int id) {
	return dao.getLocationById(id);
    }

    @Override
    public Location deleteLocationById(int id) {
	return dao.deleteLocationById(id);
    }

    @Override
    public Location updateLocationById(Location location) {
	return dao.updateLocationById(location);
    }

    @Override
    public Location addLocation(Location location) {
	return dao.addLocation(location);
    }

    //==========================================================================
    //	    Organization
    //==========================================================================
    @Override
    public List<Organization> getAllOrganizations() {
	return dao.getAllOrganizations();
    }

    @Override
    public List<Organization> getAllOrganizationsByLocationId(int id) {
	return dao.getAllOrganizationsByLocationId(id);
    }

    @Override
    public List<Organization> getAllOrganizationsByHeroId(int id) {
	return dao.getAllOrganizationsByHeroId(id);
    }

    @Override
    public Organization getOrganizationById(int id) {
	return dao.getOrganizationById(id);
    }

    @Override
    public Organization deleteOrganizationById(int id) {
	return dao.deleteOrganizationById(id);
    }

    @Override
    public Organization updateOrganizationById(Organization organization) {
	return dao.updateOrganizationById(organization);
    }

    @Override
    public Organization addOrganization(Organization organization) {
	return dao.addOrganization(organization);
    }

    //==========================================================================
    //	    Sighting
    //==========================================================================
    @Override
    public List<Sighting> getAllSightings() {
	return dao.getAllSightings();
    }

    @Override
    public List<Sighting> getAllSightingsByHeroId(int id) {
	return dao.getAllSightingsByHeroId(id);
    }

    @Override
    public List<Sighting> getAllSightingsByLocationId(int id) {
	return dao.getAllSightingsByLocationId(id);
    }

    @Override
    public Sighting getSightingById(int id) {
	return dao.getSightingById(id);
    }

    @Override
    public Sighting deleteSightingById(int id) {
	return dao.deleteSightingById(id);
    }

    @Override
    public Sighting updateSightingById(Sighting sighting) {
	return dao.updateSightingById(sighting);
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
	return dao.addSighting(sighting);
    }

}
