import { Injectable } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Patient} from "../models/Patient";

@Injectable()
export class PatientRecordService {

  constructor(private formBuilder : FormBuilder) {
  }

  createPatientForm(): FormGroup  {
    return this.formBuilder.group({
      family: [null, [Validators.required, Validators.pattern("^[A-Za-zÀ-ÿ- ]{3,50}$")]],
      given: [null, [Validators.required, Validators.pattern("^[A-Za-zÀ-ÿ- ]{3,50}$")]],
      dob: [null, Validators.required],
      sex: [null, Validators.required],
      address: [null, Validators.pattern("^[A-Za-zÀ-ÿ0-9-, ]{10,250}$")],
      phone: [null, Validators.pattern("^[0-9-. ]{8,20}$")]
    });
  }

  equals(patient1: Patient, patient2: Patient): boolean {
    return patient1.family === patient2.family
      && patient1.given === patient2.given
      && patient1.sex === patient2.sex
      && patient1.dob === patient2.dob
      && patient1.address === patient2.address
      && patient1.phone === patient2.phone;
  }

}
