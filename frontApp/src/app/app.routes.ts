import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '', loadComponent: () =>
      import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'DataLoader', loadComponent: () =>
      import('./components/data-loader/data-loader.component').then(m => m.DataLoaderComponent)
  },
];
