import { AfterViewInit, Component, ViewChild } from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTable, MatTableModule} from '@angular/material/table';
import { PatientsTableDataSource, Patient } from './patients-table-datasource';
import {DatePipe} from "@angular/common";
import {PatientService} from "../../services/PatientService";

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
  providers: [
    PatientService
  ],
  styleUrls: ['./patients-table.component.css']
})
export class PatientsTableComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<Patient>;
  dataSource: PatientsTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['family', 'given', 'dob', 'sex', 'address', 'phone'];

  constructor(private patientService: PatientService) {
    this.dataSource = new PatientsTableDataSource(patientService);
  }

  ngAfterViewInit(): void {

    this.dataSource.loadPatients();
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
