import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ApiConstants } from '../constants/ApiConstants';
import { map } from 'rxjs/operators';
import { Member } from '../interfaces/member';

@Injectable({
  providedIn: 'root'
})
export class ForgetPassService {


  public memberData: Member;
  constructor(
    private http: HttpClient
  ) { }

  getUserForgetInfo(idCard, username, phoneNumber) {
    const body = {
      idCard, username, phoneNumber
    };
    return this.http.post(ApiConstants.baseURl + '/forgetpass/', body);
    // .pipe(
    //   map((res) => {
    //     return {
    //       status: res['result'],
    //       data: res['data'],
    //       // stringData: res['stringData'],
    //       errorMessage: res['errorMessage'],
    //       code: res['code']
    //     };
    //   })
    // );
  }

  changePassword(password) {
    const body = {
      ...this.memberData[0],
      'password': password
    };
    return this.http.put(ApiConstants.baseURl + '/forgetpass/', body);
  }
}