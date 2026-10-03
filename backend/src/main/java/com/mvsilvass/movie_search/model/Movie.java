package com.mvsilvass.movie_search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "movie_id")
    @Setter(AccessLevel.NONE)
    private UUID movieId;

    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;
    private String title;
    private String posterPath;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private Double popularity;
    private LocalDate releaseDate;
    private Double runtime;
    private Double voteAverage;
    private Integer voteCount;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movies_keywords",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords;

}