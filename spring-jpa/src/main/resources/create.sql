CREATE DATABASE SpringJpa DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE SpringJpa;

-- Create the STUDIO table
CREATE TABLE Studio (
    StudioId BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    StudioName VARCHAR(100) NOT NULL,
    YearFounded INT,
    StudionHeadQuarters VARCHAR(255) NOT NULL,
    INDEX StudioNames (StudioName)
) ENGINE = InnoDB;

-- Create the ACTOR table
CREATE TABLE Actor (
    ActorId BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ActorName VARCHAR(100) NOT NULL,
    ActorBirthDate DATE,
    ActorNationality VARCHAR(50) NOT NULL,
    INDEX ActorNames (ActorName)
) ENGINE = InnoDB;

-- Create the MOVIE table
CREATE TABLE Movie (
    MovieId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    MovieTitle VARCHAR(255) NOT NULL,
    MovieReleaseDate DATE,
    MovieDuration INT NOT NULL,
    MovieGenre VARCHAR(50) NOT NULL,
    MovieRating DECIMAL(3,1),
    MovieStudioId BIGINT,
    INDEX MovieTitles (MovieTitle) 
) ENGINE = InnoDB;

CREATE TABLE SurrogateKeys (
  TableName VARCHAR(64) NOT NULL PRIMARY KEY,
  KeyValue BIGINT UNSIGNED NOT NULL,
  INDEX SurrogateKeys_Table_Values (TableName, KeyValue)
) ENGINE = InnoDB;
