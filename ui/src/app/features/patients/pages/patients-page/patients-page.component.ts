import {Component} from '@angular/core';
import {PatientsTableComponent} from "../../components/patients-table/patients-table.component";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {AddPatientDialogComponent} from "../../components/add-patient-dialog/add-patient-dialog.component";
import {Patient} from "../../models/Patient";
import {PatientService} from "../../services/patient.service";
import {DisplayService} from "../../../../general/services/display.service";


@Component({
  selector: 'app-patients',
  standalone: true,
  templateUrl: './patients-page.component.html',
  imports: [
    PatientsTableComponent,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogModule
  ],
  providers: [
    PatientService,
    DisplayService
  ],
  styleUrls: ['./patients-page.component.css']
})
export class PatientsPageComponent {
  patient!: Patient;

  constructor(public dialog: MatDialog,
              private patientService: PatientService,
              private displayService: DisplayService) {
  }

  addPatient(): void {
    const dialogRef = this.dialog.open(AddPatientDialogComponent);

    dialogRef.afterClosed().subscribe(patient => {
      this.patient = patient;

      this.patientService.addPatient(this.patient).subscribe({
          next: () => {
            console.log('Patient added successfully');
            this.displayService.openSnackBar('Patient added successfully');
            window.location.reload();
          },
          error: (): void => {
            this.displayService.openSnackBar('Error: could not add patient');
          }
        }
      );
    });
  }
}
