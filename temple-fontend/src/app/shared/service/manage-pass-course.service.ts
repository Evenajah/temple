import { Injectable } from '@angular/core';
import { HttpClientService } from './http-client.service';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ManagePassCourseService {

  constructor(
    private http: HttpClientService
  ) { }

  getMemberInCourse(courseId: number) {
    return this.http.get(ApiConstants.baseURl + `/graduated/${courseId}`)
      .pipe(
        map((res) => {
          const data = res['data'].map((member) => {
            const checked = member.status === '1' ? true : false;
            return {
              ...member,
              checked: checked
            };
          });
          return {
            status: res['result'],
            data: data
          };
        })
      );
  }

  updateMemberPassCourse(data) {
    return this.http.put(ApiConstants.baseURl + '/graduated', data)
      .pipe(
        map((res) => {
          return {
            status: res['result']
          };
        })
      );
  }

  getAllCourse(offset = 0, limit = 5, query = '') {
    return this.http.get(ApiConstants.baseURl + `/graduated?query=${query}&limit=${limit}&offset=${offset}`)
      .pipe(
        map(res => {
          return {
            status: res['result'],
            data: res['data']
          };
        })
      );
  }

  getTotalRecord() {
    return this.http.get(`${ApiConstants.baseURl}/graduated/count`).pipe(
      map(res => ({
        status: res['result'],
        data: res['data']
      }))
    );

  }
  getNumberOfPassCourse(id) {
    return this.http.get(ApiConstants.baseURl + `/courses/getPassCourse/${id}`)
      .pipe(
        map(res => {
          return {
            status: res['result'],
            data: res['data']
          };
        })
      );
  }
}

