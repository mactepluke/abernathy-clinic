import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {Patient} from "../models/Patient";
import {DatePipe} from "@angular/common";


@Injectable()
export class PatientService {

  constructor(private http: HttpClient, private datePipe: DatePipe) {
  }

  findPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${environment.gateway}/patient/getall`);
  }

  findPatient(family: string, given: string): Observable<Patient> {
    return this.http.get<Patient>(`${environment.gateway}/patient/get?family=${family}&given=${given}`);
  }

  addPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(`${environment.gateway}/patient/add?family=${patient.family}&given=${patient.given}&sex=${patient.sex}&dob=${this.datePipe.transform(patient.dob, 'yyyy-MM-dd')}&address=${patient.address}&phone=${patient.phone}`, '');
  }

  updatePatient(family: string, given: string, newPatient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${environment.gateway}/patient/update?family=${family}&given=${given}&newFamily=${newPatient.family}&newGiven=${newPatient.given}&newSex=${newPatient.sex}&newDob=${this.datePipe.transform(newPatient.dob, 'yyyy-MM-dd')}&newAddress=${newPatient.address}&newPhone=${newPatient.phone}`, '');
  }

  deletePatient(family: string, given: string):  Observable<Patient> {
    return this.http.delete<Patient>(`${environment.gateway}/patient/delete?family=${family}&given=${given}`);
  }
}
