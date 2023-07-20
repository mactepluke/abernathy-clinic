CREATE DATABASE IF NOT EXISTS mediscreen_db;

USE mediscreen_db;

CREATE TABLE IF NOT EXISTS patient
(
    patientId       INT AUTO_INCREMENT,
    family          VARCHAR(50) NOT NULL,
    given           VARCHAR(50) NOT NULL,
    dob             DATE NOT NULL,
    sex             BOOLEAN NOT NULL,
    address         VARCHAR(250),
    phone           VARCHAR(20),

    PRIMARY KEY (patientId)
);
