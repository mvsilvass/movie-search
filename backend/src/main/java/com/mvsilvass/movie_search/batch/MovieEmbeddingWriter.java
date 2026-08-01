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

        for (Movie movie : chunk) {

            movieRepository.save(movie);

            Map<String, Object> metadata = Map.of(
                    "movieId", movie.getMovieId(),
                    "title", movie.getTitle(),
                    "genres", movie.getGenres(),
                    "keywords", movie.getKeywords()
            );

            Document document = new Document(
                    String.valueOf(movie.getMovieId()),
                    movie.getOverview(),
                    metadata
            );

            documents.add(document);
        }

        vectorStore.add(documents);
    }
}
