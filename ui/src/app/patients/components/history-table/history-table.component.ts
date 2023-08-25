import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTable, MatTableModule} from '@angular/material/table';
import {HistoryTableDataSource} from './history-table-datasource';
import {HistoryService} from "../../../history/services/history.service";
import {LightNote} from "../../../history/models/LightNote";
import {Patient} from "../../models/Patient";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, TitleCasePipe} from "@angular/common";
import {Router} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {DeleteDialogComponent} from "../../../shared/components/delete-dialog/delete-dialog.component";

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
    TitleCasePipe,
    MatIconModule,
    MatDialogModule
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

  constructor(private historyService: HistoryService,
              private router: Router,
              private dialog: MatDialog) {
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

  addNote(): void {
      this.router.navigate(['mediscreen-abernathy/patient-note', '-1', this.currentPatient.patientId]);
  }

  viewNote(id: string): void {
    this.router.navigate(['mediscreen-abernathy/patient-note', id, this.currentPatient.patientId]);
  }

  deleteNote(id: string): void {
    const dialogRef: MatDialogRef<DeleteDialogComponent> = this.dialog.open(DeleteDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.historyService.deleteNote(id)
            .subscribe(() => window.location.reload());
      }
    });
  }
}
