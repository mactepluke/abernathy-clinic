import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTable, MatTableModule} from '@angular/material/table';
import {HistoryTableDataSource} from './history-table-datasource';
import {HistoryService} from "../../services/history.service";
import {LightNote} from "../../models/LightNote";
import {Patient} from "../../../patients/models/Patient";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, NgIf, TitleCasePipe} from "@angular/common";
import {Router} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {DeleteDialogComponent} from "../../../../shared/components/delete-dialog/delete-dialog.component";
import {DisplayService} from "../../../../shared/services/display.service";

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
    MatDialogModule,
    NgIf
  ],
  providers: [
    HistoryService,
    DisplayService
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
  displayedColumns = ['view', 'title', 'dateTime', 'action'];

  constructor(private historyService: HistoryService,
              private router: Router,
              private dialog: MatDialog,
              private displayService: DisplayService) {
    this.dataSource = new HistoryTableDataSource();
  }

  ngAfterViewInit(): void {
    this.historyService.findHistory(this.currentPatient.patientId).subscribe((data: LightNote[]): void => {
      this.dataSource.data = data;
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    });
  }

  addNote(): void {
      this.router.navigate(['mediscreen-abernathy/patient-note',
        '-1',
        this.currentPatient.patientId,
        this.currentPatient.family,
        this.currentPatient.given]);
  }

  viewNote(id: string): void {
    this.router.navigate(['mediscreen-abernathy/patient-note',
      id,
      this.currentPatient.patientId,
      this.currentPatient.family,
      this.currentPatient.given]);
  }

  deleteNote(id: string): void {
    const dialogRef: MatDialogRef<DeleteDialogComponent> = this.dialog.open(DeleteDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.historyService.deleteNote(id)
            .subscribe({
              next: () => {
                console.log('Note successfully deleted');
                this.displayService.openSnackBar('Note successfully deleted');
                window.location.reload();
              },
              error: (): void => {
                this.displayService.openSnackBar('Error: could not delete note');
              }
            });
      }
    });
  }
}
