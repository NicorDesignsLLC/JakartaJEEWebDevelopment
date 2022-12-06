### Creating the CharityDB and seeding  it MariaDB in Eclipse

### We create a new Eclipse-Maven sub-module that we will use to store all our SQL Scripts - No Archetype

jee8mariadbsql

In this module we create a subdirectory to stor all our SQL Scripts

src/main/resources/sqlscripts

We the use the Eclipse Data Source Explorer to run these scripts to create and populate our Charity Database


### Using the Eclipse DB Console

### 1. Recreate application user and Charity DB

#### Create 1-CREATE-CHARITY-DB.SQL Script

            DROP DATABASE IF EXISTS charityDB;
            CREATE DATABASE charityDB;
            SHOW DATABASES;

#### Create 1-A-CREATE-APP-USER.SQL Script

            DROP USER springjdbcapp@localhost;
            CREATE USER springjdbcapp@localhost IDENTIFIED BY 'password1';
            SELECT user FROM mysql.user;
            GRANT ALL PRIVILEGES ON charitydb.* TO springjdbcapp@localhost;
            FLUSH PRIVILEGES;
            SHOW GRANTS FOR springjdbcapp@localhost;

### 2. Create Charity Table

#### Create 1-B-CREATE-CHARITY-TABLE.SQL Script

            ALTER TABLE charitydb.CHARITY DROP INDEX IF EXISTS `PK_CHARITY`;

            DELETE FROM charitydb.CHARITY;
            
            DROP TABLE IF EXISTS charitydb.CHARITY;
            
            CREATE TABLE charitydb.CHARITY
            (
            `CHARITY_ID`               MEDIUMINT    NOT NULL AUTO_INCREMENT,
            `CHARITY_TAX_ID`           VARCHAR(250) NOT NULL UNIQUE , -- https://tax.co.za/what-is-my-tax-number/
            `CHARITY_NAME`             VARCHAR(250),
            `CHARITY_MISSION`          VARCHAR(250),
            `CHARITY_WEB_ADDRESS`      VARCHAR(250),          -- Need a separate ADDRESS Table
            `CHARITY_FACEBOOK_ADDRESS` VARCHAR(250),          -- Need a separate ADDRESS Table
            `CHARITY_TWITTER_ADDRESS`  VARCHAR(250),          -- Need a separate ADDRESS Table
            PRIMARY KEY (`CHARITY_ID`)
            ) ENGINE = InnoDB;
            
            CREATE UNIQUE INDEX `PK_CHARITY` ON charitydb.CHARITY (`CHARITY_ID` ASC);

### 3. Create Charity Category Table and Relationship Tables

#### Create 2-CREATE-CATEGOTY-TABLE.SQL Script

        ALTER TABLE charitydb.`CATEGORY` DROP INDEX IF EXISTS `IDX_CATEGORY`;

        ALTER TABLE CATEGORY DROP FOREIGN KEY IF EXISTS `FK_CHARITY_CATEGORY_ID`;
        
        DROP TABLE IF EXISTS charitydb.`CATEGORY`;
        
        CREATE TABLE charitydb.`CATEGORY`
        (
        `CATEGORY_ID`   MEDIUMINT NOT NULL AUTO_INCREMENT,
        `CATEGORY_NAME` VARCHAR(50),
        CONSTRAINT `CATEGORY_PK` PRIMARY KEY (`CATEGORY_ID`)
        ) ENGINE = InnoDB;

        CREATE UNIQUE INDEX `IDX_CATEGORY` ON charitydb.`CATEGORY` (`CATEGORY_ID` ASC);
        
        ALTER TABLE `CATEGORY`
        ADD CONSTRAINT `FK_CHARITY_CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`)
        REFERENCES `charity_category` (`CATEGORY_ID`) #
        ON DELETE RESTRICT
        ON UPDATE CASCADE;

