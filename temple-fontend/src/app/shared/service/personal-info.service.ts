import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PersonalInfoService {
  private personalData1 = {
    personalId: '1',
    titleName: '02',
    fname: 'มานี',
    lname: 'แสนดี',
    birthday: '12/21/1980',
    gender: '2',
    address: 'เลขที่ 5 หมู่ 5 ',
    phone: '0901112222',
    email: 'test@test.com',
    phoneEmergency: '0850001111'
  }

  private personalData2 = {
    personalId: '2',
    titleName: '01',
    fname: 'สมชาย',
    lname: 'ชายหาด',
    birthday: '01/04/1980',
    gender: '1',
    address: 'เลขที่ 123 หมู่ 6 ',
    phone: '0801213333',
    email: 'test1@test.com',
    phoneEmergency: '0850001111'
  }

  private personalData = [
    {
      personalId: '1',
      titleName: '02',
      titleNameDisplay: 'นางสาว',
      fname: 'มานี',
      lname: 'แสนดี',
      birthday: '12/21/1980',
      gender: '2',
      address: 'เลขที่ 5 หมู่ 5 ',
      phone: '0901112222',
      email: 'test@test.com',
      phoneEmergency: '0850001111'
    },
    {
      personalId: '2',
      titleName: '01',
      titleNameDisplay: 'นาย',
      fname: 'สมชาย',
      lname: 'ชายหาด',
      birthday: '01/04/1980',
      gender: '1',
      address: 'เลขที่ 123 หมู่ 6 ',
      phone: '0801213333',
      email: 'test1@test.com',
      phoneEmergency: '0850001111'
    },
    {
      personalId: '3',
      titleName: '01',
      titleNameDisplay: 'นาย',
      fname: 'ธนาธร',
      lname: 'เลือกตั้ง',
      birthday: '01/04/1980',
      gender: '1',
      address: 'เลขที่ 123 หมู่ 6 ',
      phone: '0801213333',
      email: 'test1@test.com',
      phoneEmergency: '0850001111'
    }
  ]

  constructor() { }

  getPersonalInfo(id: String) {
    if (id == '1') {
      return this.personalData1;
    }
    else if (id == '2') {
      return this.personalData2;
    }
    return null;
  }
  getAllPersonalInfo() {
    return this.personalData.map((data) => {
      return {
        id: data.personalId,
        titleNameDisplay: data.titleNameDisplay,
        fname: data.fname,
        lname: data.lname
      }
    })
  }
}
