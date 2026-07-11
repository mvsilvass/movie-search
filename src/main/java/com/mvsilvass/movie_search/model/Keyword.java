package com.mvsilvass.movie_search.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {

    @Id
    @Column(name = "keyword_id")
    @Setter(AccessLevel.NONE)
    private Long keywordId;

    private String name;

}
