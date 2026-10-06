import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';

import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

import type { MovieSummary, Movie } from '../models/movie.model';
import type { Page } from '../models/page.model';

@Service()
export class MovieService {
  private http = inject(HttpClient);

  private selectedMovieState = signal<Movie | null>(null);
  private moviesState = signal<MovieSummary[]>([]);
  private currentPageState = signal<number>(0);
  private totalPagesState = signal<number>(0);

  public readonly movies = this.moviesState.asReadonly();
  public readonly currentPage = this.currentPageState.asReadonly();
  public readonly totalPages = this.totalPagesState.asReadonly();
  public readonly selectedMovie = this.selectedMovieState.asReadonly();

  public searchMovies(query: string): Observable<MovieSummary[]> {
    const params = new HttpParams().set('query', query);

    return this.http
      .get<MovieSummary[]>(`${environment.apiUrl}/api/movies/search`, { params })
      .pipe(
        tap((movies) => {
          this.moviesState.set(movies);
        }),
        catchError((error) => {
          return throwError(() => error);
        }),
      );
  }

  public getMovieByTmdbId(tmdbId: number): Observable<Movie> {
    return this.http.get<Movie>(`${environment.apiUrl}/api/movies/${tmdbId}`).pipe(
      tap((movie) => this.selectedMovieState.set(movie)),
      catchError((error) => {
        return throwError(() => error);
      }),
    );
  }

  public getTopRatedMovies(page: number = 0, size: number = 25): Observable<Page<MovieSummary>> {
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());

    return this.http
      .get<Page<MovieSummary>>(`${environment.apiUrl}/api/movies/top-rated`, { params })
      .pipe(
        tap((response) => {
          this.moviesState.set(response.content);
          this.currentPageState.set(response.number);
          this.totalPagesState.set(response.totalPages);
        }),
        catchError((error) => {
          return throwError(() => error);
        }),
      );
  }
}
