import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HistoryDharmaService {

  constructor(
    private http: HttpClient
  ) { }

  getHistoryDharmaByMemberId(memberId) {
    const body = {
      memberId: parseInt(memberId)
    };
    return this.http.post(ApiConstants.baseURl + `/historydharma/getByMemberId`, body, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access-token')}`
      }
    }).pipe(
      map((res) => {
        return {
          status: res['result'],
          data: res['data'],
          errorMessage: res['errorMessage']
        };
      })
    );
  }

  getAllHistoryDharma() {
    return this.http.get(ApiConstants.baseURl + `/historydharma`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access-token')}`
      }
    }).pipe(
      map((res) => {
        return {
          status: res['result'],
          data: res['data'],
          errorMessage: res['errorMessage']
        };
      })
    );
  }

  delHistoryDharmaById(body) {
    return this.http.put(ApiConstants.baseURl + `/historydharma`, body, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access-token')}`
      }
    }).pipe(
      map((res) => {
        return {
          status: res['result'],
          data: res['data'],
          errorMessage: res['errorMessage']
        };
      })
    );
  }
}
