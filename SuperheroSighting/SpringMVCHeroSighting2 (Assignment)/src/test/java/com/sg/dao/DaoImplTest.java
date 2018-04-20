/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.Hero;
import com.sg.model.Location;
import com.sg.model.Organization;
import com.sg.model.Sighting;
import com.sg.model.Superpower;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Dan
 */
public class DaoImplTest {

    private Dao dao;
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public DaoImplTest() {
	ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
	dao = ctx.getBean("dao", Dao.class);
    }

    @Before
    public void setUp() {
	List<Sighting> sightings = dao.getAllSightings();
	for (Sighting s : sightings) {
	    dao.deleteSightingById(s.getId());
	}

	List<Hero> heros = dao.getAllHeros();
	for (Hero h : heros) {
	    dao.deleteHeroById(h.getId());
	}

	List<Superpower> superpowers = dao.getAllSuperpowers();
	for (Superpower s : superpowers) {
	    dao.deleteSuperpowerById(s.getId());
	}

	List<Organization> organizations = dao.getAllOrganizations();
	for (Organization o : organizations) {
	    dao.deleteOrganizationById(o.getId());
	}

	List<Location> locations = dao.getAllLocations();
	for (Location l : locations) {
	    dao.deleteLocationById(l.getId());
	}

    }

    @After
    public void tearDown() {
    }

    private Location addLocation() {
	Location l = new Location();
	//sql add: locationId
	l.setName("A Company");
	l.setDescription("A Description");
	l.setCountry("USA");
	l.setState("Minnesota");
	l.setCity("A City");
	l.setStreet("A Street");
	l.setZip("12345");
	l.setLatitude(new BigDecimal("123.123456"));
	l.setLongitude(new BigDecimal("987.987654"));

	return dao.addLocation(l);
    }

    private Organization addOrganization() {
	Location l = addLocation();

	Organization o = new Organization();
	//SQL set: organizationId
	o.setLocationId(l.getId());
	o.setLocation(l);
	o.setName("A Organization");
	o.setDescription("Cool Description");
	o.setPhone("651-555-5555");
	o.setEmail("Email@Email.com");

	return dao.addOrganization(o);
    }

    private Superpower addSuperpower() {
	Superpower s = new Superpower();
	//SQL set: superPowerId
	s.setType("Fire Bending");

	return dao.addSuperpower(s);
    }

    private Hero addHero() {
	List<Superpower> superpowers = new ArrayList();
	List<Organization> organizations = new ArrayList();

	superpowers.add(addSuperpower());

	organizations.add(addOrganization());
	organizations.add(addOrganization());
	organizations.add(addOrganization());

	Hero h = new Hero();
	//SQL set: heroId
	h.setName("Bob");
	h.setDescription("Suuuuuuper");
	h.setSuperpowers(superpowers);
	h.setOrganizations(organizations);

	return dao.addHero(h);
    }

    private Sighting addSighting() {
	Location l = addLocation();

	List<Hero> heros = new ArrayList();
	heros.add(addHero());
	heros.add(addHero());
	heros.add(addHero());

	Sighting s = new Sighting();
	//SQL set: sightingId
	s.setLocationId(l.getId());
	s.setLocation(l);
	s.setDate(LocalDate.of(1000, Month.MARCH, 8));	// January 1, 1753 > date < December 31, 9999
	s.setTime(LocalTime.of(8, 8, 8));		// 00:00:00 > time < 23:59:59
	s.setHeros(heros);

	return dao.addSighting(s);
    }

    //==========================================================================
    //	    Hero
    //==========================================================================
    @Test
    public void testGetAllHeros() {
	Hero h = addHero();

	assertEquals(1, dao.getAllHeros().size());
	assertEquals(h, dao.getAllHeros().get(0));
	assertEquals(true, dao.getAllHeros().contains(h));

	Hero hh = addHero();

	assertEquals(2, dao.getAllHeros().size());
	assertEquals(h, dao.getAllHeros().get(0));
	assertEquals(hh, dao.getAllHeros().get(1));
	assertEquals(true, dao.getAllHeros().contains(h));
	assertEquals(true, dao.getAllHeros().contains(hh));

	Hero hhh = addHero();

	assertEquals(3, dao.getAllHeros().size());
	assertEquals(h, dao.getAllHeros().get(0));
	assertEquals(hh, dao.getAllHeros().get(1));
	assertEquals(hhh, dao.getAllHeros().get(2));
	assertEquals(true, dao.getAllHeros().contains(h));
	assertEquals(true, dao.getAllHeros().contains(hh));
	assertEquals(true, dao.getAllHeros().contains(hhh));
    }

