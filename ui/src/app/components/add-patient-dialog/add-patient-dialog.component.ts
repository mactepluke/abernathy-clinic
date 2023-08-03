import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import {NgIf} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatSelectModule} from "@angular/material/select";

interface Sex {
  value: string;
  viewValue: string;
}

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
    MatSelectModule
  ],
  providers:[
    MatDatepickerModule
  ],
  templateUrl: './add-patient-dialog.component.html',
  styleUrls: ['./add-patient-dialog.component.css']
})

export class AddPatientDialogComponent implements OnInit {
  form!: FormGroup;
  sexes: Sex[] = [
    {value: 'M', viewValue: 'Male'},
    {value: 'F', viewValue: 'Female'}
  ];

  constructor(public dialogRef: MatDialogRef<AddPatientDialogComponent>,
              private formBuilder : FormBuilder) {}

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      family: [null, [Validators.required, Validators.pattern("^[A-Za-zÀ-ÿ- ]{3,50}$")]],
      given: [null, [Validators.required, Validators.pattern("^[A-Za-zÀ-ÿ- ]{3,50}$")]],
      dob: [null, Validators.required],
      sex: [null, Validators.required],
      address: [null, Validators.pattern("^[A-Za-zÀ-ÿ0-9-, ]{10,250}$")],
      phone: [null, Validators.pattern("^[0-9-. ]{8,20}$")]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onAdd(): void {
    this.dialogRef.close(this.form.value);
  }

}
