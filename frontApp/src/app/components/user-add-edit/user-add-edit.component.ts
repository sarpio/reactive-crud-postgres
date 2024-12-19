import { Component, inject, Inject, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../model/User';

@Component({
  selector: 'app-user-add-edit',
  imports: [MatFormFieldModule, MatInputModule, MatCardModule, MatButtonModule,
    MatIconModule, FormsModule, NgxMaskDirective, ReactiveFormsModule
  ],
  templateUrl: './user-add-edit.component.html',
  styleUrl: './user-add-edit.component.scss'
})
export class UserAddEditComponent implements OnInit {

  title = 'Add New User';
  dialogData: any;
  form!: FormGroup;
  user!: User;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private ref: MatDialogRef<UserAddEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }


  ngOnInit(): void {
    if (this.data.id > 0) {
      this.title = 'Update User Data'
      this.dialogData = this.data.id
      this.userService.getSingle(this.dialogData).subscribe({
        next: data => {
          this.form.setValue({
            id: data.id,
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            job: data.job,
            phone: data.phone
          });
        }
      });
    }
    this.form = this.formBuilder.group({
      id: new FormControl(),
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      job: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required]),
    });
  }

  save() {
    let userData: User = {
      id: this.form.value.id as number,
      firstName: this.form.value.firstName as string,
      lastName: this.form.value.lastName as string,
      email: this.form.value.email as string,
      job: this.form.value.job as string,
      phone: this.form.value.phone as string,
    }
    if (this.data.id > 0) {
      this.userService.updateUser(userData).subscribe({
        next: () => {
          alert("User Updated Successfully");
        },
        error: error => {
          console.error(error.message);
          alert(error.message);
        }
      })
    } else {
      this.userService.addUser(userData).subscribe({
        next: () => {
          alert("User Added Successfully");
        },
        error: error => {
          console.error(error.message);
        }
      })
    }
    this.closePopup();
  }

  closePopup(): void {
    this.ref.close();
  }

}