    @Test
    public void testGetAllHerosBySuperpowerId() {
	Hero h = addHero();
	int superpowerId = h.getSuperpowers().get(0).getId();

	assertEquals(1, dao.getAllHerosBySuperpowerId(superpowerId).size());
	assertEquals(h, dao.getAllHerosBySuperpowerId(superpowerId).get(0));
	assertEquals(true, dao.getAllHerosBySuperpowerId(superpowerId).contains(h));

	assertEquals(true, dao.getAllHerosBySuperpowerId(-10).isEmpty());
    }

    @Test
    public void testGetAllHerosByOrganizationId() {
	Hero h = addHero();
	int organizationId = h.getOrganizations().get(0).getId();

	assertEquals(1, dao.getAllHerosByOrganizationId(organizationId).size());
	assertEquals(h, dao.getAllHerosByOrganizationId(organizationId).get(0));
	assertEquals(true, dao.getAllHerosByOrganizationId(organizationId).contains(h));

	assertEquals(true, dao.getAllHerosByOrganizationId(-10).isEmpty());
    }

    @Test
    public void testGetAllHerosBySightingId() {
	Sighting s = addSighting();
	int id = s.getId();

	assertEquals(3, dao.getAllHerosBySightingId(id).size());

	assertEquals(s.getHeros().get(0), dao.getAllHerosBySightingId(id).get(0));
	assertEquals(s.getHeros().get(1), dao.getAllHerosBySightingId(id).get(1));
	assertEquals(s.getHeros().get(2), dao.getAllHerosBySightingId(id).get(2));

	assertEquals(true, dao.getAllHerosBySightingId(-100).isEmpty());

	assertEquals(true, dao.getAllHerosBySightingId(id).contains(s.getHeros().get(0)));
	assertEquals(true, dao.getAllHerosBySightingId(id).contains(s.getHeros().get(1)));
	assertEquals(true, dao.getAllHerosBySightingId(id).contains(s.getHeros().get(2)));
    }

    @Test
    public void testGetHeroById() {
	Hero h = addHero();
	Hero hh = addHero();
	Hero hhh = addHero();

	assertEquals(h, dao.getHeroById(h.getId()));
	assertEquals(hh, dao.getHeroById(hh.getId()));
	assertEquals(hhh, dao.getHeroById(hhh.getId()));

	assertEquals(null, dao.getHeroById(-10));
    }

    @Test
    public void testDeleteHeroById() {
	Hero h = addHero();
	Hero hh = addHero();
	Hero hhh = addHero();

	assertEquals(3, dao.getAllHeros().size());

	dao.deleteHeroById(h.getId());

	assertEquals(2, dao.getAllHeros().size());
	assertEquals(false, dao.getAllHeros().contains(h));
	assertEquals(true, dao.getAllHeros().contains(hh));
	assertEquals(true, dao.getAllHeros().contains(hhh));

	dao.deleteHeroById(hh.getId());

	assertEquals(1, dao.getAllHeros().size());
	assertEquals(false, dao.getAllHeros().contains(hh));
	assertEquals(true, dao.getAllHeros().contains(hhh));

	dao.deleteHeroById(hhh.getId());

	assertEquals(true, dao.getAllHeros().isEmpty());
	assertEquals(false, dao.getAllHeros().contains(hhh));
    }

    @Test
    public void testUpdateHeroById() {
	Hero h = addHero();

	String name = "A Name";
	String description = "Super Cool";

	h.setName(name);
	h.setDescription(description);

	dao.updateHeroById(h);

	assertEquals(name, dao.getHeroById(h.getId()).getName());
	assertNotEquals("ASD", dao.getHeroById(h.getId()).getName());

	assertEquals(description, dao.getHeroById(h.getId()).getDescription());
	assertNotEquals("ASD ASD", dao.getHeroById(h.getId()).getDescription());

	assertEquals(true, dao.getAllHeros().contains(h));
    }

