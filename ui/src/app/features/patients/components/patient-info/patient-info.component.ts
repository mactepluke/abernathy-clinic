import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Patient} from "../../models/Patient";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatCardModule} from "@angular/material/card";
import {PatientService} from "../../services/patient.service";
import {PatientRecordService} from "../../services/patient-record.service";
import {Router} from "@angular/router";
import {DeleteDialogComponent} from "../../../../shared/components/delete-dialog/delete-dialog.component";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {SnackBarComponent} from "../../../../general/components/snack-bar/snack-bar.component";
import {HistoryService} from "../../../history/services/history.service";
import {DiabetesAssessmentComponent} from "../../../assessment/diabetes-assessment/diabetes-assessment.component";

interface Sex {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-patient-info',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatDatepickerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatCardModule,
    MatSnackBarModule,
    DiabetesAssessmentComponent
  ],
  providers: [
    PatientService,
    HistoryService,
    PatientRecordService
  ],
  templateUrl: './patient-info.component.html',
  styleUrls: ['./patient-info.component.css']
})
export class PatientInfoComponent implements OnInit, AfterViewInit {
  @Input()
  currentPatient!: Patient;
  newPatient!: Patient;
  sexes: Sex[] = [
    {value: 'M', viewValue: 'Male'},
    {value: 'F', viewValue: 'Female'}
  ];
  form!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private patientService: PatientService,
              private historyService: HistoryService,
              private patientRecordService: PatientRecordService,
              private router: Router,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.form = this.patientRecordService.createPatientForm();
  }

  ngAfterViewInit(): void {
    this.onReset();
  }

  onReset(): void {
    this.form.setValue({
      family: this.currentPatient.family,
      given: this.currentPatient.given,
      sex: this.currentPatient.sex,
      dob: this.currentPatient.dob,
      address: this.currentPatient.address === 'null' ? '' : this.currentPatient.address,
      phone: this.currentPatient.phone === 'null' ? '' : this.currentPatient.phone,
    });
  }

  onUpdate(): void {
    this.newPatient = this.form.value;
    if (this.patientRecordService.equals(this.newPatient, this.currentPatient)) {
      console.log("No fields changed.")
    } else {
      this.patientService.updatePatient(this.currentPatient.family, this.currentPatient.given, this.newPatient).subscribe((patient: Patient): void => {
        this.currentPatient = patient;
        window.location.reload();
        this.openSnackBar();
        console.log("Update successful.")
      });
    }
  }

  openSnackBar(): void  {
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 3000,
      data: 'rrr' // TODO try to make something of it
    });
  }

  onDelete(): void {
    const dialogRef: MatDialogRef<DeleteDialogComponent> = this.dialog.open(DeleteDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.historyService.deleteAllNotes(this.currentPatient.patientId.toString()).subscribe(
          () => this.patientService.deletePatient(this.currentPatient.family, this.currentPatient.given)
            .subscribe(() => this.router.navigate(['mediscreen-abernathy/patients'])));
      }
    });
  }

}
