import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Patient} from "../models/Patient";


@Injectable()
export class PatientService {

  constructor(private http: HttpClient) {
  }

  findPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${environment.apiUrl}/patient/getall`);
  }

}