    @Test
    public void testAddHero() {
	Hero h = new Hero();
	Hero hh = new Hero();
	Hero hhh = new Hero();

	assertEquals(false, dao.getAllHeros().contains(h));
	assertEquals(false, dao.getAllHeros().contains(hh));
	assertEquals(false, dao.getAllHeros().contains(hhh));

	h = addHero();

	assertEquals(true, dao.getAllHeros().contains(h));
	assertEquals(false, dao.getAllHeros().contains(hh));
	assertEquals(false, dao.getAllHeros().contains(hhh));

	hh = addHero();

	assertEquals(true, dao.getAllHeros().contains(h));
	assertEquals(true, dao.getAllHeros().contains(hh));
	assertEquals(false, dao.getAllHeros().contains(hhh));

	hhh = addHero();

	assertEquals(true, dao.getAllHeros().contains(h));
	assertEquals(true, dao.getAllHeros().contains(hh));
	assertEquals(true, dao.getAllHeros().contains(hhh));

	assertNotEquals(h, hh);
	assertNotEquals(h, hhh);
	assertNotEquals(hh, hhh);

	assertNotEquals(dao.getHeroById(h.getId()), dao.getHeroById(hh.getId()));
	assertNotEquals(dao.getHeroById(h.getId()), dao.getHeroById(hhh.getId()));
	assertNotEquals(dao.getHeroById(hh.getId()), dao.getHeroById(hhh.getId()));
    }
    //==========================================================================
    //	    Superpower
    //==========================================================================
    @Test
    public void testGetAllSuperpowers() {
	Superpower s = addSuperpower();

	assertEquals(1, dao.getAllSuperpowers().size());
	assertEquals(s, dao.getAllSuperpowers().get(0));
	assertEquals(true, dao.getAllSuperpowers().contains(s));

	Superpower ss = addSuperpower();

	assertEquals(2, dao.getAllSuperpowers().size());
	assertEquals(s, dao.getAllSuperpowers().get(0));
	assertEquals(ss, dao.getAllSuperpowers().get(1));
	assertEquals(true, dao.getAllSuperpowers().contains(s));
	assertEquals(true, dao.getAllSuperpowers().contains(ss));

	Superpower sss = addSuperpower();

	assertEquals(3, dao.getAllSuperpowers().size());
	assertEquals(s, dao.getAllSuperpowers().get(0));
	assertEquals(ss, dao.getAllSuperpowers().get(1));
	assertEquals(sss, dao.getAllSuperpowers().get(2));
	assertEquals(true, dao.getAllSuperpowers().contains(s));
	assertEquals(true, dao.getAllSuperpowers().contains(ss));
	assertEquals(true, dao.getAllSuperpowers().contains(sss));
    }

    @Test
    public void testGetAllSuperpowersByHeroId() {
	Hero h = addHero();

	assertEquals(1, dao.getAllSuperpowersByHeroId(h.getId()).size());

	Hero hh = addHero();

	Superpower s = addSuperpower();
	Superpower ss = addSuperpower();
	Superpower sss = addSuperpower();

	List<Superpower> superpowers = new ArrayList<>();
	superpowers.add(s);
	superpowers.add(ss);
	superpowers.add(sss);

	hh.setSuperpowers(superpowers);
	dao.updateHeroById(hh);

	assertEquals(3, dao.getAllSuperpowersByHeroId(hh.getId()).size());
	
	assertEquals(s, dao.getAllSuperpowersByHeroId(hh.getId()).get(0));
	assertEquals(ss, dao.getAllSuperpowersByHeroId(hh.getId()).get(1));
	assertEquals(sss, dao.getAllSuperpowersByHeroId(hh.getId()).get(2));
	
	assertEquals(true, dao.getAllSuperpowersByHeroId(hh.getId()).contains(s));
	assertEquals(true, dao.getAllSuperpowersByHeroId(hh.getId()).contains(ss));
	assertEquals(true, dao.getAllSuperpowersByHeroId(hh.getId()).contains(sss));
	
	assertNotEquals(s, ss);
	assertNotEquals(s, sss);
	assertNotEquals(ss, sss);
	
	assertNotEquals(dao.getSuperpowerById(s.getId()), dao.getSuperpowerById(ss.getId()));
	assertNotEquals(dao.getSuperpowerById(s.getId()), dao.getSuperpowerById(sss.getId()));
	assertNotEquals(dao.getSuperpowerById(ss.getId()), dao.getSuperpowerById(sss.getId()));
    }

    @Test
    public void testGetSuperpowerById() {
	Superpower s = addSuperpower();
	Superpower ss = addSuperpower();
	Superpower sss = addSuperpower();

	assertEquals(s, dao.getSuperpowerById(s.getId()));
	assertEquals(ss, dao.getSuperpowerById(ss.getId()));
	assertEquals(sss, dao.getSuperpowerById(sss.getId()));

	assertEquals(null, dao.getSuperpowerById(-10));
    }

