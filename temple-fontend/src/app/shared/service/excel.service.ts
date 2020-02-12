import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpClientService } from './http-client.service';
import { ApiConstants } from '../constants/ApiConstants';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor(
    private http: HttpClient,
    private httpService: HttpClientService
  ) { }

  createExcel(courseId: string) {
    return this.http.get(ApiConstants.baseURl + `/excel?courseId=${courseId}`);
  }
}
