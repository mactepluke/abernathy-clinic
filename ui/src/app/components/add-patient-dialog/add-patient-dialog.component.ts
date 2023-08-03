import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import {NgIf} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";

@Component({
  selector: 'app-add-patient-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    NgIf,
    MatDialogModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],/*
  providers:[
    MatNativeDateModule,
    MatDatepickerModule
  ],*/
  templateUrl: './add-patient-dialog.component.html',
  styleUrls: ['./add-patient-dialog.component.css']
})
export class AddPatientDialogComponent implements OnInit {
  form!: FormGroup;

  constructor(public dialogRef: MatDialogRef<AddPatientDialogComponent>,
              private formBuilder : FormBuilder) {}

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      family: [null, Validators.required],
      given: [null, Validators.required],
      dob: [null, Validators.required],
      sex: [null, Validators.required],
      address: [null, null],
      phone: [null, null]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onAdd(): void {
    this.dialogRef.close(this.form.value);
  }

}
