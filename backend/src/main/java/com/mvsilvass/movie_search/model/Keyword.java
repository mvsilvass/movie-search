package com.mvsilvass.movie_search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "keywords")
public class Keyword {

    @Id
    @JsonProperty("id")
    @Column(name = "keyword_id")
    @Setter(AccessLevel.NONE)
    private Long keywordId;

    private String name;

}