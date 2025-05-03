DROP DATABASE IF EXISTS charityDB;
CREATE DATABASE charityDB;
SHOW DATABASES;

-- 👤 Drop and recreate the jee8webapp user
DROP USER IF EXISTS 'jee8webapp'@'localhost';
CREATE USER 'jee8webapp'@'localhost' IDENTIFIED BY 'your_secure_password_here';
SELECT user FROM mysql.user;

-- 🔑 Grant access to charityDB for the user
GRANT ALL PRIVILEGES ON charityDB.* TO 'jee8webapp'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'jee8webapp'@'localhost';

SELECT user, host FROM mysql.user;


-- 📁 Drop and create the USER_ADMIN table
DROP TABLE IF EXISTS charitydb.USER_ADMIN;

CREATE TABLE charitydb.USER_ADMIN (
  USER_ID    MEDIUMINT    NOT NULL AUTO_INCREMENT,
  USERNAME   VARCHAR(50)  NOT NULL UNIQUE,
  PASSWORD   VARCHAR(100) NOT NULL,
  PRIMARY KEY (USER_ID)
) ENGINE = InnoDB;

-- 🔢 Seed user data
INSERT INTO charitydb.USER_ADMIN (USERNAME, PASSWORD) VALUES
  ('Nicolaas', 'Black'),
  ('Danette', 'White'),
  ('Tom', 'Green');

-- 📜 Grant privileges to the jee8webapp user for the new table
GRANT ALL PRIVILEGES ON charitydb.USER_ADMIN TO 'jee8webapp'@'localhost';
FLUSH PRIVILEGES;

-- Verify the data
SELECT * FROM charitydb.USER_ADMIN;



-- 📄 Drop and create the CHARITY table
DROP TABLE IF EXISTS charitydb.CHARITY;

CREATE TABLE charitydb.CHARITY (
  CHARITY_ID               MEDIUMINT    NOT NULL AUTO_INCREMENT,
  CHARITY_TAX_ID           VARCHAR(100) NOT NULL UNIQUE,
  CHARITY_NAME             VARCHAR(250),
  CHARITY_MISSION          VARCHAR(250),
  CHARITY_WEB_ADDRESS      VARCHAR(250),
  CHARITY_FACEBOOK_ADDRESS VARCHAR(250),
  CHARITY_TWITTER_ADDRESS  VARCHAR(250),
  PRIMARY KEY (CHARITY_ID)
) ENGINE = InnoDB;

-- 📁 Drop and create the CATEGORY table
DROP TABLE IF EXISTS charitydb.CATEGORY;

CREATE TABLE charitydb.CATEGORY (
  CATEGORY_ID   MEDIUMINT NOT NULL AUTO_INCREMENT,
  CATEGORY_NAME VARCHAR(50),
  PRIMARY KEY (CATEGORY_ID)
) ENGINE = InnoDB;

-- 🔢 Seed standard non-profit categories
INSERT INTO charitydb.CATEGORY (CATEGORY_NAME) VALUES 
  ('ANIMALS'),
  ('ARTS,CULTURE,HUMANITIES'),
  ('COMMUNITY DEVELOPMENT'),
  ('EDUCATION'),
  ('ENVIRONMENT'),
  ('HEALTH'),
  ('HUMAN SERVICES'),
  ('HUMAN AND CIVIL RIGHTS'),
  ('RELIGION'),
  ('RESEARCH AND PUBLIC POLICY');

-- 🔗 Many-to-many join table between CHARITY and CATEGORY
DROP TABLE IF EXISTS charitydb.CHARITY_CATEGORY;

