DROP DATABASE IF EXISTS	superhumanSightingsDB;
CREATE DATABASE superhumanSightingsDB;

USE superhumanSightingsDB;

CREATE TABLE location(
	id INT PRIMARY KEY AUTO_INCREMENT,
	address VARCHAR(200) NOT NULL,
    name VARCHAR(100),
    description VARCHAR(200),
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
    
);




CREATE TABLE organization(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL, 
    description VARCHAR(200) ,
	address VARCHAR(200) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email	VARCHAR(50) NOT NULL
    
);

CREATE TABLE superpower(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE superhuman(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE sighting(
	superhuman_id INT NOT NULL,
    location_id INT NOT NULL,
    date_time DATETIME NOT NULL,
    FOREIGN KEY (superhuman_id) REFERENCES superhuman(id),
    FOREIGN KEY (location_id) REFERENCES location(id),
    PRIMARY KEY(superhuman_id, location_id, date_time)
);

CREATE TABLE member(
	superhuman_id INT NOT NULL,
    organization_id INT NOT NULL,
    FOREIGN KEY (superhuman_id) REFERENCES superhuman(id),
    FOREIGN KEY (organization_id) REFERENCES organization(id),
    PRIMARY KEY (superhuman_id, organization_id)
);

CREATE TABLE superhuman_superpower(
	superpower_id INT NOT NULL,
    superhuman_id INT NOT NULL,
    FOREIGN KEY (superpower_id) REFERENCES superpower(id),
    FOREIGN KEY (superhuman_id) REFERENCES superhuman(id),
    PRIMARY KEY (superhuman_id, superpower_id)
    
);



