package com.mvsilvass.movie_search.batch;

import com.mvsilvass.movie_search.repository.MovieRepository;
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

    private final MovieRepository movieRepository;
    private final VectorStore vectorStore;

    @Override
    public void write(Chunk<? extends Movie> chunk) {
        List<Document> documents = new ArrayList<>();
        movieRepository.saveAll(chunk.getItems());

        for (Movie movie : chunk) {

            Map<String, Object> metadata = Map.of(
                    "movieId", movie.getMovieId(),
                    "title", movie.getTitle(),
                    "genres", movie.getGenres(),
                    "keywords", movie.getKeywords()
            );

            String content = String.format(
                    "Title: %s. Genres: %s. Keywords: %s. Overview: %s",
                    movie.getTitle(),
                    movie.getGenres(),
                    movie.getKeywords(),
                    movie.getOverview()
            );

            Document document = new Document(
                    String.valueOf(movie.getMovieId()),
                    content,
                    metadata
            );

            documents.add(document);
        }

        vectorStore.add(documents);
    }
}
