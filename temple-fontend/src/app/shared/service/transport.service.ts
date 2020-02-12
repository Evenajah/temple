import { Injectable } from '@angular/core';
import { HttpClientService } from './http-client.service';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/operators';
import { Transportation } from '../interfaces/transportation';
import { ReturnStatement } from '@angular/compiler';
import { TransportationTemple } from '../interfaces/transportation-temple';

@Injectable({
  providedIn: 'root'
})
export class TransportService {

  constructor(
    private http: HttpClientService
  ) { }

  getTranSport() {
    return this.http.get(ApiConstants.baseURl + '/transportations')
      .pipe(
        map((res: any[]) => {
          return res['data'].map(data => {
            return {
              id: data['id'],
              name: data['name'],
              status: data['status']
            };
          });
        })
      );
  }

  getTranSportTemple(id) {
    if (id !== null) {
      return this.http.get(ApiConstants.baseURl + `/transportations/temple/${id}`)
        .pipe(
          map((res: any[]) => {
            return {
              status: res['result'],
              data: res['data']
            };
          })
        );
    } else if (id === null) {
      return this.http.get(ApiConstants.baseURl + `/transportations/temple/`)
        .pipe(
          map((res: any[]) => {
            return {
              status: res['result'],
              data: res['data']
            };
          })
        );
    }
  }

  getTranSportTempleForCreateCourse(id) {
    return this.http.get(ApiConstants.baseURl + `/transportations/templeForCreateCourse/`)
      .pipe(
        map((res: any[]) => {
          return {
            status: res['result'],
            data: res['data']
          };
        })
      );
  }

  getTranSportToEdit() {
    return this.http.get(ApiConstants.baseURl + '/transportations')
      .pipe(
        map((res) => {
          return {
            status: res['result'],
            data: res['data']
          };
        })
      );
  }

  getTranSportTempleToEdit(id: number) {
    return this.http.get(`${ApiConstants.baseURl}/transportations/templeRegister/${id}`)
      .pipe(
        map((res) => {
          return {
            status: res['result'],
            data: res['data']
          };
        })
      );
  }

  createTransportation(transportation: Transportation) {
    return this.http.post(ApiConstants.baseURl + '/transportations', transportation)
      .pipe(
        map(res => {
          return {
            status: res['result'],
            data: res['data'][0]
          };
        })
      );
  }

  createTransportationTemple(transportTemple: TransportationTemple) {
    return this.http.post(ApiConstants.baseURl + '/transportations/temple', transportTemple)
      .pipe(
        map(res => {
          return {
            status: res['result'],
            data: res['data']
          };
        })
      );
  }

  updateTransportation(transportation: Transportation) {
    return this.http.put(`${ApiConstants.baseURl}/transportations/${transportation.id}`, transportation)
      .pipe(
        map(res => {
          return {
            status: res['result'],
            data: res['data'][0]
          };
        })
      );
  }

  updateTransportationTemple(transportTemple: TransportationTemple) {
    return this.http.put(`${ApiConstants.baseURl}/transportations/temple/${transportTemple.id}`, transportTemple)
      .pipe(
        map(res => {
          return {
            status: res['result'],
            data: res['data'][0]
          };
        })
      );
  }

  deleteTransportation(id: number) {
    return this.http.put(`${ApiConstants.baseURl}/transportations/delete/${id}`, { id: id })
      .pipe(
        map(res => {
          return {
            status: res['result']
          };
        })
      );
  }

  deleteTransportationTemple(id: number) {
    return this.http.delete(`${ApiConstants.baseURl}/transportations/temple/delete/${id}`)
      .pipe(
        map(res => {
          return {
            status: res['result']
          };
        })
      );
  }

}