#### CREATE 2-A-CHARITY-CATEGORY-RELATIONSHIP.SQL Script

        ALTER TABLE CHARITY_CATEGORY DROP FOREIGN KEY IF EXISTS `FK_CATEGORY_ID`;

        ALTER TABLE CHARITY_CATEGORY DROP FOREIGN KEY IF EXISTS `FK_CHARITY_ID`;
        
        DROP TABLE IF EXISTS charitydb.`CHARITY_CATEGORY`;
        
        CREATE TABLE charitydb.`CHARITY_CATEGORY`
        (
        `CHARITY_ID`  MEDIUMINT NOT NULL,
        `CATEGORY_ID` MEDIUMINT NOT NULL
        ) ENGINE = InnoDB;
        
        ALTER TABLE `CHARITY_CATEGORY`
        ADD CONSTRAINT `FK_CHARITY_ID`
        FOREIGN KEY (CHARITY_ID) REFERENCES charity(CHARITY_ID)
        ON DELETE RESTRICT
        ON UPDATE CASCADE;
        
        ALTER TABLE `CHARITY_CATEGORY`
        ADD CONSTRAINT `FK_CATEGORY_ID`
        FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(CATEGORY_ID)
        ON DELETE RESTRICT
        ON UPDATE CASCADE;

### Populate Categories with scripts

#### 2-A-B-INSERT-CATEGORY-ROWS.SQL Script

        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('ANIMALS');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('ARTS,CULTURE,HUMANITIES');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('COMMUNITY DEVELOPMENT');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('EDUCATION');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('ENVIRONMENT');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('HEALTH');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('HUMAN SERVICES');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('HUMAN AND CIVIL RIGHTS');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('RELIGION');
        INSERT INTO CATEGORY (CATEGORY_NAME) VALUES ('RESEARCH AND PUBLIC POLICY');

### 4. Create Charity Program Table and Relationship Tables

#### CREATE 3-CREATE-PROGRAM.SQL Script

        ALTER TABLE charitydb.`PROGRAM` DROP INDEX IF EXISTS `IDX_PROGRAM`;

        ALTER TABLE PROGRAM DROP FOREIGN KEY IF EXISTS `FK_CHARITY_PROGRAM_ID`;
        
        DROP TABLE IF EXISTS charitydb.`PROGRAM`;
        
        CREATE TABLE charitydb.`PROGRAM`
        (
        `PROGRAM_ID`   MEDIUMINT NOT NULL AUTO_INCREMENT,
        `PROGRAM_DESCRIPTION` VARCHAR(250),
        CONSTRAINT `PROGRAM_PK` PRIMARY KEY (`PROGRAM_ID`)
        ) ENGINE = InnoDB;;
        
        CREATE UNIQUE INDEX `IDX_PROGRAM` ON charitydb.`PROGRAM` (`PROGRAM_ID` ASC);
        
        ALTER TABLE `PROGRAM`
        ADD CONSTRAINT `FK_CHARITY_PROGRAM_ID` FOREIGN KEY (`PROGRAM_ID`)
        REFERENCES `charity_program` (`PROGRAM_ID`) #
        ON DELETE RESTRICT
        ON UPDATE CASCADE;

### Populate PROGRAM table with scripts

#### CREATE 3-A-INSERT-PROGRAMS.SQL Script

        INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES ('Residential facility');
        INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES ('Social Work offices for psycho-social services');
        INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES ('Protective workshops');
        INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES ('Home based care');
        INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES ('Pre school');
        INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES ('Orphanage');

#### CREATE 3-B-CREATE-CHARITY-PROGRAMS.SQL Script

        ALTER TABLE CHARITY_PROGRAM DROP FOREIGN KEY IF EXISTS `FK_CHARITY_PROGRAM_ID`;
        ALTER TABLE CHARITY_PROGRAM DROP FOREIGN KEY IF EXISTS `FK_PROGRAM_ID`;
        
        DROP TABLE IF EXISTS charitydb.`CHARITY_PROGRAM`;
        
        CREATE TABLE charitydb.`CHARITY_PROGRAM`
        (
        `CHARITY_ID`  MEDIUMINT NOT NULL,
        `PROGRAM_ID` MEDIUMINT NOT NULL
        );
        
        ALTER TABLE `CHARITY_PROGRAM`
        ADD CONSTRAINT `FK_CHARITY_PROGRAM_ID`
        FOREIGN KEY (CHARITY_ID) REFERENCES charity(CHARITY_ID)
        ON DELETE RESTRICT
        ON UPDATE CASCADE;
        
        ALTER TABLE `CHARITY_PROGRAM`
        ADD CONSTRAINT `FK_PROGRAM_ID`
        FOREIGN KEY (PROGRAM_ID) REFERENCES program(PROGRAM_ID)
        ON DELETE RESTRICT
        ON UPDATE CASCADE;

