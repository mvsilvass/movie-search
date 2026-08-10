package com.mvsilvass.movie_search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VectorStoreConfig {

    @Value("${spring.ai.vectorstore.pgvector.dimensions}")
    private String dimensions;

    @Value("${spring.ai.vectorstore.pgvector.distance-type}")
    private String distanceType;

    @Value("${spring.ai.vectorstore.pgvector.index-type}")
    private String indexType;

    @Bean(name = "pgvectorstore")
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate,
                                   EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .dimensions(Integer.parseInt(dimensions))
                .distanceType(PgVectorStore.PgDistanceType.valueOf(distanceType))
                .indexType(PgVectorStore.PgIndexType.valueOf(indexType))
                .initializeSchema(true)
                .schemaName("public")
                .idType(PgVectorStore.PgIdType.INTEGER)
        .build();
    }

}
