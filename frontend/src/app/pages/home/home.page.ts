import { Component, inject, signal } from '@angular/core';

import { MovieCardComponent } from '../../components/movie-card/movie-card.component';

import { SearchBarComponent } from '../../components/search-bar/search-bar.component';
import { MovieService } from '../../services/movie-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [SearchBarComponent, MovieCardComponent],
  templateUrl: './home.page.html',
  styleUrl: './home.page.css',
})
export class HomePage {
  private movieService = inject(MovieService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  currentPage = this.movieService.currentPage;
  totalPages = this.movieService.totalPages;
  movies = this.movieService.movies;
  isSearching = signal(false);

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      const page = params['page'] ? Number(params['page']) : 0;
      this.movieService.getTopRatedMovies(page).subscribe();
    });
  }

  onSearch(query: string) {
    this.route.queryParams.subscribe((params) => {
      const page = params['page'] ? Number(params['page']) : 0;

      if (query && query.trim() !== '') {
        this.movieService.searchMovies(query).subscribe();
        this.isSearching.set(true);
      } else {
        this.movieService.getTopRatedMovies(page).subscribe();
        this.isSearching.set(false);
      }
    });
  }

  viewDetails(tmdbId: number) {
    this.router.navigate(['/movies', tmdbId]);
  }

  changePage(page: number) {
    if (page < 0 || page >= this.totalPages()) return;

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { page: page },
      queryParamsHandling: 'merge',
    });

    this.movieService.getTopRatedMovies(page).subscribe();
  }

  previousPage(event: Event) {
    event.preventDefault();
    this.changePage(this.currentPage() - 1);
  }

  nextPage(event: Event) {
    event.preventDefault();
    this.changePage(this.currentPage() + 1);
  }
}
