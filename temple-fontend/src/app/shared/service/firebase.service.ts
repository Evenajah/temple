import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';
import { map } from 'rxjs/operators';
import { Notifications } from '../interfaces/notification';
import { ApiConstants } from '../../shared/constants/ApiConstants';
import { HttpClientService } from './http-client.service';
@Injectable({
  providedIn: 'root'
})
export class FirebaseService {

  constructor(private db: AngularFirestore, private http: HttpClientService) { }
  getCountNoticeByUserID(userId: number) {
    return this.db.collection('notification', param => param.where('memberID', '==', userId).where('notificationStatus', '==', 0)
      .orderBy('notificationTime', 'desc'))
      .snapshotChanges().pipe(map(data => data.length));
  }

  getDataNoticeByUserID(userId: number) {
    return this.db.collection('notification', param => param.where('memberID', '==', userId).where('notificationStatus', '==', 0)
      .orderBy('notificationTime', 'desc'))
      .snapshotChanges().pipe(map(data => data.map(
        dataMap => { return { id: dataMap.payload.doc.id, ...dataMap.payload.doc.data() as Notifications } as Notifications })));
  }

  updateNotification(notification: Notifications) {
    let newNotification = { ...notification };
    newNotification.notificationStatus = 1;
    delete newNotification.id;
    return this.db.collection('notification').doc(notification.id).update(Object.assign({}, newNotification));
  }

  //เรียกคอร์สใหม่ล่าสุดใน การแจ้งเตือน
  getNewCourseForUser() {
    return this.http.get(`${ApiConstants.baseURl}/notification/previouspast`).pipe(
      map(res => ({
        status: res['result'],
        data: res['data']
      }))
    );
  }


}