    @Test
    public void testDeleteSuperpowerById() {
	Superpower s = addSuperpower();
	Superpower ss = addSuperpower();
	Superpower sss = addSuperpower();

	assertEquals(3, dao.getAllSuperpowers().size());

	dao.deleteSuperpowerById(s.getId());

	assertEquals(2, dao.getAllSuperpowers().size());
	assertEquals(false, dao.getAllSuperpowers().contains(s));
	assertEquals(true, dao.getAllSuperpowers().contains(ss));
	assertEquals(true, dao.getAllSuperpowers().contains(sss));

	dao.deleteSuperpowerById(ss.getId());

	assertEquals(1, dao.getAllSuperpowers().size());
	assertEquals(false, dao.getAllSuperpowers().contains(ss));
	assertEquals(true, dao.getAllSuperpowers().contains(sss));

	dao.deleteSuperpowerById(sss.getId());

	assertEquals(true, dao.getAllSuperpowers().isEmpty());
	assertEquals(false, dao.getAllSuperpowers().contains(sss));
    }

    @Test
    public void testUpdateSuperpowerById() {
	Superpower s = addSuperpower();

	String type = "Super Cool Superpower";

	s.setType(type);

	dao.updateSuperpowerById(s);

	assertEquals(type, dao.getSuperpowerById(s.getId()).getType());
	assertNotEquals("ASD", dao.getSuperpowerById(s.getId()).getType());

	assertEquals(true, dao.getAllSuperpowers().contains(s));
    }

    @Test
    public void testAddSuperpower() {
	Superpower s = new Superpower();
	Superpower ss = new Superpower();
	Superpower sss = new Superpower();

	assertEquals(false, dao.getAllSuperpowers().contains(s));
	assertEquals(false, dao.getAllSuperpowers().contains(ss));
	assertEquals(false, dao.getAllSuperpowers().contains(sss));

	s = addSuperpower();

	assertEquals(true, dao.getAllSuperpowers().contains(s));
	assertEquals(false, dao.getAllSuperpowers().contains(ss));
	assertEquals(false, dao.getAllSuperpowers().contains(sss));

	ss = addSuperpower();

	assertEquals(true, dao.getAllSuperpowers().contains(s));
	assertEquals(true, dao.getAllSuperpowers().contains(ss));
	assertEquals(false, dao.getAllSuperpowers().contains(sss));

	sss = addSuperpower();

	assertEquals(true, dao.getAllSuperpowers().contains(s));
	assertEquals(true, dao.getAllSuperpowers().contains(ss));
	assertEquals(true, dao.getAllSuperpowers().contains(sss));

	assertNotEquals(s, ss);
	assertNotEquals(s, sss);
	assertNotEquals(ss, sss);

	assertNotEquals(dao.getSuperpowerById(s.getId()), dao.getSuperpowerById(ss.getId()));
	assertNotEquals(dao.getSuperpowerById(s.getId()), dao.getSuperpowerById(sss.getId()));
	assertNotEquals(dao.getSuperpowerById(ss.getId()), dao.getSuperpowerById(sss.getId()));
    }
    //==========================================================================
    //	    Location
    //==========================================================================
    @Test
    public void testGetAllLocations() {
	Location l = addLocation();

	assertEquals(1, dao.getAllLocations().size());
	assertEquals(l, dao.getAllLocations().get(0));
	assertEquals(true, dao.getAllLocations().contains(l));

	Location ll = addLocation();

	assertEquals(2, dao.getAllLocations().size());
	assertEquals(l, dao.getAllLocations().get(0));
	assertEquals(ll, dao.getAllLocations().get(1));
	assertEquals(true, dao.getAllLocations().contains(l));
	assertEquals(true, dao.getAllLocations().contains(ll));

	Location lll = addLocation();

	assertEquals(3, dao.getAllLocations().size());
	assertEquals(l, dao.getAllLocations().get(0));
	assertEquals(ll, dao.getAllLocations().get(1));
	assertEquals(lll, dao.getAllLocations().get(2));
	assertEquals(true, dao.getAllLocations().contains(l));
	assertEquals(true, dao.getAllLocations().contains(ll));
	assertEquals(true, dao.getAllLocations().contains(lll));
    }

    @Test
    public void testGetLocationById() {
	Location l = addLocation();
	Location ll = addLocation();
	Location lll = addLocation();

	assertEquals(l, dao.getLocationById(l.getId()));
	assertEquals(ll, dao.getLocationById(ll.getId()));
	assertEquals(lll, dao.getLocationById(lll.getId()));

	assertEquals(null, dao.getLocationById(-10));
    }

