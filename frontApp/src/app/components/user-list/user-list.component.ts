import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../service/user.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { User } from '../../model/User';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatButton } from '@angular/material/button';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { Subscription } from 'rxjs';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UserAddEditComponent } from '../user-add-edit/user-add-edit.component';
import { UpperCasePipe } from '@angular/common';
import { PhonePipe } from '../../pipes/phone.pipe';

@Component({
  selector: 'app-user-list',
  imports: [MatTableModule, MatSortModule, MatPaginatorModule, MatButton, MatCardModule, MatDialogModule, UpperCasePipe, PhonePipe],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit, OnDestroy {

  /**
   * Default parameters
   **/
  currentPage: number = 0;
  pageSize: number = 10;
  sortByColumn: string = 'id'
  order: 'asc' | 'desc' = 'asc';
  TOTAL_COUNT: number | null = 0;
  subscription = new Subscription();

  constructor(private userService: UserService, private router: Router, private dialog: MatDialog) {
  }

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'job', 'phone', 'actions'];
  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>();


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit(): void {
    this.loadUsers();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadUsers(): void {
    this.subscription = this.userService.getAllPageable(this.currentPage, this.pageSize, this.order, this.sortByColumn).subscribe({
      next: (data: any) => {
        this.dataSource = data.body;
        this.TOTAL_COUNT = Number(data.headers.get('X-Total-Count')) || null;
      },
      error: error => {
        console.log('Got error message: ', error.message);
      }
    })
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsers();
  }

  onSortChange(event: any): void {
    this.order = this.order == 'asc' ? 'desc' : 'asc';
    this.sortByColumn = event.active;
    this.loadUsers();
  }

  edit(id: number) {
    this.openPopup(id);
  }

  addUser() {
    this.openPopup(0);
  }

  openPopup(id: number) {
    this.dialog.open(UserAddEditComponent, {
      width: '40%',
      exitAnimationDuration: '1000ms',
      enterAnimationDuration: '1000ms',
      data: {'id': id}
    }).afterClosed().subscribe(o => {
      this.loadUsers()
    })
  }

  delete(id: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.subscription = this.userService.deleteUser(id).subscribe({
        next: (res) => {
          this.loadUsers();
        }
      });
    }
  }

  protected readonly Number = Number;
}

