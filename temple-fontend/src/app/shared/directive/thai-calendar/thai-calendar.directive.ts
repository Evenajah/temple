import { Directive } from '@angular/core';
import { Calendar } from 'primeng/primeng';

@Directive({
  selector: '[cdgsThaiCalendar]'
})
export class ThaiCalendarDirective {
  constructor(private calendar: Calendar) {

    this.calendar.createMonths = (month, year) => {
      this.calendar.months = this.calendar.months = [];
      for (let i = 0; i < this.calendar.numberOfMonths; i++) {
          let m = month + i;
          if (year > 2100) {
            year = year - 543;
          }
          let y = year;
          if (m > 11) {
              m = m % 11 - 1;
              y = year + 1;
          }
          this.calendar.months.push(this.calendar.createMonth(m, y));
      }
    };

    const oldParseValueFromString  = this.calendar.parseValueFromString.bind(this.calendar);
    this.calendar.parseValueFromString = (text: string) => {
      text = this.convertBDyearToADyearString(text);
      return oldParseValueFromString(text);
    };

    const oldCreateMonth = this.calendar.createMonth.bind(this.calendar);
    this.calendar.createMonth = (month: any, year: any) => {
      const shiftYear = year <= 2100 ? year : year - 543;
      const result =  oldCreateMonth(month, shiftYear);
      if (result.year && result.year <= 2100) {
        result.year = result.year + 543;
      }
      return result;
    };

    const showOverlayCustom  = () => {
      if (!this.calendar.overlayVisible) {
        this.calendar.updateUI();
        this.calendar.currentYear = this.convertADyearToBDyearNumber(this.calendar.currentYear);
        this.calendar.overlayVisible = true;
      }
    };
    this.calendar.showOverlay = showOverlayCustom.bind(this);
    const oldPopulateYearOptions = this.calendar.populateYearOptions.bind(this.calendar);
    this.calendar.populateYearOptions = (start: any, end: any) => {
      oldPopulateYearOptions(start, end);
      this.calendar.yearOptions = this.calendar.yearOptions.map(year => {
        if (year <= 2100) {
          return year + 543;
        }
      });
    };

    const oldFormatDate = this.calendar.formatDate.bind(this.calendar);
    this.calendar.formatDate = (date, format) => {
      const result = oldFormatDate(date, format);
      return this.convertADyearToBDyearString(result);
   };
    this.calendar.dateFormat =  'dd/mm/yy';
    this.calendar.shortYearCutoff = 555;
    this.calendar.locale = {
      firstDayOfWeek: 0,
      dayNames: [
        'อาทิตย์',
        'จันทร์',
        'อังคาร',
        'พุธ',
        'พฤหัสบดี',
        'ศุกร์',
        'เสาร์'
      ],
      dayNamesShort: ['อา', 'จ', 'อ', 'พ', 'พฤ', 'ศ', 'ส'],
      dayNamesMin: ['อา', 'จ', 'อ', 'พ', 'พฤ', 'ศ', 'ส'],
      monthNames: [
        'มกราคม',
        'กุมภาพันธ์',
        'มีนาคม',
        'เมษายน',
        'พฤษภาคม',
        'มิถุนายน',
        'กรกฎาคม',
        'สิงหาคม',
        'กันยายน',
        'ตุลาคม',
        'พฤศจิกายน',
        'ธันวาคม'
      ],
      monthNamesShort: [
        'ม.ค.',
        'ก.พ.',
        'มี.ค.',
        'เม.ย.',
        'พ.ค.',
        'มิ.ย.',
        'ก.ค.',
        'ส.ค.',
        'ก.ย.',
        'ต.ค.',
        'พ.ย.',
        'ธ.ค.'
      ],
      today: 'วันนี้',
      clear: 'ล้างข้อมูล'
    };
  }

  convertADyearToBDyearNumber(result: number): number {
    if (!result) {
      return result;
    }
    result = parseInt((result + '').replace(/(20|19)\d{2}/, (yearPatt: string) => {
      const year = parseInt(yearPatt, 0) + 543;
      return year + '';
    }), 0);
    return result;
  }

  convertADyearToBDyearString(result: string): string {
    if (!result) {
      return result;
    }
    result = result.replace(/(20|19)\d{2}/, (yearPatt: string) => {
      const year = parseInt(yearPatt, 0) + 543;
      return year + '';
    });
    return result;
  }

  convertBDyearToADyearString(result: string): string {
    if (!result) {
      return result;
    }
    result = result.replace(/25\d{2}/, (yearPatt: string) => {
      const year = parseInt(yearPatt, 0) - 543;
      return year + '';
    });
    return result;
  }
}
