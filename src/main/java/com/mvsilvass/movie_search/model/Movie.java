package com.mvsilvass.movie_search.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id()
    @Column(name = "movie_id")
    @Setter(AccessLevel.NONE)
    private Long movieId;

    private String title;
    private String overview;
    private Double popularity;
    private LocalDate releaseDate;
    private Integer runtime;
    private Double voteAverage;
    private Integer voteCount;

    @ManyToMany
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @JoinTable(
            name = "movies_keywords",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords;

}
