import { Routes } from '@angular/router';

import { MovieDetailsPage } from './pages/movie-details/movie-details.page';
import { HomePage } from './pages/home/home.page';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: HomePage,
  },
  {
    path: 'movies/:tmdbId',
    component: MovieDetailsPage,
  },
];
