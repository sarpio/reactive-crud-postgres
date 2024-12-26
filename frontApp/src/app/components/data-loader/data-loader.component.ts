import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { Message } from '../../model/Message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-data-loader',
  imports: [MatFormFieldModule, MatInputModule, MatCardModule, MatButton, FormsModule],
  templateUrl: './data-loader.component.html',
  styleUrl: './data-loader.component.scss'
})
export class DataLoaderComponent {

  constructor(private userService: UserService, private router: Router) {
  }

  value = '';
  message!: Message;

  onInputChange(event: any): void {
    // const inputValue = event.target.value;
    // if (inputValue.length > 3) {
    //   this.value = inputValue.slice(0, 3);
    //   console.log(this.value);
    // }
  }

  send(value: string) {
    this.userService.loadData(Number(value)).subscribe({
      next: (resp) => {
        this.router.navigate(['/']).then(r => {
          if (r) {
            console.log("Redirected to main page")
          } else {
            console.error("Unable redirect to main page");
          }
        });
      }
    })
  }
}
