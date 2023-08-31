import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'riskColor',
  standalone: true
})
export class RiskColorPipe implements PipeTransform {

  transform(riskLevel: string): string {

    console.log(riskLevel);

    switch (riskLevel)  {
      case 'NONE': return 'green';
      case 'BORDERLINE': return 'goldenrod';
      case 'IN_DANGER': return 'orange';
      case 'EARLY_ONSET': return 'red';
      default: return 'black';
    }
  }

}
