import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThaiCalendarDirective } from './thai-calendar.directive';

@NgModule({
  declarations: [ThaiCalendarDirective],
  imports: [
    CommonModule
  ],
  exports: [ThaiCalendarDirective]
})
export class ThaiCalendarModule { }
