import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'nullToDash',
  standalone: true
})
export class NullToDashPipe implements PipeTransform {

  transform(value: string): string {
    return value === 'null' ? '–' : value;
  }

}