    @Test
    public void testDeleteLocationById() {
	Location l = addLocation();
	Location ll = addLocation();
	Location lll = addLocation();

	assertEquals(3, dao.getAllLocations().size());

	dao.deleteLocationById(l.getId());

	assertEquals(2, dao.getAllLocations().size());
	assertEquals(false, dao.getAllLocations().contains(l));
	assertEquals(true, dao.getAllLocations().contains(ll));
	assertEquals(true, dao.getAllLocations().contains(lll));

	dao.deleteLocationById(ll.getId());

	assertEquals(1, dao.getAllLocations().size());
	assertEquals(false, dao.getAllLocations().contains(ll));
	assertEquals(true, dao.getAllLocations().contains(lll));

	dao.deleteLocationById(lll.getId());

	assertEquals(true, dao.getAllLocations().isEmpty());
	assertEquals(false, dao.getAllLocations().contains(lll));
    }

    @Test
    public void testUpdateLocationById() {
	Location l = addLocation();

	String name = "A cool name";
	String description = "A cool description.";
	String country = "CountryName";
	String state = "StateName";
	String city = "CityName";
	String street = "StreetName";
	String zip = "AAA ZZZ";
	BigDecimal lati = new BigDecimal("333.666666");
	BigDecimal longi = new BigDecimal("666.121212");
	
	l.setName(name);
	l.setDescription(description);
	l.setCountry(country);
	l.setState(state);
	l.setCity(city);
	l.setStreet(street);
	l.setZip(zip);
	l.setLatitude(lati);
	l.setLongitude(longi);

	dao.updateLocationById(l);

	assertEquals(name, dao.getLocationById(l.getId()).getName());
	assertEquals(description, dao.getLocationById(l.getId()).getDescription());
	assertEquals(country, dao.getLocationById(l.getId()).getCountry());
	assertEquals(state, dao.getLocationById(l.getId()).getState());
	assertEquals(city, dao.getLocationById(l.getId()).getCity());
	assertEquals(street, dao.getLocationById(l.getId()).getStreet());
	assertEquals(zip, dao.getLocationById(l.getId()).getZip());
	assertEquals(lati, dao.getLocationById(l.getId()).getLatitude());
	assertEquals(longi, dao.getLocationById(l.getId()).getLongitude());

	assertEquals(true, dao.getAllLocations().contains(l));
    }

    @Test
    public void testAddLocation() {
	Location l = new Location();
	Location ll = new Location();
	Location lll = new Location();

	assertEquals(false, dao.getAllLocations().contains(l));
	assertEquals(false, dao.getAllLocations().contains(ll));
	assertEquals(false, dao.getAllLocations().contains(lll));

	l = addLocation();

	assertEquals(true, dao.getAllLocations().contains(l));
	assertEquals(false, dao.getAllLocations().contains(ll));
	assertEquals(false, dao.getAllLocations().contains(lll));

	ll = addLocation();

	assertEquals(true, dao.getAllLocations().contains(l));
	assertEquals(true, dao.getAllLocations().contains(ll));
	assertEquals(false, dao.getAllLocations().contains(lll));

	lll = addLocation();

	assertEquals(true, dao.getAllLocations().contains(l));
	assertEquals(true, dao.getAllLocations().contains(ll));
	assertEquals(true, dao.getAllLocations().contains(lll));

	assertNotEquals(l, ll);
	assertNotEquals(l, lll);
	assertNotEquals(ll, lll);

	assertNotEquals(dao.getLocationById(l.getId()), dao.getLocationById(ll.getId()));
	assertNotEquals(dao.getLocationById(l.getId()), dao.getLocationById(lll.getId()));
	assertNotEquals(dao.getLocationById(ll.getId()), dao.getLocationById(lll.getId()));
    }
    //==========================================================================
    //	    Organization
    //==========================================================================
    @Test
    public void testGetAllOrganizations() {
	Organization o = addOrganization();

	assertEquals(1, dao.getAllOrganizations().size());
	assertEquals(o, dao.getAllOrganizations().get(0));
	assertEquals(true, dao.getAllOrganizations().contains(o));

	Organization oo = addOrganization();

	assertEquals(2, dao.getAllOrganizations().size());
	assertEquals(o, dao.getAllOrganizations().get(0));
	assertEquals(oo, dao.getAllOrganizations().get(1));
	assertEquals(true, dao.getAllOrganizations().contains(o));
	assertEquals(true, dao.getAllOrganizations().contains(oo));

	Organization ooo = addOrganization();

	assertEquals(3, dao.getAllOrganizations().size());
	assertEquals(o, dao.getAllOrganizations().get(0));
	assertEquals(oo, dao.getAllOrganizations().get(1));
	assertEquals(ooo, dao.getAllOrganizations().get(2));
	assertEquals(true, dao.getAllOrganizations().contains(o));
	assertEquals(true, dao.getAllOrganizations().contains(oo));
	assertEquals(true, dao.getAllOrganizations().contains(ooo));
    }

