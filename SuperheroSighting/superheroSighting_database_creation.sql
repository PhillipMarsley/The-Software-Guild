DROP DATABASE IF EXISTS superheroSighting;

CREATE DATABASE IF NOT EXISTS superHeroSighting;

USE superHeroSighting;

CREATE TABLE hero (
	heroId INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50), 
    description VARCHAR(500) NOT NULL
);

CREATE TABLE superpower (
	superpowerId INT PRIMARY KEY AUTO_INCREMENT,
    `type` VARCHAR(50) NOT NULL
);

CREATE TABLE heroSuperpower (
	heroId INT,
    superpowerId INT,
    PRIMARY KEY (heroId, superpowerId),
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (superpowerId) REFERENCES superpower(superpowerId)
);

CREATE TABLE location (
	locationId INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    country VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    latitude DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);

CREATE TABLE organization (
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
    locationId INT,
    `name` VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    phone VARCHAR(50),
    email VARCHAR(100),
    FOREIGN KEY(locationId) REFERENCES location(locationId)
);

CREATE TABLE heroOrganization (
    heroId INT,
	organizationId INT,
    PRIMARY KEY(heroId, organizationId),
    FOREIGN KEY(heroId) REFERENCES hero(heroId),
    FOREIGN KEY(organizationId) REFERENCES organization(organizationId)
);

CREATE TABLE sighting (
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    locationId INT,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    FOREIGN KEY(locationId) REFERENCES location(locationId)
);

CREATE TABLE heroSighting (
	heroId INT,
    sightingId INT,
    PRIMARY KEY(heroId, sightingId),
    FOREIGN KEY(heroId) REFERENCES hero(heroId),
    FOREIGN KEY(sightingId) REFERENCES sighting(sightingId)
);