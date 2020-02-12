import { Injectable } from '@angular/core';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/internal/operators/map';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClientService, private dashboard: DashboardService) { }
  getDataChart() {
    return this.http.get(ApiConstants.baseURl + `/dashboard`).pipe(
      map((res: {}) => {
        return res['data'].map(data => {
          const { tranTemple, transport, genderMale, genderFemale, genderNotspec, central, northEast, north, south, east, western,
            passCourse, studyCourse } = data;
          return {
            gender: [genderMale, genderFemale, genderNotspec],
            transport: [tranTemple, transport],
            region: [north, northEast, western, central, east, south],
            courseHistory: [passCourse, studyCourse]
          }
        });
      })
    );
  }
  getNumberOfCounty(id: number) {
    return this.http.get(`${ApiConstants.baseURl}/dashboard/${id}`).pipe(map(res => {
      return res['data'].map(data => {
        const { province, totalMemberHasCourse } = data;
        return { province, totalMemberHasCourse }
      })
    }))
  }
}


