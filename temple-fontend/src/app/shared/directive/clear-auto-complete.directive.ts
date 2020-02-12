import { Directive, Host, AfterViewInit } from '@angular/core';
import { AutoComplete } from 'primeng/primeng';

@Directive({
  selector: '[appClearAutoComplete]'
})
export class ClearAutoCompleteDirective implements AfterViewInit{
  
  constructor(@Host() private autoComplete: AutoComplete) { }

  ngAfterViewInit(): void {
    const element = (this.autoComplete.inputEL.nativeElement as HTMLInputElement);
    element.addEventListener('search', (event) => {
      if (element.value.length === 0 && !this.autoComplete.multiple) {
        this.autoComplete.hide();
        this.autoComplete.onClear.emit({data: (event.target as any ).value});
        this.autoComplete.onModelChange(element.value);
      }

    }, {capture: true});
  }

}
