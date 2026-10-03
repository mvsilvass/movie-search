import { Component, input, output } from '@angular/core';
import { MovieCard } from '../../models/movie.model';

@Component({
  standalone: true,
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrl: './movie-card.component.css',
})
export class MovieCardComponent {
  movie = input.required<MovieCard>();
  clicked = output<number>();

  onClick() {
    this.clicked.emit(this.movie().tmdbId);
  }
}
