import { Injectable } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';
import { filter, pairwise } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PrePathService {
  private url: string;
  private previousUrl: string = '';

  constructor(private router: Router) {
    this.router.events
      .pipe(filter((e: any) => e instanceof RoutesRecognized),
        pairwise()
      ).subscribe((e: any) => {
        this.previousUrl = e[0].url;
      });
  }

  setPreviousUrl() {
    return this.previousUrl;
  }
}
