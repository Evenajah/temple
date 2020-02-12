import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { ApiConstants } from '../../shared/constants/ApiConstants';
import { HttpClientService } from '../../shared/service/http-client.service';

@Injectable({
  providedIn: 'root'
})
export class ApprovalService {


  constructor(
    private http: HttpClientService,
  ) {
  }

  getMemberForApprove(coursesId) {
    return this.http.get(`${ApiConstants.baseURl}/approve/${coursesId}`).pipe(
      map((res) => {
        if (res !== null) {
          const data = res['data'].map((member) => {
            const checked = member.status === '1';
            return {
              ...member,
              checked: checked
            };
          });
          return {
            status: res['result'],
            data: data,
          };
        } else {return null;}
      })
    );
  }

  getMemberForApproveOutTime(coursesId) {
    return this.http.get(`${ApiConstants.baseURl}/approve/outTime/${coursesId}`).pipe(
      map((res) => {
        const data = res['data'].map((member) => {
          const checked = member.status === '1';
          return {
            ...member,
            checked: checked
          };
        });
        return {
          status: res['result'],
          data: data,
        };
      })
    );
  }

  getTotalRecord() {
    return this.http.get(`${ApiConstants.baseURl}/courses/approve/count`).pipe(
      map(res => ({
        status: res['result'],
        data: [...res['data']]
      }))
    );
  }
  getTotalRecordOutTime() {
    return this.http.get(`${ApiConstants.baseURl}/courses/approve/outTime/count`).pipe(
      map(res => ({
        status: res['result'],
        data: [...res['data']]
      }))
    );
  }

  getCoursesApproval(first: number, rows: number, query: string) {
    return this.http.get(`${ApiConstants.baseURl}/courses/approve?query=${query}&offset=${first}&limit=${rows}`).pipe(
      map(res => ({
        status: res['result'],
        data: [...res['data']]
      }))
    );
  }

  getCoursesApprovalOutTime(first: number, rows: number, query: string) {
    return this.http.get(`${ApiConstants.baseURl}/courses/approve/outTime?query=${query}&offset=${first}&limit=${rows}`).pipe(
      map(res => ({
        status: res['result'],
        data: [...res['data']]
      }))
    );
  }

  approveStudents(data) {
    const req = {
      spaId: data.member ? data.member : data.spaId,
      courseId: data.courseId,
      status: data.status
    };
    return this.http.put(`${ApiConstants.baseURl}/approve`, req).pipe(
      map((res) => {
        return {
          status: res['result'],
          data: res['data'],
        };
      })
    );
  }


}
