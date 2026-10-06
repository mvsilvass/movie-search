import { Genre } from './genre.model';
import { Keyword } from './keyword.model';

export interface MovieSummary {
  tmdbId: number;
  title: string;
  posterPath: string;
}

export interface Movie {
  tmdbId: number;
  title: string;
  posterPath: string | null;
  overview: string;
  popularity: number;
  releaseDate: Date;
  runtime: number;
  voteAverage: number;
  voteCount: number;
  genres: Genre[];
  keywords: Keyword[];
}
