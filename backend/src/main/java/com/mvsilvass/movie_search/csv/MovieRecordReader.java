package com.mvsilvass.movie_search.csv;

import com.mvsilvass.movie_search.csv.model.MovieRecord;
import com.mvsilvass.movie_search.exception.MovieCsvParseException;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;

@Component
public class MovieRecordReader {

    public List<MovieRecord> readAll(String filePath) {
        try {

            // Usa o RFC 4180 porque o dataset do TMDB traz aspas duplicadas ("") dentro do JSON (ex: ""trudy jackson"").
            // O CSVParser padrão tentaria buscar uma barra invertida ('\') como escape e quebraria o mapeamento.
            RFC4180Parser parser = new RFC4180ParserBuilder()
                    .withSeparator(',')
                    .withQuoteChar('"')
            .build();

            CSVReader reader = new CSVReaderBuilder(
                    new FileReader(filePath))
                    .withCSVParser(parser).build();

            return new CsvToBeanBuilder<MovieRecord>(reader)
                        .withType(MovieRecord.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build()
                    .parse();

        }catch (Exception e) {
            throw new MovieCsvParseException("Falha crítica ao ler e processar o arquivo CSV.", e);
        }
    }

}
