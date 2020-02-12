import { Component, OnInit } from '@angular/core';

import { AngularFirestore } from '@angular/fire/firestore';
import { Observable } from 'rxjs/internal/Observable';

import { Router, NavigationEnd } from '@angular/router';
import { FirebaseService } from 'src/app/shared/service/firebase.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss'],
  providers: [AngularFirestore]
})
export class NotificationComponent implements OnInit {
  notiDisable: boolean = true;
  currentUrl: string;
  items: Observable<any[]>;
  userId: string;
  numberOfNotice: number;
  // เรียก current path เพื่อซ่อนการแจ้งเตือน
  constructor(private router: Router, private fireBase: FirebaseService) {
    this.router.events.subscribe((_: NavigationEnd) => this.currentUrl = _.url);

  }

  ngOnInit() {
    this.userId = localStorage.getItem('userId');
    this.fireBase.getCountNoticeByUserID(+(this.userId)).subscribe(res => {
      this.numberOfNotice = res;
    });
  }
  toggle() {
    this.notiDisable = false;
  }
}