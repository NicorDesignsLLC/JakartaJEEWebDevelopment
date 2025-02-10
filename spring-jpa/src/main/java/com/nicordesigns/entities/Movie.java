package com.nicordesigns.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nicordesigns.enums.Rating;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovieId", nullable = false, unique = true)
    private Long id;

    @Column(name = "MovieTitle", nullable = false, length = 255)
    private String title;

    @Column(name = "MovieReleaseDate")
    private LocalDate releaseDate;

    @Column(name = "MovieDuration", nullable = false)
    private Integer duration;

    @Column(name = "MovieGenre", nullable = false, length = 50)
    private String genre;

    @Enumerated(EnumType.STRING)  //Store Enum as String in DB
    @Column(name = "MovieRating", length = 10)  // Adjust DB column for text storage
    private Rating rating;

    @ManyToOne
    @JoinColumn(name = "MovieStudioId", nullable = false)
    @JsonBackReference
    private Studio studio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Movie_Actor",
        joinColumns = @JoinColumn(name = "MovieId"),
        inverseJoinColumns = @JoinColumn(name = "ActorId")
    )
    private List<Actor> actors = new ArrayList<>();

    // Default Constructor
    public Movie() {}

    // Updated Constructor using Rating Enum
    public Movie(String title, LocalDate releaseDate, Integer duration, String genre, Rating rating, Studio studio) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.studio = studio;
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

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    // toString() method (excluding actors to prevent infinite recursion)
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +  // ✅ Now prints ENUM instead of BigDecimal
                ", studio=" + (studio != null ? studio.getStudioName() : "null") +
                '}';
    }
}
