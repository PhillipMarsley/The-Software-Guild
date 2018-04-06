/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.model.Hero;
import com.sg.model.Location;
import com.sg.model.Organization;
import com.sg.model.Sighting;
import com.sg.model.Superpower;
import com.sg.service.ServiceImpl;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dan
 */
@CrossOrigin
@RestController
public class RESTController {

    @Inject
    private ServiceImpl service;

    public RESTController() {
    }

    //==========================================================================
    //	    Locations
    //==========================================================================
    @GetMapping(value = "/html/locations")
    public List<Location> getAllLocations() {
	return service.getAllLocations();
    }

    @GetMapping(value = "/html/locations/{id}")
    public Location getLocation(@PathVariable("id") int id) {
	return service.getLocationById(id);
    }

    @DeleteMapping(value = "/html/locations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Location deleteLocation(@PathVariable("id") int id) {
	return service.deleteLocationById(id);
    }

    @PutMapping(value = "/html/locations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Location updateLocation(@PathVariable("id") int id, @RequestBody Location location) {
	return service.updateLocationById(location);
    }

    @PostMapping(value = "/html/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public Location addLocation(@RequestBody Location location) {
	return service.addLocation(location);
    }

    //==========================================================================
    //	    Superpowers
    //==========================================================================
    @GetMapping(value = "/html/superpowers")
    public List<Superpower> getAllSuperpowers() {
	return service.getAllSuperpowers();
    }

    @GetMapping(value = "/html/superpowers/hero/{id}")
    public List<Superpower> getAllSuperpowersByHeroId(@PathVariable("id") int id) {
	return service.getAllSuperpowersByHeroId(id);
    }

    @GetMapping(value = "/html/superpowers/{id}")
    public Superpower getSuperpower(@PathVariable("id") int id) {
	return service.getSuperpowerById(id);
    }

    @DeleteMapping(value = "/html/superpowers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Superpower deleteSuperpower(@PathVariable("id") int id) {
	return service.deleteSuperpowerById(id);
    }

    @PutMapping(value = "/html/superpowers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Superpower updateSuperpower(@PathVariable("id") int id, @RequestBody Superpower superpower) {
	return service.updateSuperpowerById(superpower);
    }

    @PostMapping(value = "/html/superpowers")
    @ResponseStatus(HttpStatus.CREATED)
    public Superpower addSuperpower(@RequestBody Superpower superpower) {
	return service.addSuperpower(superpower);
    }

    //==========================================================================
    //	    Organizations
    //==========================================================================
    @GetMapping(value = "/html/organizations")
    public List<Organization> getAllOrganizations() {
	return service.getAllOrganizations();
    }

    @GetMapping(value = "/html/organizations/hero/{id}")
    public List<Organization> getAllOrganizationsByHeroId(@PathVariable("id") int id) {
	return service.getAllOrganizationsByHeroId(id);
    }

    @GetMapping(value = "/html/organizations/{id}")
    public Organization getOrganization(@PathVariable("id") int id) {
	return service.getOrganizationById(id);
    }

    @DeleteMapping(value = "/html/organizations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Organization deleteOrganization(@PathVariable("id") int id) {
	return service.deleteOrganizationById(id);
    }

    @PutMapping(value = "/html/organizations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Organization updateOrganization(@PathVariable("id") int id, @RequestBody Organization organization) {
	return service.updateOrganizationById(organization);
    }

    @PostMapping(value = "/html/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    public Organization addOrganization(@RequestBody Organization organization) {
	return service.addOrganization(organization);
    }

    //==========================================================================
    //	    Heros
    //==========================================================================
    @GetMapping(value = "/html/heros")
    public List<Hero> getAllHeros() {
	return service.getAllHeros();
    }

    @GetMapping(value = "/html/hero/sighting/{id}")
    public List<Hero> getAllHerosBySightingId(@PathVariable("id") int id) {
	return service.getAllHerosBySightingId(id);
    }

    @GetMapping(value = "/html/heros/{id}")
    public Hero getHero(@PathVariable("id") int id) {
	return service.getHeroById(id);
    }

    @DeleteMapping(value = "/html/heros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Hero deleteHero(@PathVariable("id") int id) {
	return service.deleteHeroById(id);
    }

    @PutMapping(value = "/html/heros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Hero updateHero(@PathVariable("id") int id, @RequestBody Hero hero) {
	return service.updateHeroById(hero);
    }

    @PostMapping(value = "/html/heros")
    @ResponseStatus(HttpStatus.CREATED)
    public Hero addHero(@RequestBody Hero hero) {
	return service.addHero(hero);
    }

    //==========================================================================
    //	    Sightings
    //==========================================================================
    @GetMapping(value = "/html/sightings")
    public List<Sighting> getAllSightings() {
	return service.getAllSightings();
    }

    @GetMapping(value = "/html/sightings/{id}")
    public Sighting getSighting(@PathVariable("id") int id) {
	return service.getSightingById(id);
    }

    @DeleteMapping(value = "/html/sightings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Sighting deleteSighting(@PathVariable("id") int id) {
	return service.deleteSightingById(id);
    }

    @PutMapping(value = "/html/sightings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Sighting updateSighting(@PathVariable("id") int id, @RequestBody Sighting sighting) {
	return service.updateSightingById(sighting);
    }

    @PostMapping(value = "/html/sightings")
    @ResponseStatus(HttpStatus.CREATED)
    public Sighting addSighting(@RequestBody Sighting sighting) {
	return service.addSighting(sighting);
    }
}
