import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiConstants} from '../constants/ApiConstants';
import {map} from 'rxjs/operators';
import {HttpClientService} from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class BaggageService {

  constructor(
    private http: HttpClient,
    private httpService: HttpClientService
  ) {
  }

  getItems() {
    return this.http.get(ApiConstants.baseURl + '/baggage', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access-token')}`
      }
    }).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }

  getItem() {
    return this.http.get(ApiConstants.baseURl + '/lockers', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access-token')}`
      }
    }).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }
  getItemAll() {
    return this.http.get(ApiConstants.baseURl + '/lockers/all', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('access-token')}`
      }
    }).pipe(
      map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      })
    );
  }

  update(data) {

    return this.http.put(ApiConstants.baseURl + `/lockers/${data['lockerId']}`, data, { headers: { Authorization: `Bearer ${localStorage.getItem('access-token')}` } })
      .pipe(map((res) => {
        return {
          status: res['result'],
          data: res['data']
        };
      }));

  }

  delete(id) {
    return this.http.delete(ApiConstants.baseURl + `/lockers/${id}`, { headers: { Authorization: `Bearer ${localStorage.getItem('access-token')}` } })
    .pipe(map(res => {
        return {
          status: res['result'],
          code: res['code']
        };
      }));
  }

  save(data) {
    return this.httpService.post(ApiConstants.baseURl + `/lockers`, data )
      .pipe(map(res => {
        return {
          status: res['result'],
          data: res['data']
        };
      }));
  }

  saveStorage(data) {
    return this.httpService.post(ApiConstants.baseURl + `/baggage`, data
    )
    .pipe(map(res => {
      return {
        status: res['result'],
        data: res['data'][0]
      };
    }));
  }
  updateStorage(id, data) {
    return this.httpService.put(ApiConstants.baseURl + `/baggage/${id}`, data).pipe(map(res => {
      return {
        status: res['result'],
        data: res['data'][0]
      };
    }));
  }
}
