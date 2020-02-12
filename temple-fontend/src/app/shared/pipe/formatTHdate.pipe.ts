import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
  name: 'formatTHdate'
})
export class FormatTHdatePipe implements PipeTransform {

  transform(date: Date , format: string = 'dd/MM/yyyy'): string {
    if(date) {
      date = new Date(date);  // if orginal type was a string
      date.setFullYear(date.getFullYear() + 543);
      return new DatePipe('th-TH').transform(date, format);
    } else {
      return '';
    }
  }

}
