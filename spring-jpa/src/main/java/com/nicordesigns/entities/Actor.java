package com.nicordesigns.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
class Actor {
  @Id
  private Long id;
  @ManyToMany(mappedBy="actors")
  private List<Movie> movies;
}

