import { Component, input, output } from '@angular/core';
import { MovieSummary } from '../../models/movie.model';

@Component({
  standalone: true,
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrl: './movie-card.component.css',
})
export class MovieCardComponent {
  movie = input.required<MovieSummary>();
  clicked = output<number>();

  onClick() {
    this.clicked.emit(this.movie().tmdbId);
  }
}
