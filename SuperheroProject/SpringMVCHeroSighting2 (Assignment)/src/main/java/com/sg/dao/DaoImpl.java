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
import com.sg.model.TableObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dan
 */
public class DaoImpl implements Dao {

    JdbcTemplate jdbcTemplate;

    public DaoImpl() {

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    private void setId(TableObject o) {
	int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	o.setId(id);
    }

    //==========================================================================
    //	    Hero
    //==========================================================================
    private static final String GET_ALL_HEROS
	    = "SELECT * "
	    + "FROM hero";

    private static final String GET_ALL_HEROS_BY_SUPERPOWER
	    = "SELECT h.* "
	    + "FROM heroSuperpower hs "
	    + "JOIN hero h ON h.heroId = hs.heroId "
	    + "WHERE hs.superpowerId = ?";

    private static final String GET_ALL_HEROS_BY_ORGANIZATION
	    = "SELECT h.* "
	    + "FROM heroOrganization ho "
	    + "JOIN hero h ON h.heroId = ho.heroId "
	    + "WHERE ho.organizationId = ?";

    private static final String GET_ALL_HEROS_BY_SIGHTING
	    = "SELECT h.* "
	    + "FROM hero h "
	    + "JOIN heroSighting hs ON hs.heroId = h.heroId "
	    + "WHERE hs.sightingId = ?";

    private static final String GET_HERO
	    = "SELECT * "
	    + "FROM hero "
	    + "WHERE heroId = ?";

    private static final String DELETE_HERO
	    = "DELETE "
	    + "FROM hero "
	    + "WHERE heroId = ?";

    private static final String UPDATE_HERO
	    = "UPDATE hero "
	    + "SET name = ?, description = ? "
	    + "WHERE heroId = ?";

    private static final String ADD_HERO
	    = "INSERT "
	    + "INTO hero "
	    + "(name, description) "
	    + "VALUES (?, ?)";

    //==========================================================================
    //	    Hero Superpower Bridge
    //==========================================================================
    private static final String ADD_BRIDGE_HERO_SUPERPOWER_IDS
	    = "INSERT "
	    + "INTO heroSuperpower "
	    + "(heroId, superpowerId) "
	    + "VALUES (?, ?)";

    private static final String DELETE_HERO_FROM_BRIDGE_HERO_SUPERPOWER
	    = "DELETE "
	    + "FROM heroSuperpower "
	    + "WHERE heroId = ?";

    private static final String DELETE_SUPERPOWER_FROM_BRIDGE_HERO_SUPERPOWER
	    = "DELETE "
	    + "FROM heroSuperpower "
	    + "WHERE superpowerId = ?";

    //==========================================================================
    //	    Superpower
    //==========================================================================
    private static final String GET_ALL_SUPERPOWERS
	    = "SELECT * "
	    + "FROM superpower";

    private static final String GET_ALL_SUPERPOWERS_BY_HERO
	    = "SELECT s.* "
	    + "FROM superpower s "
	    + "JOIN heroSuperpower hs ON hs.superpowerId = s.superpowerId "
	    + "WHERE hs.heroId = ?";

    private static final String GET_SUPERPOWER
	    = "SELECT * "
	    + "FROM superpower "
	    + "WHERE superpowerId = ?";

    private static final String DELETE_SUPERPOWER
	    = "DELETE "
	    + "FROM superpower "
	    + "WHERE superpowerId = ?";

    private static final String UPDATE_SUPERPOWER
	    = "UPDATE superpower "
	    + "SET type = ? "
	    + "WHERE superpowerId = ?";

    private static final String ADD_SUPERPOWER
	    = "INSERT "
	    + "INTO superpower "
	    + "(type) "
	    + "VALUES (?)";

    //==========================================================================
    //	    Location
    //==========================================================================
    private static final String GET_ALL_LOCATIONS
	    = "SELECT * "
	    + "FROM location";

    private static final String GET_LOCATION_BY_ORGANIZATION
	    = "SELECT l.* "
	    + "FROM location l "
	    + "JOIN organization o ON o.locationId = l.locationId "
	    + "WHERE o.organizationId = ?";

    private static final String GET_LOCATION_BY_SIGHTING
	    = "SELECT l.* "
	    + "FROM location l "
	    + "JOIN sighting s ON s.locationId = l.locationId "
	    + "WHERE s.sightingId = ?";

    private static final String GET_LOCATION
	    = "SELECT * "
	    + "FROM location "
	    + "WHERE locationId = ?";

    private static final String DELETE_LOCATION
	    = "DELETE "
	    + "FROM location "
	    + "WHERE locationId = ?";

    private static final String UPDATE_LOCATION
	    = "UPDATE location "
	    + "SET name = ?, description = ?, country = ?, state = ?, "
	    + "city = ?, street = ?, zip = ?, latitude = ?, longitude = ? "
	    + "WHERE locationId = ?";

    private static final String ADD_LOCATION
	    = "INSERT "
	    + "INTO location "
	    + "(name, description, country, state, city, street, zip, latitude, longitude) "
	    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //==========================================================================
    //	    Hero Organization Bridge
    //==========================================================================
    private static final String ADD_BRIDGE_HERO_ORGANIZATION_IDS
	    = "INSERT "
	    + "INTO heroOrganization "
	    + "(heroId, organizationId) "
	    + "VALUES (?, ?)";

    private static final String DELETE_ORGANIZATION_FROM_BRIDGE_HERO_ORGANIZATION
	    = "DELETE "
	    + "FROM heroOrganization "
	    + "WHERE organizationId = ?";

    private static final String DELETE_HERO_FROM_BRIDGE_HERO_ORGANIZATION
	    = "DELETE "
	    + "FROM heroOrganization "
	    + "WHERE heroId = ?";

    //==========================================================================
    //	    Organization
    //==========================================================================
    private static final String GET_ALL_ORGANIZATIONS
	    = "SELECT * "
	    + "FROM organization";

    private static final String GET_ALL_ORGANIZATIONS_BY_LOCATION
	    = "SELECT * "
	    + "FROM organization "
	    + "WHERE locationId = ?";

    private static final String GET_ALL_ORGANIZATIONS_BY_HERO
	    = "SELECT o.* "
	    + "FROM organization o "
	    + "JOIN heroOrganization ho ON ho.organizationId = o.organizationId "
	    + "WHERE ho.heroId = ?";

    private static final String GET_ORGANIZATION
	    = "SELECT * "
	    + "FROM organization "
	    + "WHERE organizationId = ?";

    private static final String DELETE_ORGANIZATION
	    = "DELETE "
	    + "FROM organization "
	    + "WHERE organizationId = ?";

    private static final String UPDATE_ORGANIZATION
	    = "UPDATE organization "
	    + "SET locationId = ?, name = ?, description = ?, phone = ?, email = ? "
	    + "WHERE organizationId = ?";

    private static final String ADD_ORGANIZATION
	    = "INSERT "
	    + "INTO organization "
	    + "(locationId, name, description, phone, email) "
	    + "VALUES (?, ?, ?, ?, ?)";

    //==========================================================================
    //	    Hero Sighting Bridge
    //==========================================================================
    private static final String ADD_BRIDGE_HERO_SIGHTING_IDS
	    = "INSERT "
	    + "INTO heroSighting "
	    + "(heroId, sightingId) "
	    + "VALUES (?, ?)";

    private static final String DELETE_SIGHTING_FROM_BRIDGE_HERO_SIGHTING
	    = "DELETE "
	    + "FROM heroSighting "
	    + "WHERE sightingId = ?";

    private static final String DELETE_HERO_FROM_BRIDGE_HERO_SIGHTING
	    = "DELETE "
	    + "FROM heroSighting "
	    + "WHERE heroId = ?";

    //==========================================================================
    //	    Sighting
    //==========================================================================
    private static final String GET_ALL_SIGHTINGS
	    = "SELECT * "
	    + "FROM sighting";

    private static final String GET_ALL_SIGHTINGS_BY_HERO
	    = "SELECT s.* "
	    + "FROM sighting s "
	    + "JOIN heroSighting hs ON hs.sightingId = s.sightingId "
	    + "WHERE hs.heroId = ?";

    private static final String GET_ALL_SIGHTINGS_BY_LOCATION
	    = "SELECT * "
	    + "FROM sighting "
	    + "WHERE locationId = ?";

    private static final String GET_SIGHTING
	    = "SELECT * "
	    + "FROM sighting "
	    + "WHERE sightingId = ?";

    private static final String DELETE_SIGHTING
	    = "DELETE "
	    + "FROM sighting "
	    + "WHERE sightingId = ?";

    private static final String UPDATE_SIGHTING
	    = "UPDATE sighting "
	    + "SET locationId = ?, date = ?, time = ? "
	    + "WHERE sightingId = ?";

    private static final String ADD_SIGHTING
	    = "INSERT "
	    + "INTO sighting "
	    + "(locationId, date, time) "
	    + "VALUES (?, ?, ?)";

    //==========================================================================
    //	    Hero
    //==========================================================================    
    private void addBridgeHeroSuperpowerIds(Hero hero) {
	int id = hero.getId();
	List<Superpower> superpowers = hero.getSuperpowers();
	for (Superpower s : superpowers) {
	    jdbcTemplate.update(ADD_BRIDGE_HERO_SUPERPOWER_IDS, id, s.getId());
	}
    }

    private void associateHeroWithSuperpower(Hero hero) {
	hero.setSuperpowers(jdbcTemplate.query(GET_ALL_SUPERPOWERS_BY_HERO, new SuperpowerMapper(), hero.getId()));
    }

    private void associateHeroWithSuperpower(List<Hero> heros) {
	for (Hero h : heros) {
	    associateHeroWithSuperpower(h);
	}
    }

    private void addBridgeHeroOrganizationIds(Hero hero) {
	int id = hero.getId();
	List<Organization> organizations = hero.getOrganizations();
	for (Organization o : organizations) {
	    jdbcTemplate.update(ADD_BRIDGE_HERO_ORGANIZATION_IDS, id, o.getId());
	}
    }

    private void associateHeroWithOrganization(Hero hero) {
	hero.setOrganizations(getAllOrganizationsByHeroId(hero.getId()));
    }

    private void associateHeroWithOrganization(List<Hero> heros) {
	for (Hero h : heros) {
	    associateHeroWithOrganization(h);
	}
    }

    private class HeroMapper implements RowMapper<Hero> {

	@Override
	public Hero mapRow(ResultSet rs, int i) throws SQLException {
	    Hero h = new Hero();
	    h.setId(rs.getInt("heroId"));
	    h.setName(rs.getString("name"));
	    h.setDescription(rs.getString("description"));
	    return h;
	}
    }

    @Override
    public List<Hero> getAllHeros() {
	List<Hero> heros = jdbcTemplate.query(GET_ALL_HEROS, new HeroMapper());
	associateHeroWithSuperpower(heros);
	associateHeroWithOrganization(heros);
	return heros;
    }

    @Override
    public List<Hero> getAllHerosBySuperpowerId(int id) {
	List<Hero> heros = jdbcTemplate.query(GET_ALL_HEROS_BY_SUPERPOWER, new HeroMapper(), id);
	associateHeroWithSuperpower(heros);
	associateHeroWithOrganization(heros);
	return heros;
    }

    @Override
    public List<Hero> getAllHerosByOrganizationId(int id) {
	List<Hero> heros = jdbcTemplate.query(GET_ALL_HEROS_BY_ORGANIZATION, new HeroMapper(), id);
	associateHeroWithSuperpower(heros);
	associateHeroWithOrganization(heros);
	return heros;
    }

    @Override
    public List<Hero> getAllHerosBySightingId(int id) {
	List<Hero> heros = jdbcTemplate.query(GET_ALL_HEROS_BY_SIGHTING, new HeroMapper(), id);
	associateHeroWithSuperpower(heros);
	associateHeroWithOrganization(heros);
	return heros;
    }

    @Override
    public Hero getHeroById(int id) {
	try {
	    Hero hero = jdbcTemplate.queryForObject(GET_HERO, new HeroMapper(), id);
	    associateHeroWithSuperpower(hero);
	    associateHeroWithOrganization(hero);
	    return hero;
	}
	catch (DataAccessException ex) {
	    return null;
	}
    }

    @Override
    @Transactional
    public Hero deleteHeroById(int id) {
	Hero h = getHeroById(id);
	jdbcTemplate.update(DELETE_HERO_FROM_BRIDGE_HERO_SUPERPOWER, id);
	jdbcTemplate.update(DELETE_HERO_FROM_BRIDGE_HERO_ORGANIZATION, id);
	jdbcTemplate.update(DELETE_HERO_FROM_BRIDGE_HERO_SIGHTING, id);
	jdbcTemplate.update(DELETE_HERO, id);
	return h;
    }

    @Override
    @Transactional
    public Hero updateHeroById(Hero hero) {
	int id = hero.getId();

	jdbcTemplate.update(UPDATE_HERO,
		hero.getName(),
		hero.getDescription(),
		id);

	jdbcTemplate.update(DELETE_HERO_FROM_BRIDGE_HERO_SUPERPOWER, id);
	addBridgeHeroSuperpowerIds(hero);

	jdbcTemplate.update(DELETE_HERO_FROM_BRIDGE_HERO_ORGANIZATION, id);
	addBridgeHeroOrganizationIds(hero);

	return hero;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
	jdbcTemplate.update(ADD_HERO,
		hero.getName(),
		hero.getDescription());
	setId(hero);

	addBridgeHeroSuperpowerIds(hero);
	addBridgeHeroOrganizationIds(hero);
	return hero;
    }

    //==========================================================================
    //	    Superpower
    //==========================================================================    
    private class SuperpowerMapper implements RowMapper<Superpower> {

	@Override
	public Superpower mapRow(ResultSet rs, int i) throws SQLException {
	    Superpower s = new Superpower();
	    s.setId(rs.getInt("superpowerId"));
	    s.setType(rs.getString("type"));
	    return s;
	}
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
	return jdbcTemplate.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
    }

    @Override
    public List<Superpower> getAllSuperpowersByHeroId(int id) {
	return jdbcTemplate.query(GET_ALL_SUPERPOWERS_BY_HERO, new SuperpowerMapper(), id);
    }

    @Override
    public Superpower getSuperpowerById(int id) {
	try {
	    return jdbcTemplate.queryForObject(GET_SUPERPOWER, new SuperpowerMapper(), id);
	}
	catch (DataAccessException ex) {
	    return null;
	}
    }

    @Override
    @Transactional
    public Superpower deleteSuperpowerById(int id) {
	Superpower s = getSuperpowerById(id);
	jdbcTemplate.update(DELETE_SUPERPOWER_FROM_BRIDGE_HERO_SUPERPOWER, id);
	jdbcTemplate.update(DELETE_SUPERPOWER, id);
	return s;
    }

    @Override
    public Superpower updateSuperpowerById(Superpower superpower) {
	jdbcTemplate.update(UPDATE_SUPERPOWER,
		superpower.getType(),
		superpower.getId());
	return superpower;
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
	jdbcTemplate.update(ADD_SUPERPOWER,
		superpower.getType());
	setId(superpower);
	return superpower;
    }

    //==========================================================================
    //	    Location
    //==========================================================================
    private class LocationMapper implements RowMapper<Location> {

	@Override
	public Location mapRow(ResultSet rs, int i) throws SQLException {
	    Location l = new Location();
	    l.setId(rs.getInt("locationId"));
	    l.setName(rs.getString("name"));
	    l.setDescription(rs.getString("description"));
	    l.setCountry(rs.getString("country"));
	    l.setState(rs.getString("state"));
	    l.setCity(rs.getString("city"));
	    l.setStreet(rs.getString("street"));
	    l.setZip(rs.getString("zip"));
	    l.setLatitude(rs.getBigDecimal("latitude"));
	    l.setLongitude(rs.getBigDecimal("longitude"));
	    return l;
	}
    }

    @Override
    public List<Location> getAllLocations() {
	return jdbcTemplate.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location getLocationById(int id) {
	try {
	    return jdbcTemplate.queryForObject(GET_LOCATION, new LocationMapper(), id);
	}
	catch (DataAccessException ex) {
	    return null;
	}
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Location deleteLocationById(int id) {
	Location l = getLocationById(id);

	List<Organization> organizations = jdbcTemplate.query(GET_ALL_ORGANIZATIONS_BY_LOCATION, new OrganizationMapper(), id);
	List<Sighting> sightings = jdbcTemplate.query(GET_ALL_SIGHTINGS_BY_LOCATION, new SightingMapper(), id);

	for (Organization o : organizations) {
	    jdbcTemplate.update(DELETE_ORGANIZATION_FROM_BRIDGE_HERO_ORGANIZATION);
	    jdbcTemplate.update(DELETE_ORGANIZATION);
	}

	for (Sighting s : sightings) {
	    jdbcTemplate.update(DELETE_SIGHTING_FROM_BRIDGE_HERO_SIGHTING, s.getId());
	    jdbcTemplate.update(DELETE_SIGHTING, s.getId());
	}

	jdbcTemplate.update(DELETE_LOCATION, id);

	return l;
    }

    @Override
    public Location updateLocationById(Location location) {
	jdbcTemplate.update(UPDATE_LOCATION,
		location.getName(),
		location.getDescription(),
		location.getCountry(),
		location.getState(),
		location.getCity(),
		location.getStreet(),
		location.getZip(),
		location.getLatitude(),
		location.getLongitude(),
		location.getId());
	return location;
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
	jdbcTemplate.update(ADD_LOCATION,
		location.getName(),
		location.getDescription(),
		location.getCountry(),
		location.getState(),
		location.getCity(),
		location.getStreet(),
		location.getZip(),
		location.getLatitude(),
		location.getLongitude());
	setId(location);
	return location;
    }

    //==========================================================================
    //	    Organization
    //==========================================================================
    private void addBridgeHeroOrganizationIds(Organization organization) {
	int id = organization.getId();
	List<Hero> heros = jdbcTemplate.query(GET_ALL_HEROS_BY_ORGANIZATION, new HeroMapper(), id);
	for (Hero h : heros) {
	    jdbcTemplate.update(ADD_BRIDGE_HERO_ORGANIZATION_IDS, h.getId(), id);
	}
    }

    public void associateOrganizationWithLocation(Organization organization) {
	try {
	    organization.setLocation(jdbcTemplate.queryForObject(GET_LOCATION_BY_ORGANIZATION, new LocationMapper(), organization.getId()));
	}
	catch (DataAccessException ex) {
	}
    }

    public void associateOrganizationWithLocation(List<Organization> organizations) {
	for (Organization o : organizations) {
	    associateOrganizationWithLocation(o);
	}
    }

    private class OrganizationMapper implements RowMapper<Organization> {

	@Override
	public Organization mapRow(ResultSet rs, int i) throws SQLException {
	    Organization o = new Organization();
	    o.setId(rs.getInt("organizationId"));
	    o.setLocationId(rs.getInt("locationId"));
	    o.setName(rs.getString("name"));
	    o.setDescription(rs.getString("description"));
	    o.setPhone(rs.getString("phone"));
	    o.setEmail(rs.getString("email"));
	    return o;
	}
    }

    @Override
    public List<Organization> getAllOrganizations() {
	List<Organization> organizations = jdbcTemplate.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
	associateOrganizationWithLocation(organizations);
	return organizations;
    }

    @Override
    public List<Organization> getAllOrganizationsByLocationId(int id) {
	List<Organization> organizations = jdbcTemplate.query(GET_ALL_ORGANIZATIONS_BY_LOCATION, new OrganizationMapper(), id);
	associateOrganizationWithLocation(organizations);
	return organizations;
    }

    @Override
    public List<Organization> getAllOrganizationsByHeroId(int id) {
	List<Organization> organizations = jdbcTemplate.query(GET_ALL_ORGANIZATIONS_BY_HERO, new OrganizationMapper(), id);
	associateOrganizationWithLocation(organizations);
	return organizations;
    }

    @Override
    public Organization getOrganizationById(int id) {
	try {
	    Organization organization = jdbcTemplate.queryForObject(GET_ORGANIZATION, new OrganizationMapper(), id);
	    associateOrganizationWithLocation(organization);
	    return organization;
	}
	catch (DataAccessException ex) {
	    return null;
	}
    }

    @Override
    @Transactional
    public Organization deleteOrganizationById(int id) {
	Organization organization = getOrganizationById(id);
	jdbcTemplate.update(DELETE_ORGANIZATION_FROM_BRIDGE_HERO_ORGANIZATION, id);
	jdbcTemplate.update(DELETE_ORGANIZATION, id);
	return organization;
    }

    @Override
    @Transactional
    public Organization updateOrganizationById(Organization organization) {
	jdbcTemplate.update(UPDATE_ORGANIZATION,
		organization.getLocationId(),
		organization.getName(),
		organization.getDescription(),
		organization.getPhone(),
		organization.getEmail(),
		organization.getId());

	jdbcTemplate.update(DELETE_ORGANIZATION_FROM_BRIDGE_HERO_ORGANIZATION, organization.getId());
	addBridgeHeroOrganizationIds(organization);
	return organization;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
	jdbcTemplate.update(ADD_ORGANIZATION,
		organization.getLocationId(),
		organization.getName(),
		organization.getDescription(),
		organization.getPhone(),
		organization.getEmail());
	setId(organization);
	return organization;
    }

    //==========================================================================
    //	    Sighting
    //==========================================================================
    private void addBridgeHeroSightingIds(Sighting sighting) {
	int id = sighting.getId();
	List<Hero> heros = sighting.getHeros();
	for (Hero hero : heros) {
	    try {
		jdbcTemplate.update(ADD_BRIDGE_HERO_SIGHTING_IDS, hero.getId(), id);
	    }
	    catch (DataAccessException ex) {
		System.out.println(ex.getMessage());
	    }
	}
    }

    private void associateSightingWithHero(Sighting sighting) {
	sighting.setHeros(getAllHerosBySightingId(sighting.getId()));
    }

    private void associateSightingWithHero(List<Sighting> sightings) {
	for (Sighting s : sightings) {
	    associateSightingWithHero(s);
	}
    }

    public void associateSightingWithLocation(Sighting sighting) {
	sighting.setLocation(jdbcTemplate.queryForObject(GET_LOCATION_BY_SIGHTING, new LocationMapper(), sighting.getId()));
    }

    public void associateSightingWithLocation(List<Sighting> sightings) {
	for (Sighting s : sightings) {
	    associateSightingWithLocation(s);
	}
    }

    private class SightingMapper implements RowMapper<Sighting> {

	@Override
	public Sighting mapRow(ResultSet rs, int i) throws SQLException {
	    Sighting s = new Sighting();
	    s.setId(rs.getInt("sightingId"));
	    s.setLocationId(rs.getInt("locationId"));
	    s.setDate(rs.getDate("date").toLocalDate());
	    s.setTime(rs.getTime("time").toLocalTime());
	    return s;
	}
    }

    @Override
    public List<Sighting> getAllSightings() {
	List<Sighting> sightings = jdbcTemplate.query(GET_ALL_SIGHTINGS, new SightingMapper());
	associateSightingWithLocation(sightings);
	associateSightingWithHero(sightings);
	return sightings;
    }

    @Override
    public List<Sighting> getAllSightingsByHeroId(int id) {
	List<Sighting> sightings = jdbcTemplate.query(GET_ALL_SIGHTINGS_BY_HERO, new SightingMapper(), id);
	associateSightingWithLocation(sightings);
	associateSightingWithHero(sightings);
	return sightings;
    }

    @Override
    public List<Sighting> getAllSightingsByLocationId(int id) {
	List<Sighting> sightings = jdbcTemplate.query(GET_ALL_SIGHTINGS_BY_LOCATION, new SightingMapper(), id);
	associateSightingWithLocation(sightings);
	associateSightingWithHero(sightings);
	return sightings;
    }

    @Override
    public Sighting getSightingById(int id) {
	try {
	    Sighting sighting = jdbcTemplate.queryForObject(GET_SIGHTING, new SightingMapper(), id);
	    associateSightingWithLocation(sighting);
	    associateSightingWithHero(sighting);
	    return sighting;
	}
	catch (DataAccessException ex) {
	    return null;
	}
    }

    @Override
    @Transactional
    public Sighting deleteSightingById(int id) {
	Sighting sighting = getSightingById(id);
	jdbcTemplate.update(DELETE_SIGHTING_FROM_BRIDGE_HERO_SIGHTING, id);
	jdbcTemplate.update(DELETE_SIGHTING, id);
	return sighting;
    }

    @Override
    @Transactional
    public Sighting updateSightingById(Sighting sighting) {
	jdbcTemplate.update(UPDATE_SIGHTING,
		sighting.getLocationId(),
		java.sql.Date.valueOf(sighting.getDate()),
		java.sql.Time.valueOf(sighting.getTime()),
		sighting.getId());

	jdbcTemplate.update(DELETE_SIGHTING_FROM_BRIDGE_HERO_SIGHTING, sighting.getId());
	addBridgeHeroSightingIds(sighting);

	return sighting;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
	jdbcTemplate.update(ADD_SIGHTING,
		sighting.getLocationId(),
		java.sql.Date.valueOf(sighting.getDate()),
		java.sql.Time.valueOf(sighting.getTime()));
	setId(sighting);

	addBridgeHeroSightingIds(sighting);

	return sighting;
    }
}
