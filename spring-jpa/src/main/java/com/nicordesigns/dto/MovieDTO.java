package com.nicordesigns.dto;

import com.nicordesigns.enums.Rating;

import java.time.LocalDate;
import java.util.List;

public class MovieDTO {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private Integer duration;
    private String genre;
    private Rating rating;
    private String studioName;
    private List<String> actorNames;

    
    // Default Constructor
    public MovieDTO() {
    }

    // Parameterized Constructor
    public MovieDTO(Long id, String title, LocalDate releaseDate, Integer duration,
                    String genre, Rating rating, String studioName, List<String> actorNames) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.studioName = studioName;
        this.actorNames = actorNames;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", studioName='" + studioName + '\'' +
                ", actorNames=" + actorNames +
                '}';
    }
}
