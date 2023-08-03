import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../models/Patient";
import {PatientService} from "../../services/PatientService";

@Component({
  selector: 'app-patient-record-page',
  standalone: true,
  templateUrl: './patient-record-page.component.html',
  styleUrls: ['./patient-record-page.component.css'],
  providers: [
    PatientService
  ]
})
export class PatientRecordPageComponent implements OnInit {
  patient!: Patient;

  constructor(private route: ActivatedRoute, private patientService: PatientService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.patientService.findPatient(<string>params.get('family'), <string>params.get('given'))
        .subscribe((patient) => {
        this.patient = patient;
      });
    });
  }

}
