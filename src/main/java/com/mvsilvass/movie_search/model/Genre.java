package com.mvsilvass.movie_search.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @Column(name = "genre_id")
    @Setter(AccessLevel.NONE)
    private Long genreId;

    private String name;

}