    @Test
    public void testGetAllOrganizationsByLocationId() {
	Organization o = addOrganization();
	int id = o.getLocationId();
	Location l = o.getLocation();
	
	assertEquals(1, dao.getAllOrganizationsByLocationId(id).size());
	assertEquals(o, dao.getAllOrganizationsByLocationId(id).get(0));
	assertEquals(l, dao.getAllOrganizationsByLocationId(id).get(0).getLocation());
	assertEquals(id, dao.getAllOrganizationsByLocationId(id).get(0).getLocationId());
	
	assertEquals(true, dao.getAllOrganizationsByLocationId(-10).isEmpty());
    }

    @Test
    public void getAllOrganizationsByHeroId() {
	Hero h = addHero();
	int id = h.getId();
	
	assertEquals(3, dao.getAllOrganizationsByHeroId(id).size());
	
	assertEquals(h.getOrganizations().get(0), dao.getAllOrganizationsByHeroId(id).get(0));
	assertEquals(h.getOrganizations().get(1), dao.getAllOrganizationsByHeroId(id).get(1));
	assertEquals(h.getOrganizations().get(2), dao.getAllOrganizationsByHeroId(id).get(2));
	
	assertNotEquals(h.getOrganizations().get(0), h.getOrganizations().get(1));
	assertNotEquals(h.getOrganizations().get(0), h.getOrganizations().get(2));
	assertNotEquals(h.getOrganizations().get(1), h.getOrganizations().get(2));
	
	assertEquals(true, dao.getAllOrganizationsByHeroId(-10).isEmpty());
    }

    @Test
    public void testGetOrganizationById() {
	Organization o = addOrganization();
	Organization oo = addOrganization();
	Organization ooo = addOrganization();

	assertEquals(o, dao.getOrganizationById(o.getId()));
	assertEquals(oo, dao.getOrganizationById(oo.getId()));
	assertEquals(ooo, dao.getOrganizationById(ooo.getId()));

	assertEquals(null, dao.getOrganizationById(-10));
    }

    @Test
    public void testDeleteOrganizationById() {
	Organization o = addOrganization();
	Organization oo = addOrganization();
	Organization ooo = addOrganization();

	assertEquals(3, dao.getAllOrganizations().size());

	dao.deleteOrganizationById(o.getId());

	assertEquals(2, dao.getAllOrganizations().size());
	assertEquals(false, dao.getAllOrganizations().contains(o));
	assertEquals(true, dao.getAllOrganizations().contains(oo));
	assertEquals(true, dao.getAllOrganizations().contains(ooo));

	dao.deleteOrganizationById(oo.getId());

	assertEquals(1, dao.getAllOrganizations().size());
	assertEquals(false, dao.getAllOrganizations().contains(oo));
	assertEquals(true, dao.getAllOrganizations().contains(ooo));

	dao.deleteOrganizationById(ooo.getId());

	assertEquals(true, dao.getAllOrganizations().isEmpty());
	assertEquals(false, dao.getAllOrganizations().contains(ooo));
    }

    @Test
    public void testUpdateOrganizationById() {
	Organization o = addOrganization();

	Location l = addLocation();
	int locationId = l.getId();
	String name = "A Name";
	String description = "Super Cool";
	String phone = "1-666-4444";
	String email = "email@eeeemail.com";

	o.setLocationId(locationId);
	o.setLocation(l);
	o.setName(name);
	o.setDescription(description);
	o.setPhone(phone);
	o.setEmail(email);

	dao.updateOrganizationById(o);

	assertEquals(locationId, dao.getOrganizationById(o.getId()).getLocationId());
	assertEquals(l, dao.getOrganizationById(o.getId()).getLocation());
	assertEquals(name, dao.getOrganizationById(o.getId()).getName());
	assertEquals(description, dao.getOrganizationById(o.getId()).getDescription());
	assertEquals(phone, dao.getOrganizationById(o.getId()).getPhone());
	assertEquals(email, dao.getOrganizationById(o.getId()).getEmail());

	assertEquals(true, dao.getAllOrganizations().contains(o));
    }

