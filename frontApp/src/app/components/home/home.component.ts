import { Component } from '@angular/core';
import {UserListComponent} from '../user-list/user-list.component';

@Component({
  selector: 'app-home',
  imports: [
    UserListComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
