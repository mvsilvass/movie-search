import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe, TitleCasePipe } from '@angular/common';

import { MovieService } from '../../services/movie-service';
import type { Movie } from '../../models/movie.model';

@Component({
  selector: 'app-movie-detail',
  imports: [DatePipe, TitleCasePipe],
  templateUrl: './movie-details.page.html',
  styleUrl: './movie-details.page.css',
})
export class MovieDetailsPage {
  private movieService = inject(MovieService);
  private activatedRoute = inject(ActivatedRoute);
  private router = inject(Router);

  private movieId = signal<number | null>(null);
  movieDetails = signal<Movie | null>(null);

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('tmdbId');

    if (idParam) {
      const id = Number(idParam);
      this.movieId.set(id);

      this.movieService.getMovieByTmdbId(id).subscribe({
        next: (movie) => {
          this.movieDetails.set(movie);
        },
        error: (err) => {
          console.error('Erro ao buscar detalhes do filme:', err);
        },
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/home']);
  }
}