    @Test
    public void testAddOrganization() {
	Organization o = new Organization();
	Organization oo = new Organization();
	Organization ooo = new Organization();

	assertEquals(false, dao.getAllOrganizations().contains(o));
	assertEquals(false, dao.getAllOrganizations().contains(oo));
	assertEquals(false, dao.getAllOrganizations().contains(ooo));

	o = addOrganization();

	assertEquals(true, dao.getAllOrganizations().contains(o));
	assertEquals(false, dao.getAllOrganizations().contains(oo));
	assertEquals(false, dao.getAllOrganizations().contains(ooo));

	oo = addOrganization();

	assertEquals(true, dao.getAllOrganizations().contains(o));
	assertEquals(true, dao.getAllOrganizations().contains(oo));
	assertEquals(false, dao.getAllOrganizations().contains(ooo));

	ooo = addOrganization();

	assertEquals(true, dao.getAllOrganizations().contains(o));
	assertEquals(true, dao.getAllOrganizations().contains(oo));
	assertEquals(true, dao.getAllOrganizations().contains(ooo));

	assertNotEquals(o, oo);
	assertNotEquals(o, ooo);
	assertNotEquals(oo, ooo);

	assertNotEquals(dao.getOrganizationById(o.getId()), dao.getOrganizationById(oo.getId()));
	assertNotEquals(dao.getOrganizationById(o.getId()), dao.getOrganizationById(ooo.getId()));
	assertNotEquals(dao.getOrganizationById(oo.getId()), dao.getOrganizationById(ooo.getId()));
    }
    //==========================================================================
    //	   Sighting
    //==========================================================================
    @Test
    public void testGetAllSightings() {
	Sighting s = addSighting();

	assertEquals(1, dao.getAllSightings().size());
	assertEquals(s, dao.getAllSightings().get(0));
	assertEquals(true, dao.getAllSightings().contains(s));

	Sighting ss = addSighting();

	assertEquals(2, dao.getAllSightings().size());
	assertEquals(s, dao.getAllSightings().get(0));
	assertEquals(ss, dao.getAllSightings().get(1));
	assertEquals(true, dao.getAllSightings().contains(s));
	assertEquals(true, dao.getAllSightings().contains(ss));

	Sighting sss = addSighting();

	assertEquals(3, dao.getAllSightings().size());
	assertEquals(s, dao.getAllSightings().get(0));
	assertEquals(ss, dao.getAllSightings().get(1));
	assertEquals(sss, dao.getAllSightings().get(2));
	assertEquals(true, dao.getAllSightings().contains(s));
	assertEquals(true, dao.getAllSightings().contains(ss));
	assertEquals(true, dao.getAllSightings().contains(sss));
    }

    @Test
    public void getAllSightingsByHeroId() {
	Sighting s = addSighting();
	Sighting ss = addSighting();
	Sighting sss = addSighting();

	Hero h = addHero();
	List<Hero> heros = new ArrayList<>();
	heros.add(h);

	s.setHeros(heros);
	ss.setHeros(heros);
	sss.setHeros(heros);

	dao.updateSightingById(s);
	dao.updateSightingById(ss);
	dao.updateSightingById(sss);

	int heroId = s.getHeros().get(0).getId();

	assertEquals(3, dao.getAllSightingsByHeroId(heroId).size());
	assertEquals(h, dao.getAllSightingsByHeroId(heroId).get(0).getHeros().get(0));
	assertEquals(h, dao.getAllSightingsByHeroId(heroId).get(1).getHeros().get(0));
	assertEquals(h, dao.getAllSightingsByHeroId(heroId).get(2).getHeros().get(0));

	assertEquals(true, dao.getAllSightingsByHeroId(-10).isEmpty());
    }

