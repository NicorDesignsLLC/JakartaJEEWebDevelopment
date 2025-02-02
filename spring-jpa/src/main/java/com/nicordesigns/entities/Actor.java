package com.nicordesigns.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Actor")
public class Actor implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActorId", nullable = false, unique = true)
    private Long id;

    @Column(name = "ActorName", nullable = false, length = 100)
    private String name;

    @Column(name = "ActorBirthDate")
    private LocalDate birthDate;

    @Column(name = "ActorNationality", nullable = false, length = 50)
    private String nationality;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
