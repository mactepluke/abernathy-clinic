import { Component } from '@angular/core';
import {PatientsTableComponent} from "../../components/patients-table/patients-table.component";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {AddPatientDialogComponent} from "../../components/add-patient-dialog/add-patient-dialog.component";
import {Patient} from "../../models/Patient";
import {PatientService} from "../../services/PatientService";


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
    NgIf,
    MatDialogModule
  ],
  providers: [
    PatientService
  ],
  styleUrls: ['./patients-page.component.css']
})
export class PatientsPageComponent {
  patient!: Patient;

  constructor(public dialog: MatDialog, private patientService: PatientService) {}

  addPatient() {
    const dialogRef = this.dialog.open(AddPatientDialogComponent);

    dialogRef.afterClosed().subscribe(patient => {
      console.log('The dialog was closed');
      this.patient = patient;
      console.log(this.patient);
      this.patientService.addPatient(this.patient).subscribe( () => window.location.reload());
    });
  }
}
