import {Component, OnInit, ViewChild} from '@angular/core';
import {StaffService} from '../../services/staff.service';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {Person} from '../../model/StaffModel';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-personnel-list',
  imports: [MatTableModule, MatSortModule, MatPaginatorModule, MatButton, RouterLink,],
  templateUrl: './personnel-list.component.html',
  styleUrl: './personnel-list.component.scss'
})
export class PersonnelListComponent implements OnInit {

  currentPage: number = 0;
  pageSize: number = 10;
  sortByColumn: string = 'id'
  order: 'asc' | 'desc' = 'asc';
  TOTAL_COUNT: number | null = 0;

  constructor(private staffService: StaffService) {
  }

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'job', 'phone', 'actions'];
  dataSource: MatTableDataSource<Person> = new MatTableDataSource<Person>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit(): void {
    this.getData();
  }

  getData(): void {
    this.staffService.getPageableList(this.currentPage, this.pageSize, this.order, this.sortByColumn).subscribe({
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
    this.getData();
  }

  onSortChange(event: any): void {
    this.order = this.order === 'asc' ? 'desc' : 'asc';
    this.sortByColumn = event.active;
    this.getData();
  }
}
