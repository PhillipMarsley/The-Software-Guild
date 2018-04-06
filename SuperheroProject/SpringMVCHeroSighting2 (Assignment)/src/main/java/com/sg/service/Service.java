/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.model.Hero;
import com.sg.model.Location;
import com.sg.model.Organization;
import com.sg.model.Sighting;
import com.sg.model.Superpower;
import java.util.List;

/**
 *
 * @author Dan
 */
public interface Service {
    //==========================================================================
    //	    Hero
    //==========================================================================
    public List<Hero> getAllHeros();
    
    public List<Hero> getAllHerosBySuperpowerId(int id);

    public List<Hero> getAllHerosByOrganizationId(int id);
    
    public List<Hero> getAllHerosBySightingId(int id);
    
    public Hero getHeroById(int id);

    public Hero deleteHeroById(int id);

    public Hero updateHeroById(Hero hero);

    public Hero addHero(Hero hero);

    //==========================================================================
    //	    Superpower
    //==========================================================================
    public List<Superpower> getAllSuperpowers();

    public List<Superpower> getAllSuperpowersByHeroId(int id);
    
    public Superpower getSuperpowerById(int id);

    public Superpower deleteSuperpowerById(int id);

    public Superpower updateSuperpowerById(Superpower superpower);

    public Superpower addSuperpower(Superpower superpower);

    //==========================================================================
    //	    Location
    //==========================================================================
    public List<Location> getAllLocations();

    public Location getLocationById(int id);

    public Location deleteLocationById(int id);

    public Location updateLocationById(Location location);

    public Location addLocation(Location location);

    //==========================================================================
    //	    Organization
    //==========================================================================
    public List<Organization> getAllOrganizations();

    public List<Organization> getAllOrganizationsByLocationId(int id);

    public List<Organization> getAllOrganizationsByHeroId(int id);
    
    public Organization getOrganizationById(int id);

    public Organization deleteOrganizationById(int id);

    public Organization updateOrganizationById(Organization organization);

    public Organization addOrganization(Organization organization);

    //==========================================================================
    //	    Sighting
    //==========================================================================
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getAllSightingsByHeroId(int id);
    
    public List<Sighting> getAllSightingsByLocationId(int id);

    public Sighting getSightingById(int id);

    public Sighting deleteSightingById(int id);

    public Sighting updateSightingById(Sighting sighting);

    public Sighting addSighting(Sighting sighting);
}
