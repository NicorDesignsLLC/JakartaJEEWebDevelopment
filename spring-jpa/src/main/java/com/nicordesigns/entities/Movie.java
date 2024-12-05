package com.nicordesigns.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
class Movie {
  @Id
  private Long id;
  @ManyToOne
  private Studio studio;
  @ManyToMany
  private List<Actor> actors;
}
