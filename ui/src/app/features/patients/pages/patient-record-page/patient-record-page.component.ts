import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Patient} from "../../models/Patient";
import {PatientService} from "../../services/patient.service";
import {PatientInfoComponent} from "../../components/patient-info/patient-info.component";
import {HistoryTableComponent} from "../../../history/components/history-table/history-table.component";
import {DiabetesAssessmentComponent} from "../../../assessment/diabetes-assessment/diabetes-assessment.component";
import {NgIf} from "@angular/common";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-patient-record-page',
  standalone: true,
  templateUrl: './patient-record-page.component.html',
  styleUrls: ['./patient-record-page.component.css'],
  imports: [
    PatientInfoComponent,
    HistoryTableComponent,
    DiabetesAssessmentComponent,
    NgIf,
    MatProgressSpinnerModule
  ],
  providers: [
    PatientService
  ]
})
export class PatientRecordPageComponent implements OnInit {
  patient!: Patient;

  constructor(private route: ActivatedRoute, private patientService: PatientService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap): void => {
      this.patientService.findPatient(<string>params.get('family'), <string>params.get('given'))
        .subscribe((patient: Patient): void => {
        this.patient = patient;
      });
    });
  }

}
