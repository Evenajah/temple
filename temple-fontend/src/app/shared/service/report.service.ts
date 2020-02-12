import { Injectable } from '@angular/core';
import { HttpClientService } from './http-client.service';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor( private http: HttpClientService) { }

  getDataByCourseId(id) {
    if (id !== null) {
      return this.http.get(ApiConstants.baseURl + `/report/${id}`)
      .pipe(
        map((res: any[]) => {
          if (res !== null) {
            return {
              status: res['result'],
              data: res['data']
            };
          }
        })
      );
    } else {
      return this.http.get(ApiConstants.baseURl + `/report`)
      .pipe(
        map((res: any[]) => {
          if (res !== null) {
            return {
                    status: res['result'],
                    data: res['data']
            };
          }
        })
      );
    }
  }

  getCourseName() {
    return this.http.get(ApiConstants.baseURl + `/report/courseNamelist`)
    .pipe(
      map((res: any[]) => {
        if (res !== null) {
          return {
            status: res['result'],
            data: res['data']
          };
        } else {
            return {
              status: null,
              data: null
            };
          }
      })
    );
  }
}
