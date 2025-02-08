package com.nicordesigns.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Studio")
public class Studio implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID
    @Column(name = "StudioId", nullable = false, unique = true)
    private Long id;

    @Column(name = "StudioName", nullable = false, length = 100)
    private String studioName;

    @Column(name = "YearFounded")
    private Integer yearFounded;  // Can also be java.time.Year

    @Column(name = "StudioHeadQuarters", nullable = false, length = 255)
    private String studioHeadquarters;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<Movie> movies;

    // Default Constructor
    public Studio() {}

    // Parameterized Constructor
    public Studio(String studioName, Integer yearFounded, String studioHeadquarters) {
        this.studioName = studioName;
        this.yearFounded = yearFounded;
        this.studioHeadquarters = studioHeadquarters;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getStudioHeadquarters() {
        return studioHeadquarters;
    }

    public void setStudioHeadquarters(String studioHeadquarters) {
        this.studioHeadquarters = studioHeadquarters;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    // toString() method (excluding movies to prevent infinite recursion)
    @Override
    public String toString() {
        return "Studio{" +
                "id=" + id +
                ", studioName='" + studioName + '\'' +
                ", yearFounded=" + yearFounded +
                ", studioHeadquarters='" + studioHeadquarters + '\'' +
                '}';
    }
}
