package com.mvsilvass.movie_search.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.Chunk;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import com.mvsilvass.movie_search.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MovieEmbeddingWriter implements ItemWriter<Movie> {

    private final VectorStore vectorStore;

    @Override
    public void write(Chunk<? extends Movie> chunk) {
        List<Document> documents = new ArrayList<>();

        for (Movie movie : chunk) {

            Map<String, Object> metadata = Map.of(
                    "movieId", movie.id(),
                    "title", movie.title(),
                    "genres", movie.genres(),
                    "keywords", movie.keywords()
            );

            Document document = new Document(
                    String.valueOf(movie.id()),
                    movie.overview(),
                    metadata
            );

            documents.add(document);
        }

        vectorStore.add(documents);
    }
}
