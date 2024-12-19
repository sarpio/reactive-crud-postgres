import { Component } from '@angular/core';
import {MatFormField, MatLabel, MatFormFieldModule} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-person-add-edit',
  imports: [
    MatFormField,
    MatInput, MatLabel,
    MatFormFieldModule, MatButton
  ],
  templateUrl: './person-add-edit.component.html',
  styleUrl: './person-add-edit.component.scss'
})
export class PersonAddEditComponent {

}
