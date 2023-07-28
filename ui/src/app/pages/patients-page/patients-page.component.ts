import { Component } from '@angular/core';
import {PatientsTableComponent} from "../../components/patients-table/patients-table.component";

@Component({
  selector: 'app-patients',
  standalone: true,
  templateUrl: './patients-page.component.html',
  imports: [
    PatientsTableComponent
  ],
  styleUrls: ['./patients-page.component.css']
})
export class PatientsPageComponent {

}
