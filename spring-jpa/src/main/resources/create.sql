-- Drop and recreate the database
DROP DATABASE IF EXISTS SpringJpa;
CREATE DATABASE SpringJpa DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

-- Use the new database
USE SpringJpa;

-- Create the Studio table
DROP TABLE IF EXISTS Studio;
CREATE TABLE Studio (
    StudioId BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    StudioName VARCHAR(100) NOT NULL,
    YearFounded YEAR,  -- Uses the YEAR data type (valid range: 1901-2155)
    StudioHeadQuarters VARCHAR(255) NOT NULL,
    INDEX StudioNames (StudioName)
) ENGINE = InnoDB;

-- Create the Actor table
DROP TABLE IF EXISTS Actor;
CREATE TABLE Actor (
    ActorId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ActorName VARCHAR(100) NOT NULL,
    ActorBirthDate DATE,
    ActorNationality VARCHAR(50) NOT NULL,
    INDEX ActorNames (ActorName)
) ENGINE = InnoDB;

-- Create the Movie table
DROP TABLE IF EXISTS Movie;
CREATE TABLE Movie (
    MovieId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    MovieTitle VARCHAR(255) NOT NULL,
    MovieReleaseDate DATE,
    MovieDuration INT NOT NULL,
    MovieGenre VARCHAR(50) NOT NULL,
    MovieRating DECIMAL(4,1),
    MovieStudioId BIGINT UNSIGNED,
    INDEX MovieTitles (MovieTitle),
    CONSTRAINT FK_Movie_Studio FOREIGN KEY (MovieStudioId) REFERENCES Studio(StudioId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = InnoDB;

DROP TABLE IF EXISTS Movie_Actor;
CREATE TABLE Movie_Actor (
    MovieId BIGINT UNSIGNED NOT NULL,
    ActorId BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (MovieId, ActorId),
    CONSTRAINT FK_Movie FOREIGN KEY (MovieId) REFERENCES Movie(MovieId) ON DELETE CASCADE,
    CONSTRAINT FK_Actor FOREIGN KEY (ActorId) REFERENCES Actor(ActorId) ON DELETE CASCADE
) ENGINE = InnoDB;

-- Optional: Query the tables to verify
SELECT * FROM Studio;
SELECT * FROM Actor;
SELECT * FROM Movie;

SHOW CREATE TABLE Studio;

