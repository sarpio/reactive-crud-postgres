import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { Message } from '../../model/Message';

@Component({
  selector: 'app-data-loader',
  imports: [MatFormFieldModule, MatInputModule, MatCardModule, MatButton, FormsModule],
  templateUrl: './data-loader.component.html',
  styleUrl: './data-loader.component.scss'
})
export class DataLoaderComponent {

  constructor(private userService: UserService) {
  }

  value = '';
  message!: Message;

  onInputChange(event: any): void {
    const inputValue = event.target.value;
    if (inputValue.length > 3) {
      this.value = inputValue.slice(0, 3);
      console.log(this.value);
    }
  }

  send() {
    this.userService.loadData(Number(this.value)).subscribe(
      response => {
        this.message = response;
        console.log(this.message);
      },
      error => {
        console.error(error.message);
        this.message = error.message;
      }
    )
  }
}
