import {Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {PageNotFoundComponent} from './shared/components/page-not-found/page-not-found.component';
import {PersonnelListComponent} from './components/personnel-list/personnel-list.component';
import {PersonAddEditComponent} from './components/person-add-edit/person-add-edit.component';

export const routes: Routes = [
  {path: '', component: HomeComponent, pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'list', component: PersonnelListComponent},
  {path: 'personnel', component: PersonAddEditComponent},
  {path:'**', component: PageNotFoundComponent}
];
