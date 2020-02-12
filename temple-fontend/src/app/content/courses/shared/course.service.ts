import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { ApiConstants } from 'src/app/shared/constants/ApiConstants';
import { SpecialApprove } from '../../../shared/interfaces/special-approve';
import { HttpClientService } from 'src/app/shared/service/http-client.service';
import { HttpParams, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClientService,
    private https:HttpClient
    ) {}
  

  /*-------------------------------------*/

  downloadFile(data){
    //เรียก spring-boot service
    const REQUEST_PARAMS = new HttpParams().set('fileName',data.fileName);
    const REQUEST_URI= ApiConstants.baseURl+'/excel/download';
    return this.https.get(REQUEST_URI, {
      params: REQUEST_PARAMS,
      responseType: 'arraybuffer',
    })
  }

  /*-------------------------------------*/

  getTotalRecord(status: string) {
    return this.http.get(`${ApiConstants.baseURl}/courses/count?status=${status}`).pipe(
      map(res => ({
        status: res['result'],
        data: res['data']
      }))
    );
  }

  getCourseByid(id) {
    return this.http.get(ApiConstants.baseURl + `/courses/${id}`).pipe(
      map(res => ({

        status: res['result'],
        data: res['data'][0]
      })
      ));
  }

  getCourses() {
    return this.http.get(`${ApiConstants.baseURl}/courses`).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }


  getCoursesUser(status: string) {
    return this.http.get(`${ApiConstants.baseURl}/courses/user?status=${status}`).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }


  assignCourse(dataCourse) {
    return this.http.post(ApiConstants.baseURl + `/courses/register`, dataCourse );
  }

  createCourse(data) {
    return this.http.post(ApiConstants.baseURl + `/courses`, data);
  }

  editCourse(id, course) {
    return this.http.put(ApiConstants.baseURl + `/courses/${id}`, course);
  }

  // deleteCourse(id) {
  //   return this.http.patch(ApiConstants.baseURl + `/courses`, {courseId: id});
  // }

  approvalCourse(data) {
    return this.http.post(ApiConstants.baseURl + `/approve`, data);
  }

  cancelApprovalCourse(id) {
    return this.http.delete(ApiConstants.baseURl + `/approve/${id}`);
  }

  getTeachers() {
    return this.http.get(ApiConstants.baseURl + `/members/monk`).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }

  deleteCourse(courseId) {
    return this.http.put(ApiConstants.baseURl + `/courses/deleteCourse/${courseId}`, null)
      .pipe(
        map(res => {
          return {
            status: res['result']
          }
        }
        )
      )
  }

  getUserByCourseId(courseId) {
    return this.http.get(ApiConstants.baseURl + `/courses/allmembers/${courseId}`).pipe(
      map(res => ({
        status: res['result'],
        data: res['data']
      })
      ));
  }

  getCoursesOutTimeCanRegis() {
    return this.http.get(`${ApiConstants.baseURl}/courses/outTime`).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }
  registerCourseOutTime(course) {
    return this.http.post(`${ApiConstants.baseURl}/courses/outTime`, course).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }

  getSpecialApprove(approveType) {
    return this.http.get(`${ApiConstants.baseURl}/approve/outTime?approveType=${approveType}`).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }

  cancelApproveOutTime(courseId) {
    return this.http.put(`${ApiConstants.baseURl}/approve/outTime?courseId=${courseId}`, null).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }


}
