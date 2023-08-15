import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTable, MatTableModule} from '@angular/material/table';
import {HistoryTableDataSource} from './history-table-datasource';
import {HistoryService} from "../../services/history.service";
import {LightNote} from "../../models/LightNote";
import {Patient} from "../../models/Patient";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, TitleCasePipe} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-history-table',
  standalone: true,
  templateUrl: './history-table.component.html',
  styleUrls: ['./history-table.component.css'],
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatButtonModule,
    DatePipe,
    TitleCasePipe
  ],
  providers: [
    HistoryService
  ]
})
export class HistoryTableComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<LightNote>;

  @Input()
  currentPatient!: Patient;
  dataSource: HistoryTableDataSource;


  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['title', 'dateTime', 'action'];

  constructor(private historyService: HistoryService, private router: Router) {
    this.dataSource = new HistoryTableDataSource();
  }

  ngAfterViewInit(): void {
    this.historyService.findHistory(this.currentPatient.patientId).subscribe((data) => {
      this.dataSource.data = data;
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
  }

  addNote() {
      this.router.navigate(['mediscreen-abernathy/patient-record', '-1']);
  }

  viewNote(id: string) {
    this.router.navigate(['mediscreen-abernathy/patient-record', id]);
  }
}
