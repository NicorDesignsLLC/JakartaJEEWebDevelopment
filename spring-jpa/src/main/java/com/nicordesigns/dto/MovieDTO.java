package com.nicordesigns.dto;

import java.util.Date;
import java.util.List;

public class MovieDTO {
    private Long movieId;
    private String movieTitle;
    private Date movieReleaseDate;
    private int movieDuration;
    private String movieGenre;
    private String movieRating;
    private String studioName;
    private List<String> actorNames;

    // Default Constructor
    public MovieDTO() {
    }

    // Parameterized Constructor
    public MovieDTO(Long movieId, String movieTitle, Date movieReleaseDate, int movieDuration, 
                    String movieGenre, String movieRating, String studioName, List<String> actorNames) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieDuration = movieDuration;
        this.movieGenre = movieGenre;
        this.movieRating = movieRating;
        this.studioName = studioName;
        this.actorNames = actorNames;
    }

    // Getters and Setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public int getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(int movieDuration) {
        this.movieDuration = movieDuration;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    public void setActorNames(List<String> actorNames) {
        this.actorNames = actorNames;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieReleaseDate=" + movieReleaseDate +
                ", movieDuration=" + movieDuration +
                ", movieGenre='" + movieGenre + '\'' +
                ", movieRating='" + movieRating + '\'' +
                ", studioName='" + studioName + '\'' +
                ", actorNames=" + actorNames +
                '}';
    }
}
