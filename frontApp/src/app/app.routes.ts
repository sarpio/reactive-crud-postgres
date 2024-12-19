import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '', loadComponent: () =>
      import('./components/home/home.component').then(m => m.HomeComponent)
  },
  // {
  //   path: 'user', loadComponent: () =>
  //     import('./components/user-add-edit/user-add-edit.component').then(m => m.UserAddEditComponent)
  // },
];
