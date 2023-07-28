import { AfterViewInit, Component, ViewChild } from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTable, MatTableModule} from '@angular/material/table';
import { PatientsTableDataSource, PatientsTableItem } from './patients-table-datasource';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-patients-table',
  standalone: true,
  templateUrl: './patients-table.component.html',
  imports: [
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    DatePipe
  ],
  styleUrls: ['./patients-table.component.css']
})
export class PatientsTableComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<PatientsTableItem>;
  dataSource: PatientsTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['family', 'given', 'dob', 'sex', 'address', 'phone'];

  constructor() {
    this.dataSource = new PatientsTableDataSource();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
