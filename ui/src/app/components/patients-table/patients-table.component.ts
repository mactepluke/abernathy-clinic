import { AfterViewInit, Component, ViewChild } from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule, Sort} from '@angular/material/sort';
import {MatTable, MatTableModule} from '@angular/material/table';
import { PatientsTableDataSource} from './patients-table-datasource';
import {DatePipe} from "@angular/common";
import {PatientService} from "../../services/PatientService";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {Patient} from "../../models/Patient";

@Component({
  selector: 'app-patients-table',
  standalone: true,
  templateUrl: './patients-table.component.html',
  imports: [
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    DatePipe,
    MatInputModule,
    MatButtonModule
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
  displayedColumns = ['family', 'given', 'dob', 'sex', 'address', 'phone', 'action'];

  constructor(private patientService: PatientService) {
    this.dataSource = new PatientsTableDataSource();
  }

  ngAfterViewInit(): void {

    this.patientService.findPatients().subscribe((data) => {
      this.dataSource.data = data;
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
  }

  viewPatient(): void  {
  }

}


