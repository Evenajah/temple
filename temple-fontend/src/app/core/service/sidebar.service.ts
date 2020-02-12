import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class SidebarService {
  private sideBar = new BehaviorSubject<boolean>(false);
  constructor() { }

  sidebar(): BehaviorSubject<boolean> {
    return this.sideBar;
  }

  switchBar(data: boolean) {
    this.sideBar.next(data);
  }

  openSideBar() {
    this.sideBar.next(true);
  }

  closeSideBar() {
    this.sideBar.next(false)
  }

  // destroy() {
  //   this.sideBar.unsubscribe();
  // }

}
