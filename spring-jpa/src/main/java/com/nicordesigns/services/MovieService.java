package com.nicordesigns.services;

import com.nicordesigns.dto.MovieDTO;
import com.nicordesigns.entities.Movie;
import com.nicordesigns.entities.Studio;
import com.nicordesigns.entities.Actor;
import com.nicordesigns.repositories.MovieRepository;
import com.nicordesigns.repositories.StudioRepository;
import com.nicordesigns.repositories.ActorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Fetch a movie by ID and convert it to a DTO.
     */
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        return convertToDTO(movie);
    }

    /**
     * Create a new movie from a MovieDTO.
     */
    @Transactional
    public MovieDTO createMovie(MovieDTO dto) {
        Movie movie = convertToEntity(dto);

        // Save the movie in the database
        movie = movieRepository.save(movie);
        return convertToDTO(movie);
    }

    /**
     * Update the movie title.
     */
    @Transactional
    public void updateMovieTitle(Long id, String newTitle) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        movie.setTitle(newTitle);
        movieRepository.save(movie);
    }

    /**
     * Convert Movie entity to MovieDTO.
     */
    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
        dto.setStudioName(movie.getStudio().getStudioName());
        dto.setActorNames(movie.getActors().stream()
                .map(Actor::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    /**
     * Convert MovieDTO to Movie entity.
     */
    private Movie convertToEntity(MovieDTO dto) {
        Movie movie = modelMapper.map(dto, Movie.class);

        // Handle Studio
        Studio studio = studioRepository.findByStudioName(dto.getStudioName())
                .orElseGet(() -> {
                    Studio newStudio = new Studio();
                    newStudio.setStudioName(dto.getStudioName());
                    return studioRepository.save(newStudio);
                });
        movie.setStudio(studio);

        // Handle Actors
        List<Actor> actors = dto.getActorNames().stream()
                .map(name -> actorRepository.findByActorName(name)
                        .orElseGet(() -> {
                            Actor newActor = new Actor();
                            newActor.setName(name);
                            return actorRepository.save(newActor);
                        }))
                .collect(Collectors.toList());
        movie.setActors(actors);

        return movie;
    }
}
