import { Injectable } from '@angular/core';
import { HttpClientService } from './http-client.service';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
}
)
export class ProvinceService {

  constructor(
    private http: HttpClientService
  ) { }

  getProvince() {
    return this.http.get(ApiConstants.baseURl + '/provinces')
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
