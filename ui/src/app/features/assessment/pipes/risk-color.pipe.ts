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
      case 'BORDERLINE': return 'gold';
      case 'IN_DANGER': return 'darkorange';
      case 'EARLY_ONSET': return 'crimson';
      default: return 'black';
    }
  }

}
