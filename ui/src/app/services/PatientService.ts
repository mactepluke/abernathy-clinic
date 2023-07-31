import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Patient} from "../components/patients-table/patients-table-datasource";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";


@Injectable()
export class PatientService {

  constructor(private http: HttpClient) {
  }

  findPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${environment.apiUrl}/patient/getall`);
  }

}
