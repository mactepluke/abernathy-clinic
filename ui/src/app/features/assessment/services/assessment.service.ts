import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {Patient} from "../../patients/models/Patient";
import {DiabetesRiskLevel} from "../models/DiabetesRiskLevel";

@Injectable()
export class AssessmentService {

  constructor(private http: HttpClient) { }

  assessDiabetesRisk(patient: Patient): Observable<DiabetesRiskLevel> {
    return this.http.get<DiabetesRiskLevel>(`${environment.gateway}/assessment/assessDiabetesRisk?patientId=${patient.patientId}&sex=${patient.sex}&dob=${patient.dob}`);
  }

}
