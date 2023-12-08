-- Create the database
CREATE DATABASE IF NOT EXISTS AP_GROUP_PROJECT;
USE AP_GROUP_PROJECT;

-- Create the citizendatabase table
CREATE TABLE `citizendatabase` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fullname` VARCHAR(45) DEFAULT NULL,
  `username` VARCHAR(45) DEFAULT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fullname_UNIQUE` (`fullname`)
);


-- Create the additionalinfo table with a foreign key constraint
CREATE TABLE `additionalinfo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Fathername` VARCHAR(45) DEFAULT NULL,
  `Mothername` VARCHAR(45) DEFAULT NULL,
  `UserName` VARCHAR(45) DEFAULT NULL,
  `Gender` VARCHAR(45) DEFAULT NULL,
  `Nationality` VARCHAR(45) DEFAULT NULL,
  `CitizenshipId` VARCHAR(45) DEFAULT NULL,
  `Dob` DATE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_id` FOREIGN KEY (`id`) REFERENCES `citizendatabase` (`id`)
);

CREATE TABLE admin(
	id INT NOT NULL AUTO_INCREMENT,
    Adminusername VARCHAR(45) DEFAULT NULL,
    password VARCHAR(45) DEFAULt NULL,
    PRIMARY KEY(id)
    );

