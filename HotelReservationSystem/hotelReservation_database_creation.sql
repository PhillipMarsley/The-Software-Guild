DROP DATABASE IF EXISTS hotelReservation;

CREATE DATABASE hotelReservation;

USE hotelReservation;

CREATE TABLE hotel (
	hotelID INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
    country VARCHAR(45) NOT NULL,
    state VARCHAR(45) NOT NULL,
    city VARCHAR(45) NOT NULL,
    zip INT NOT NULL,
    address VARCHAR(45) NOT NULL,
	PRIMARY KEY (hotelID)
);

CREATE TABLE roomType (
	roomTypeID INT NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(45),
    PRIMARY KEY (roomTypeID)
);

CREATE TABLE roomTypePrice (
	roomTypeID INT NOT NULL,
	`date` DATE NOT NULL,
    price DECIMAL NOT NULL,
    PRIMARY KEY (roomID, `date`),
    FOREIGN KEY (roomTypeID) REFERENCES roomType(roomTypeID)
);

CREATE TABLE room (
	roomID INT NOT NULL AUTO_INCREMENT,
    hotelID INT NOT NULL,
    roomTypeID INT NOT NULL,
    `number` INT NOT NULL,
    floor INT NOT NULL,
    occupacity INT NOT NULL,
    PRIMARY KEY (roomID),
    FOREIGN KEY (hotelID) REFERENCES hotel(hotelID),
    FOREIGN KEY (roomTypeID) REFERENCES roomType(roomTypeID)
);

CREATE TABLE amenity (
	amenityID INT NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(45),
    PRIMARY KEY (amenityID)
);

CREATE TABLE roomAmenties (
	roomID INT NOT NULL,
    amenityID INT NOT NULL,
	PRIMARY KEY (roomID, amenityID),
	FOREIGN KEY (roomID) REFERENCES room(roomID),
    FOREIGN KEY (amenityID) REFERENCES amenity(amenityID)
);

CREATE TABLE bill (
	billID INT NOT NULL AUTO_INCREMENT,
	preTotal DECIMAL(20,2) NOT NULL,
    taxTotal DECIMAL(20,2) NOT NULL,
    postTotal DECIMAL(20,2) NOT NULL,
    PRIMARY KEY (billID)
);

CREATE TABLE billDetails (
	billDetailsID INT NOT NULL AUTO_INCREMENT,
	billID INT NOT NULL,
	itemDescription VARCHAR(45) NOT NULL,
    charge DECIMAL(20,2) NOT NULL,
    priceWaved BOOLEAN,
    PRIMARY KEY (billDetailsID),
    FOREIGN KEY (billID) REFERENCES bill(billID)
);

CREATE TABLE customer (
	customerID INT NOT NULL AUTO_INCREMENT,
    billID INT NOT NULL,
    firstName VARCHAR(45) NOT NULL,
    lastName VARCHAR(45) NOT NULL,
    phone VARCHAR(45) NOT NULL,
    email VARCHAR(45),
	PRIMARY KEY (customerID)
);

CREATE TABLE reservation (
	reservationID INT NOT NULL AUTO_INCREMENT,
    customerID INT NOT NULL,
	billID INT NOT NULL,
    dateStart DATE NOT NULL,
    dateEnd DATE NOT NULL,
    wasCancled BOOLEAN,
    PRIMARY KEY (reservationID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID),
    FOREIGN KEY (billID) REFERENCES bill(billID)
);

CREATE TABLE reservationRoom (
	reservationID INT NOT NULL,
    roomID INT NOT NULL,
    PRIMARY KEY (reservationID, roomID),
	FOREIGN KEY (reservationID) REFERENCES reservation(reservationID),
    FOREIGN KEY (roomID) REFERENCES room(roomID)
);

CREATE TABLE promotionCode (
	promotionCodeID INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(45) NOT NULL,
    percentAmount DECIMAL,
    dollarAmount DECIMAL,
    PRIMARY KEY (promotionCodeID)
);

CREATE TABLE promotionCodeReservation (
	promotionCodeID INT NOT NULL,
    reservationID INT NOT NULL,
    PRIMARY KEY (promotionCodeID, reservationID),
	FOREIGN KEY (promotionCodeID) REFERENCES promotionCode(promotionCodeID),
    FOREIGN KEY (reservationID) REFERENCES reservation(reservationID)
);

CREATE TABLE guest (
	guestID INT NOT NULL AUTO_INCREMENT,
    reservationID INT NOT NULL,
    firstName VARCHAR(45) NOT NULL,
    lastName VARCHAR(45) NOT NULL,
    age TINYINT NOT NULL,
    PRIMARY KEY (guestID),
    FOREIGN KEY (reservationID) REFERENCES reservation(reservationID)
);

CREATE TABLE addon (
	addonID INT NOT NULL AUTO_INCREMENT,
    hotelID INT NOT NULL,
    `type` VARCHAR(45) NOT NULL,
    PRIMARY KEY (addonID),
    FOREIGN KEY (hotelID) REFERENCES hotel(hotelID)
);

CREATE TABLE guestAddon (
	guestID INT NOT NULL,
    addonID INT NOT NULL,
    PRIMARY KEY (guestID, addonID),
	FOREIGN KEY (guestID) REFERENCES guest(guestID),
    FOREIGN KEY (addonID) REFERENCES addon(addonID)
);

CREATE TABLE addonPrice (
	addonPriceID INT NOT NULL AUTO_INCREMENT,
	addonID INT NOT NULL,
	`date` DATE NOT NULL,
    price DECIMAL(20,2) NOT NULL,
    PRIMARY KEY (addonPriceID),
    FOREIGN KEY (addonID) REFERENCES addon(addonID)
);