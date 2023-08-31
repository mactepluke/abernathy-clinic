import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Patient} from "../../patients/models/Patient";
import {AssessmentService} from "../services/assessment.service";
import {DiabetesRiskLevel} from "../models/DiabetesRiskLevel";
import {MatCardModule} from "@angular/material/card";
import {RiskLevelPipe} from "../pipes/risk-level.pipe";
import {RiskColorPipe} from "../pipes/risk-color.pipe";

@Component({
  selector: 'app-diabetes-assessment',
  standalone: true,
  imports: [CommonModule, MatCardModule, RiskLevelPipe, RiskColorPipe],
  templateUrl: './diabetes-assessment.component.html',
  styleUrls: ['./diabetes-assessment.component.css'],
  providers: [
    AssessmentService
  ]
})
export class DiabetesAssessmentComponent implements OnInit, AfterViewInit {
  @Input()
  currentPatient!: Patient;
  diabetesRiskLevel!: DiabetesRiskLevel;

  constructor(private assessmentService: AssessmentService) {
  }

  ngOnInit(): void {
    this.diabetesRiskLevel = DiabetesRiskLevel.UNKNOWN;
  }

  ngAfterViewInit(): void {
    this.assessmentService.assessDiabetesRisk(this.currentPatient).subscribe( (riskLevel: DiabetesRiskLevel) => this.diabetesRiskLevel = riskLevel);
  }


}
