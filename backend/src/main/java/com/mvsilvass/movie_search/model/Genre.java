package com.mvsilvass.movie_search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {

    @Id
    @JsonProperty("id")
    @Column(name = "genre_id")
    @Setter(AccessLevel.NONE)
    private Long genreId;

    private String name;

}