    @Test
    public void testGetAllSigstingsByLocationId() {
	Sighting s = addSighting();
	Sighting ss = addSighting();
	Sighting sss = addSighting();

	Location l = addLocation();
	int id = l.getId();

	s.setLocation(l);
	s.setLocationId(id);

	ss.setLocation(l);
	ss.setLocationId(id);

	sss.setLocation(l);
	sss.setLocationId(id);
		
	dao.updateSightingById(s);
	dao.updateSightingById(ss);
	dao.updateSightingById(sss);

	assertEquals(3, dao.getAllSightingsByLocationId(id).size());
	
	assertEquals(l, dao.getAllSightingsByLocationId(id).get(0).getLocation());
	assertEquals(id, dao.getAllSightingsByLocationId(id).get(0).getLocationId());
	
	assertEquals(l, dao.getAllSightingsByLocationId(id).get(1).getLocation());
	assertEquals(id, dao.getAllSightingsByLocationId(id).get(1).getLocationId());
	
	assertEquals(l, dao.getAllSightingsByLocationId(id).get(2).getLocation());
	assertEquals(id, dao.getAllSightingsByLocationId(id).get(2).getLocationId());

	assertEquals(true, dao.getAllSightingsByLocationId(-10).isEmpty());
    }

    @Test
    public void testGetSigstingById() {
	Sighting s = addSighting();
	Sighting ss = addSighting();
	Sighting sss = addSighting();

	assertEquals(s, dao.getSightingById(s.getId()));
	assertEquals(ss, dao.getSightingById(ss.getId()));
	assertEquals(sss, dao.getSightingById(sss.getId()));

	assertEquals(null, dao.getSightingById(-10));
    }

    @Test
    public void testDeleteSigstingById() {
	Sighting s = addSighting();
	Sighting ss = addSighting();
	Sighting sss = addSighting();

	assertEquals(3, dao.getAllSightings().size());

	dao.deleteSightingById(s.getId());

	assertEquals(2, dao.getAllSightings().size());
	assertEquals(false, dao.getAllSightings().contains(s));
	assertEquals(true, dao.getAllSightings().contains(ss));
	assertEquals(true, dao.getAllSightings().contains(sss));

	dao.deleteSightingById(ss.getId());

	assertEquals(1, dao.getAllSightings().size());
	assertEquals(false, dao.getAllSightings().contains(ss));
	assertEquals(true, dao.getAllSightings().contains(sss));

	dao.deleteSightingById(sss.getId());

	assertEquals(true, dao.getAllSightings().isEmpty());
	assertEquals(false, dao.getAllSightings().contains(sss));
    }

    @Test
    public void testUpdateSigstingById() {
	Sighting s = addSighting();

	Location l = addLocation();
	int id = l.getId();

	List<Hero> heros = new ArrayList<>();
	heros.add(addHero());
	heros.add(addHero());
	heros.add(addHero());

	LocalDate date = LocalDate.of(3000, 3, 3);
	LocalTime time = LocalTime.of(3, 3, 3);

	s.setLocation(l);
	s.setLocationId(id);
	s.setHeros(heros);
	s.setDate(date);
	s.setTime(time);

	dao.updateSightingById(s);

	assertEquals(l, dao.getSightingById(s.getId()).getLocation());
	assertEquals(id, dao.getSightingById(s.getId()).getLocationId());
	assertEquals(heros, dao.getSightingById(s.getId()).getHeros());
	assertEquals(date, dao.getSightingById(s.getId()).getDate());
	assertEquals(time, dao.getSightingById(s.getId()).getTime());

	assertEquals(true, dao.getAllSightings().contains(s));
    }

    @Test
    public void testAddSigsting() {
	Sighting s = new Sighting();
	Sighting ss = new Sighting();
	Sighting sss = new Sighting();

	assertEquals(false, dao.getAllSightings().contains(s));
	assertEquals(false, dao.getAllSightings().contains(ss));
	assertEquals(false, dao.getAllSightings().contains(sss));

	s = addSighting();

	assertEquals(true, dao.getAllSightings().contains(s));
	assertEquals(false, dao.getAllSightings().contains(ss));
	assertEquals(false, dao.getAllSightings().contains(sss));

	ss = addSighting();

	assertEquals(true, dao.getAllSightings().contains(s));
	assertEquals(true, dao.getAllSightings().contains(ss));
	assertEquals(false, dao.getAllSightings().contains(sss));

	sss = addSighting();

	assertEquals(true, dao.getAllSightings().contains(s));
	assertEquals(true, dao.getAllSightings().contains(ss));
	assertEquals(true, dao.getAllSightings().contains(sss));

	assertNotEquals(s, ss);
	assertNotEquals(s, sss);
	assertNotEquals(ss, sss);

	assertNotEquals(dao.getSightingById(s.getId()), dao.getSightingById(ss.getId()));
	assertNotEquals(dao.getSightingById(s.getId()), dao.getSightingById(sss.getId()));
	assertNotEquals(dao.getSightingById(ss.getId()), dao.getSightingById(sss.getId()));
    }
}
