package com.nicordesigns.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
class Studio {
  @Id
  private Long id;
  
  @OneToMany(mappedBy="studio")
  private List<Movie> movies;
}