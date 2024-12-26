package com.nicordesigns.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

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

    @Column(name = "MovieRating", precision = 3, scale = 1)
    private BigDecimal rating;

    @ManyToOne
    @JoinColumn(name = "MovieStudioId", nullable = false)
    private Studio studio;

    @ManyToMany
    @JoinTable(
        name = "Movie_Actor",
        joinColumns = @JoinColumn(name = "MovieId"),
        inverseJoinColumns = @JoinColumn(name = "ActorId")
    )
    private List<Actor> actors;

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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
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
}
