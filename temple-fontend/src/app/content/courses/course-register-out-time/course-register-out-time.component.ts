import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/shared/interfaces/course';
import { CourseService } from '../shared/course.service';
import { LazyLoadEvent, ConfirmationService, MessageService } from 'primeng/api';
import { of, forkJoin } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ManagePassCourseService } from 'src/app/shared/service/manage-pass-course.service';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';

import { ApiConstants } from 'src/app/shared/constants/ApiConstants';
import { HttpClient } from '@angular/common/http';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { formatDate } from '@angular/common';
import { Transportation } from 'src/app/shared/interfaces/transportation';
import { TransportService } from 'src/app/shared/service/transport.service';

@Component({
  selector: 'app-course-register-out-time',
  templateUrl: './course-register-out-time.component.html',
  styleUrls: ['./course-register-out-time.component.css']
})
export class CourseRegisterOutTimeComponent implements OnInit {

  public cols: any[];
  public courses: Course[];
  public specialApproveWait: Course[];
  public specialApproveSuccess: Course[];
  public totalRecords: number;
  public userPasscours: number;
  public displayDialog = false;

  public courseId: number;

  public filterTransport: any[];

  id: any;
  name: any;




  constructor(
    private breadCrumbService: BreadcrumbService,
    private courseService: CourseService,
    private managePassCourseService: ManagePassCourseService,
    private http: HttpClient,

    private confirmationService: ConfirmationService,
    private messageService: MessageService,
   
  ) { }

  ngOnInit() {
    this.cols = [
      { field: 'stDate', header: 'วันที่' },
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'locationName', header: 'สถานที่' },
      { field: 'conditionMin', header: 'หมายเหตุ' },
    ];

    this.managePassCourseService.getNumberOfPassCourse(localStorage.getItem('userId'))
      .subscribe(res => this.userPasscours = res.data[0]);
    this.breadCrumbService.setPath([
      { label: 'คอร์สนอกเวลา' },
    ]);
    this.getDataCanRegis();

    this.getDataSpecialApproveWait();
    this.getDataSpecialApproveSuccess();
  }



  private getDataCanRegis() {
    this.courseService.getCoursesOutTimeCanRegis().subscribe(res => this.courses = res['data']);
  }

  showDisplay(id) {
    this.displayDialog = true;
    console.log(this.displayDialog);

    this.courseId = id;

  }



  private getDataSpecialApproveWait() {
    this.courseService.getSpecialApprove('Wait').subscribe(res => this.specialApproveWait = res['data']);
  }

  private getDataSpecialApproveSuccess() {
    this.courseService.getSpecialApprove('Success').subscribe(res => this.specialApproveSuccess = res['data']);
  }

  cancelApprove(courseId) {
    this.confirmationService.confirm({
      message: 'ยืนยันการยกเลิกขออนุมัติคอร์สนอกเวลา',
      header: 'ข้อความจากระบบ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.courseService.cancelApproveOutTime(courseId).subscribe(res => {
          if (res.status === 'Success') {
            // console.log("ยกเลิกขออนุมัตินอกเวลาสำเร็จ");
            this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกขออนุมัตินอกเวลาสำเร็จ' });
            this.setUpMethod();
          } else {
            this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: res['errorMessage'] });
            // console.log(res);
          }
        });
      },
      reject: () => { }
    });
  }

  onDialogClose(event) {
    console.log(event);
    this.setUpMethod();
    this.displayDialog = event;
  }
  setUpMethod() {
    this.getDataCanRegis();
    this.getDataSpecialApproveWait();
    this.getDataSpecialApproveSuccess();
  }
}