CREATE TABLE charitydb.CHARITY_CATEGORY (
  CHARITY_ID  MEDIUMINT NOT NULL,
  CATEGORY_ID MEDIUMINT NOT NULL,
  PRIMARY KEY (CHARITY_ID, CATEGORY_ID),
  FOREIGN KEY (CHARITY_ID) REFERENCES charitydb.CHARITY(CHARITY_ID)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (CATEGORY_ID) REFERENCES charitydb.CATEGORY(CATEGORY_ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 📁 Drop and create the PROGRAM table
DROP TABLE IF EXISTS charitydb.PROGRAM;

CREATE TABLE charitydb.PROGRAM (
  PROGRAM_ID          MEDIUMINT NOT NULL AUTO_INCREMENT,
  PROGRAM_DESCRIPTION VARCHAR(250),
  PRIMARY KEY (PROGRAM_ID)
) ENGINE = InnoDB;

-- 🔢 Seed some program types
INSERT INTO charitydb.PROGRAM (PROGRAM_DESCRIPTION) VALUES 
  ('Residential facility'),
  ('Social Work offices for psycho-social services'),
  ('Protective workshops'),
  ('Home based care'),
  ('Pre school'),
  ('Orphanage');

-- 🔗 Many-to-many join table between CHARITY and PROGRAM
DROP TABLE IF EXISTS charitydb.CHARITY_PROGRAM;

CREATE TABLE charitydb.CHARITY_PROGRAM (
  CHARITY_ID  MEDIUMINT NOT NULL,
  PROGRAM_ID  MEDIUMINT NOT NULL,
  PRIMARY KEY (CHARITY_ID, PROGRAM_ID),
  FOREIGN KEY (CHARITY_ID) REFERENCES charitydb.CHARITY(CHARITY_ID)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (PROGRAM_ID) REFERENCES charitydb.PROGRAM(PROGRAM_ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
);


SELECT * FROM charitydb.PROGRAM;

SELECT * FROM charitydb.CATEGORY;

SELECT * FROM charitydb.CHARITY;

SELECT * FROM charitydb.CHARITY_PROGRAM;

SELECT * FROM charitydb.CHARITY_CATEGORY;


-- 📌 Add CHARITIES
INSERT INTO charitydb.CHARITY 
  (CHARITY_TAX_ID, CHARITY_NAME, CHARITY_MISSION, CHARITY_WEB_ADDRESS, CHARITY_FACEBOOK_ADDRESS, CHARITY_TWITTER_ADDRESS)
VALUES
  ('TAX123456', 'Bright Futures Foundation', 'Empowering youth through education and preschool programs.', 'https://brightfutures.org', 'https://facebook.com/brightfutures', '@brightfutures'),
  ('TAX654321', 'Green Paws Alliance', 'Protecting animals and promoting sustainable environments.', 'https://greenpaws.org', 'https://facebook.com/greenpaws', '@greenpaws');

-- Accociate Charities with Categories
 
  
-- Find CATEGORY_IDs first
-- For example:
-- SELECT CATEGORY_ID, CATEGORY_NAME FROM charitydb.CATEGORY;

-- 📌 Link Bright Futures Foundation to EDUCATION and HUMAN SERVICES
INSERT INTO charitydb.CHARITY_CATEGORY (CHARITY_ID, CATEGORY_ID) VALUES
  (1, 4),  -- EDUCATION
  (1, 7);  -- HUMAN SERVICES

-- 📌 Link Green Paws Alliance to ANIMALS and ENVIRONMENT
INSERT INTO charitydb.CHARITY_CATEGORY (CHARITY_ID, CATEGORY_ID) VALUES
  (2, 1),  -- ANIMALS
  (2, 5);  -- ENVIRONMENT
  
-- Accociate Charities with Programs
  
  -- Find PROGRAM_IDs first
-- For example:
-- SELECT PROGRAM_ID, PROGRAM_DESCRIPTION FROM charitydb.PROGRAM;

-- 📌 Link Bright Futures Foundation to Pre School and Social Work
INSERT INTO charitydb.CHARITY_PROGRAM (CHARITY_ID, PROGRAM_ID) VALUES
  (1, 2),  -- Social Work offices for psycho-social services
  (1, 5);  -- Pre school

-- 📌 Link Green Paws Alliance to Protective Workshops and Home based care
INSERT INTO charitydb.CHARITY_PROGRAM (CHARITY_ID, PROGRAM_ID) VALUES
  (2, 3),  -- Protective workshops
  (2, 4);  -- Home based care

  
INSERT INTO charitydb.CHARITY 
  (CHARITY_TAX_ID, CHARITY_NAME, CHARITY_MISSION, CHARITY_WEB_ADDRESS, CHARITY_FACEBOOK_ADDRESS, CHARITY_TWITTER_ADDRESS)
VALUES
  ('TAX777777', 'Hope Haven Orphanage', 'Christian-based care and shelter for orphaned and vulnerable children.', 'https://hopehaven.org', 'https://facebook.com/hopehaven', '@hopehaven');

INSERT INTO charitydb.CHARITY_CATEGORY (CHARITY_ID, CATEGORY_ID) VALUES
  (3, 9),  -- RELIGION
  (3, 7),  -- HUMAN SERVICES
  (3, 4);  -- EDUCATION

  
INSERT INTO charitydb.CHARITY_PROGRAM (CHARITY_ID, PROGRAM_ID) VALUES
  (3, 6),  -- Orphanage
  (3, 1),  -- Residential facility
  (3, 5);  -- Pre school

  
  
  -- Categories per charity
SELECT c.CHARITY_NAME, cat.CATEGORY_NAME
FROM charitydb.CHARITY c
JOIN charitydb.CHARITY_CATEGORY cc ON c.CHARITY_ID = cc.CHARITY_ID
JOIN charitydb.CATEGORY cat ON cc.CATEGORY_ID = cat.CATEGORY_ID;

-- Programs per charity
SELECT c.CHARITY_NAME, p.PROGRAM_DESCRIPTION
FROM charitydb.CHARITY c
JOIN charitydb.CHARITY_PROGRAM cp ON c.CHARITY_ID = cp.CHARITY_ID
JOIN charitydb.PROGRAM p ON cp.PROGRAM_ID = p.PROGRAM_ID;


-- Ensure the database is selected
USE charitydb;

-- 📁 Drop and create the REGISTRATION table (unchanged from previous)
DROP TABLE IF EXISTS charitydb.REGISTRATION;

CREATE TABLE charitydb.REGISTRATION (
    REGISTRATION_ID BIGINT NOT NULL AUTO_INCREMENT,
    USER_NAME VARCHAR(50) NOT NULL,
    SUBJECT VARCHAR(255) NOT NULL,
    BODY VARCHAR(255) NOT NULL,
    CREATED_DATE DATETIME NOT NULL,
    PRIMARY KEY (REGISTRATION_ID)
) ENGINE = InnoDB;

-- 📁 Drop and create the FILE_ATTACHMENT table
DROP TABLE IF EXISTS charitydb.FILE_ATTACHMENT;

CREATE TABLE charitydb.FILE_ATTACHMENT (
    ATTACHMENT_ID BIGINT NOT NULL AUTO_INCREMENT,
    REGISTRATION_ID BIGINT NOT NULL,
    ATTACHMENT_NAME VARCHAR(255) NOT NULL,
    MIME_CONTENT_TYPE VARCHAR(100) NOT NULL,
    CONTENTS LONGBLOB NOT NULL,
    PRIMARY KEY (ATTACHMENT_ID),
    FOREIGN KEY (REGISTRATION_ID) REFERENCES charitydb.REGISTRATION(REGISTRATION_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

-- 📜 Grant privileges to the jee8webapp user
GRANT ALL PRIVILEGES ON charitydb.REGISTRATION TO 'jee8webapp'@'localhost';
GRANT ALL PRIVILEGES ON charitydb.FILE_ATTACHMENT TO 'jee8webapp'@'localhost';
FLUSH PRIVILEGES;

-- 🔢 Seed sample registration data
INSERT INTO charitydb.REGISTRATION (USER_NAME, SUBJECT, BODY, CREATED_DATE) VALUES
    ('Nicolaas', 'Event Registration', 'Registered for Bright Futures Foundation event', '2025-04-28 10:00:00'),
    ('Danette', 'Volunteer Application', 'Volunteer registration for Green Paws Alliance', '2025-04-28 12:00:00'),
    ('Tom', 'Sponsor Registration', 'Sponsor registration for Hope Haven Orphanage', '2025-04-28 14:00:00');

-- 🔢 Seed sample file attachment data (use UNHEX for BLOB content in MariaDB)
INSERT INTO charitydb.FILE_ATTACHMENT (REGISTRATION_ID, ATTACHMENT_NAME, MIME_CONTENT_TYPE, CONTENTS) VALUES
    (1, 'event_details.pdf', 'application/pdf', UNHEX('255044462D312E340A25E2E3CFD30A')), -- Sample PDF header
    (1, 'photo.jpg', 'image/jpeg', UNHEX('FFD8FFE000104A464946')), -- Sample JPEG header
    (2, 'volunteer_form.doc', 'application/msword', UNHEX('D0CF11E0A1B11AE1')), -- Sample DOC header
    (3, 'sponsor_agreement.pdf', 'application/pdf', UNHEX('255044462D312E340A25E2E3CFD30A')); -- Sample PDF header
    
    
-- Verify the data
SELECT REGISTRATION_ID, USER_NAME, SUBJECT, BODY, CREATED_DATE FROM charitydb.REGISTRATION;
SELECT ATTACHMENT_ID, REGISTRATION_ID, ATTACHMENT_NAME, MIME_CONTENT_TYPE, LENGTH(CONTENTS) AS CONTENT_LENGTH FROM charitydb.FILE_ATTACHMENT;  
  
  
  
  
  
