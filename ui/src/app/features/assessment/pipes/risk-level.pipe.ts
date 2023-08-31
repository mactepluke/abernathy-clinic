import {Pipe, PipeTransform} from '@angular/core';
import {DiabetesRiskLevel} from "../models/DiabetesRiskLevel";

@Pipe({
  name: 'riskLevel',
  standalone: true
})
export class RiskLevelPipe implements PipeTransform {

  transform(riskLevel: string): string {

    console.log(riskLevel);

    switch (riskLevel)  {
      case 'NONE': return 'none';
      case 'BORDERLINE': return 'borderline';
      case 'IN_DANGER': return 'in danger';
      case 'EARLY_ONSET': return 'early onset';
      default: return 'Error processing data: the risk level is unknown';
    }
  }

}
