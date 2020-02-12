import { Component, OnInit, ViewChild } from '@angular/core';

import { CourseService } from '../shared/course.service';
import {
  ConfirmationService,
  LazyLoadEvent,
  MenuItem,
  SelectItem,
  SortEvent,
  MessageService
} from 'primeng/api';
import { Course } from '../../../shared/interfaces/course';
import { BreadcrumbService } from '../../../shared/service/breadcrumb.service';
import { SpecialApprove } from '../../../shared/interfaces/special-approve';
import { Router } from '@angular/router';
import { of, UnsubscriptionError, from, combineLatest } from 'rxjs';
import { switchMap, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TransportService } from 'src/app/shared/service/transport.service';
import { element } from '@angular/core/src/render3';
import { CourseCreateComponent } from '../course-create/course-create.component';
import { TransportationTemple } from 'src/app/shared/interfaces/transportation-temple';
import { Spinner } from 'primeng/primeng';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.css']
})
export class CoursesListComponent implements OnInit {
  // public msgs: any[] = [];
  public courses: Course[] = [];
  public studyingcourses: Course[];
  public values: Course[] = [];
  public cols: any[];
  public cols2: any[];
  public menu: MenuItem[];
  public displayApproveDialog = false;
  public displayRegisterDialog = false;
  public specialApprove: SpecialApprove;
  public selectedCourse: Course;
  public totalRecords: number;
  public totalStudyRecords: number;
  public courseId: number;
  public total: number;
  public couresTran: any[];
  public transports: any[];
  public selectedSpec: SpecialApprove;
  public lengthError: string;
  public optionTime: any;
  public transport: TransportationTemple[];
  public transportId: any;

  assignFormCourse = new FormGroup({
    transportation: new FormControl('', Validators.required),
    experience: new FormControl('', [
      Validators.required,
      Validators.maxLength(100)
    ]),
    expected: new FormControl('', [
      Validators.required,
      Validators.maxLength(100)
    ])
  });

  approveFormCourse = new FormGroup({
    transportation: new FormControl('', Validators.required),
    experience: new FormControl('', [
      Validators.required,
      Validators.maxLength(100)
    ]),
    expected: new FormControl('', [
      Validators.required,
      Validators.maxLength(100)
    ]),
    detail: new FormControl('', [
      Validators.required,
      Validators.maxLength(255)
    ])
  });

  public formAssignError = {
    transportation: '',
    experience: '',
    expected: ''
  };

  public formLengthError = {
    transportation: '',
    experience: '',
    expected: '',
    detail: ''
  };

  public formApproveError = {
    transportation: '',
    experience: '',
    expected: '',
    detail: ''
  };

  public validationAssignMessage = {
    transportation: {
      required: 'การเดินทางมาอบรม*'
    },
    experience: {
      required: 'ประสบการณ์ปฎิบัติธรรมที่ผ่านมา*'
    },
    expected: {
      required: 'ความคาดหวังในครั้งนี้*'
    }
  };

  public validationApproveMessage = {
    transportation: {
      required: 'การเดินทางมาอบรม*'
    },
    experience: {
      required: 'ประสบการณ์ปฎิบัติธรรมที่ผ่านมา*'
    },
    expected: {
      required: 'ความคาดหวังในครั้งนี้*'
    },
    detail: {
      required: 'รายละเอียดคำขออนุมัติ*'
    }
  };

  constructor(
    private courseService: CourseService,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private router: Router,
    private transportation: TransportService,
    private messageService: MessageService,
    public spinner: NgxSpinnerService,
  ) { }

  ngOnInit() {
    this.values = [];
    this.getData();
    this.getStudyingData();
    this.getTotalRecord();
    this.getStudyTotalRecord();
    // this.initSpecialApprove();
    this.getGraduatedCourse();
    this.cols = [
      { field: 'stDate', header: 'วันที่' },
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'locationName', header: 'สถานที่' },
      { field: 'conditionMin', header: 'หมายเหตุ' },
      { field: 'status', header: 'สถานะ' }
    ];
    this.cols2 = [
      { field: 'stDate', header: 'วันที่' },
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'status', header: 'สถานะ' }
    ];

    this.transportation.getTranSport().subscribe(
      res => {
        this.transports = res;
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );

    this.breadCrumbService.setPath([
      { label: 'ลงทะเบียนเรียน', routerLink: '/courses' }
    ]);
  }

  public assignCourse() {
    const transItem = this.assignFormCourse.get('transportation').value;
    const dataCourse = {
      courseId: this.courseId,
      transportationId: transItem.id,
      experience: this.assignFormCourse.get('experience').value,
      expected: this.assignFormCourse.get('expected').value
    };
    this.confirmationService.confirm({
      message: 'ยืนยันการลงทะเบียน',
      header: 'ข้อความจากระบบ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.assignCourse(dataCourse).toPromise()
          .then(res => {
            if (res['result'] === 'Success') {
              const index = this.courses.findIndex(
                course => course.id === this.courseId
              );
              const upd = this.courses[index];
              upd.status = 'กำลังศึกษา';
              upd.canRegister = 0;
              upd.mhcStatus = '2';
              const name = upd.name;
              this.updateTable([
                ...this.courses.slice(0, index),
                upd,
                ...this.courses.slice(index + 1)
              ]);
              this.onCancle('as');
              this.messageService.add({
                severity: 'success',
                summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการลงทะเบียนคอร์ส ' + name + ' สำเร็จ'
              });
              this.getData();
              this.getStudyingData();
              this.getTotalRecord();
              this.getStudyTotalRecord();
            } else if (res['result'] === 'Fail') {
              this.onCancle('as');
              this.messageService.add({
                severity: 'error',
                summary: 'ข้อความจากระบบ',
                detail: res['errorMessage']
              });
            }
          }).catch(err => {
            this.onCancle('as');
            this.messageService.add({
              severity: 'error',
              summary: 'ข้อความจากระบบ',
              detail: err['error']['errorMessage']
            });
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        this.onCancle('as');
        this.messageService.add({
          severity: 'info',
          summary: 'ข้อความจากระบบ',
          detail: 'ยกเลิกกการลงทะเบียน'
        });
        this.getData();
        this.getStudyingData();
        this.getTotalRecord();
        this.getStudyTotalRecord();
      }
    });
  }

  public approvalCourse() {
    const transItem = this.approveFormCourse.get('transportation').value;
    const dataCourse = {
      courseId: this.courseId,
      transportationId: transItem.id,
      experience: this.approveFormCourse.get('experience').value,
      expected: this.approveFormCourse.get('expected').value,
      detail: this.approveFormCourse.get('detail').value
    };
    this.confirmationService.confirm({
      message: 'ยืนยันการขออนุมัติพิเศษ',
      header: 'ข้อความจากระบบ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.approvalCourse(dataCourse).toPromise()
          .then(res => {
            if (res['result'] === 'Success') {
              const index = this.courses.findIndex(
                course => course.id === this.courseId
              );
              const upd = this.courses[index];
              upd.status = 'รอการอนุมัติ';
              upd.saStatus = '2';
              upd.canRegister = 0;
              this.updateTable([
                ...this.courses.slice(0, index),
                upd,
                ...this.courses.slice(index + 1)
              ]);
              this.onCancle('ap');
              this.messageService.add({
                severity: 'success',
                summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการขออนุมัติพิเศษสำเร็จ'
              });
              this.getData();
              this.getStudyingData();
              this.getTotalRecord();
              this.getStudyTotalRecord();
            } else if (res['result'] === 'Fail') {
              this.onCancle('ap');
              this.messageService.add({
                severity: 'error',
                summary: 'ข้อความจากระบบ',
                detail: res['errorMessage']
              });
              this.getData();
              this.getStudyingData();
              this.getTotalRecord();
              this.getStudyTotalRecord();
            }
          }).catch(err => {
            this.onCancle('ap');
            this.messageService.add({
              severity: 'error',
              summary: 'ข้อความจากระบบ',
              detail: err['error']['errorMessage']
            });
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        this.onCancle('ap');
        this.messageService.add({
          severity: 'info',
          summary: 'ข้อความจากระบบ',
          detail: 'ยกเลิกการขออนุมัติพิเศษ'
        });
      }
    });
  }

  private getData() {
    this.spinner.show();
    this.values = [];
    this.courseService.getCoursesUser('0').toPromise()
      .then(res => {
        if (res['status'] === 'Success') {
          res['data'].forEach(element => {
            if (
              element.status === 'ยังไม่ได้ลงทะเบียน' ||
              element.status === 'รอการอนุมัติ'
            ) {
              this.values.push(element);
            }
          });
        }
        // this.values.sort((a,b) => a.name.localeCompare(b.name));
        this.courses = this.values;
      }).catch(err => {
        console.log(err['error']['errorMessage']);
      }).finally(() => this.spinner.hide());
  }

  private getStudyingData() {
    this.values = [];
    this.courseService.getCoursesUser('2').subscribe(res => {
      if (res['status'] === 'Success') {
        this.studyingcourses = res['data'];
      }
    });
  }

  private getTotalRecord() {
    // status = 0 / ยังไม่ได้ลงทะเบียน
    this.courseService.getTotalRecord('0').subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalRecords = res['data'][0]['totalRecord'];
      }
    });
  }

  private getStudyTotalRecord() {
    // status = 2 / กำลังศึกษา
    this.courseService.getTotalRecord('2').subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalStudyRecords = res['data'][0]['totalRecord'];
      }
    });
  }

  public cancelApprovalCourse(id) {
    this.closeMessage();
    this.confirmationService.confirm({
      message: 'ยืนยันการยกเลิกการขออนุมัติพิเศษ',
      header: 'ข้อความจากระบบ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.cancelApprovalCourse(id).toPromise()
          .then(res => {
            if (res['result'] === 'Success') {
              const index = this.courses.findIndex(course => course.id === id);
              const upd = this.courses[index];
              upd.status = 'ยังไม่ได้ลงทะเบียน';
              upd.canRegister = 1;
              upd.saStatus = null;
              this.updateTable([
                ...this.courses.slice(0, index),
                upd,
                ...this.courses.slice(index + 1)
              ]);

              this.messageService.add({
                severity: 'success',
                summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการยกเลิกการขออนุมัติพิเศษสำเร็จ'
              });
              this.getData();
              this.getStudyingData();
              this.getTotalRecord();
              this.getStudyTotalRecord();
            } else if (res['result'] === 'Fail') {
              this.messageService.add({
                severity: 'error',
                summary: 'ข้อความจากระบบ',
                detail: res['error']['errorMessage']
              });
            }
          }).catch(err => {
            this.messageService.add({
              severity: 'error',
              summary: 'ข้อความจากระบบ',
              detail: err['error']['errorMessage']
            });
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        this.messageService.add({
          severity: 'info',
          summary: 'ข้อความจากระบบ',
          detail: 'ปฏิเสธการยกเลิกการขออนุมัติพิเศษ'
        });
      }
    });
  }

  public saCourse(courseId: number) {
    this.closeMessage();
    this.courseId = courseId;
    this.courseService.getCourseByid(courseId).subscribe(res => {
      this.transportId = res['data']['transportTempleId'];
      if (this.transportId !== null) {
        // combineLatest for process 2 service before subscribe
        this.optionTime = { hour: '2-digit', minute: '2-digit' };
        combineLatest(
          this.transportation.getTranSportToEdit(),
          this.transportation.getTranSportTempleToEdit(this.courseId)
        ).subscribe(([tranSport, tranSportTemple]) => {
          this.transports = [
            ...tranSport.data,
            ...tranSportTemple.data.map(data => {
              return {
                id: data.id,
                name:
                  data.name +
                  ' เวลารับ: ' +
                  new Date(data.timePickUp).toLocaleTimeString(
                    'th-TH',
                    this.optionTime
                  ) +
                  ' เวลา: ' +
                  new Date(data.timeSend).toLocaleTimeString(
                    'th-TH',
                    this.optionTime
                  )
              };
            })
          ];
        });
      } else {
        this.transportation.getTranSportToEdit().subscribe(
          data => {
            this.transports = [...data.data];
          }
        );
      }
      // this.transportId = this.transportId.map(
      //   data => {
      //     return data.transportTempleId;
      //   }
      // );
    });
    this.displayApproveDialog = true;
  }

  public rgCourse(courseId: number) {
    this.closeMessage();
    this.courseId = courseId;
    this.courseService.getCourseByid(courseId).subscribe(res => {
      this.transportId = res['data']['transportTempleId'];
      if (this.transportId !== null) {
        // combineLatest for process 2 service before subscribe
        this.optionTime = { hour: '2-digit', minute: '2-digit' };
        combineLatest(
          this.transportation.getTranSportToEdit(),
          this.transportation.getTranSportTempleToEdit(this.courseId)
        ).subscribe(([tranSport, tranSportTemple]) => {
          this.transports = [
            ...tranSport.data,
            ...tranSportTemple.data.map(data => {
              return {
                id: data.id,
                name:
                  data.name +
                  ' เวลารับ: ' +
                  new Date(data.timePickUp).toLocaleTimeString(
                    'th-TH',
                    this.optionTime
                  ) +
                  ' เวลาส่ง: ' +
                  new Date(data.timeSend).toLocaleTimeString(
                    'th-TH',
                    this.optionTime
                  )
              };
            })
          ];
        });
      } else {
        this.transportation.getTranSportToEdit().subscribe(data => {
          this.transports = data.data;
        });
      }
      // this.transportId = this.transportId.map(
      //   data => {
      //     return data.transportTempleId;
      //   }
      // );
    });
    this.displayRegisterDialog = true;
  }

  private initSpecialApprove() {
    this.displayApproveDialog = false;
    this.specialApprove = {
      specialApproveId: null,
      courseId: null,
      memberId: null,
      detail: '',
      status: '',
      createDate: null,
      lastUpdate: null,
      courseName: null,
      expected: '',
      experience: '',
      transportationId: null
    };
  }

  private updateTable(data: any[]) {
    this.courses = data;
  }

  public onRowSelect(e) {
    const course: Course = e.data;
    this.router.navigate(['/courses', course.id]);
  }

  public onSelectCourse(id) {
    this.router.navigate(['/courses', id]);
  }

  // count graduated course
  private getGraduatedCourse() {
    this.courseService.getTotalRecord('1').subscribe(res => {
      if (res['status'] === 'Success') {
        this.total = res['data'][0]['totalRecord'];
      }
    });
  }
  /**
   * ตรวจสอบค่าที่รับเข้ามาใหม่ในกรณีกรอกข้อมูลไม่ครบถ้วน
   */
  subscribeInputMessageWaring(e) {
    if (e === 'as') {
      this.assignFormCourse.valueChanges
        .pipe(debounceTime(500), distinctUntilChanged())
        .subscribe(() => this.waringAssignMessage());
      this.waringAssignMessage();
    } else {
      this.approveFormCourse.valueChanges
        .pipe(debounceTime(500), distinctUntilChanged())
        .subscribe(() => this.waringApproveMessage());
      this.waringApproveMessage();
    }
  }

  waringAssignMessage() {
    if (!this.formAssignError) {
      return;
    }
    for (const field of Object.keys(this.formAssignError)) {
      this.formAssignError[field] = '';
      const control = this.assignFormCourse.get(field);
      if (control && !control.valid && this.validationAssignMessage[field]) {
        this.formAssignError[field] = this.validationAssignMessage[
          field
        ].required;
        if (field !== 'transportation' && control.hasError('maxlength')) {
          this.formLengthError[field] = '**ข้อความต้องน้อยกว่า 100 ตัวอักษร';
        } else {
          this.formLengthError[field] = '';
        }
      }
    }
  }

  // ตรวจสอบ Valid maxLength required
  waringApproveMessage() {
    if (!this.approveFormCourse) {
      return;
    }
    for (const field of Object.keys(this.formApproveError)) {
      this.formApproveError[field] = '';
      const control = this.approveFormCourse.get(field);
      if (control && !control.valid && this.validationApproveMessage[field]) {
        this.formApproveError[field] = this.validationApproveMessage[
          field
        ].required;
        if (field !== 'transportation' && control.hasError('maxlength')) {
          this.formLengthError[field] = '**ข้อความต้องน้อยกว่า 100 ตัวอักษร';
        } else {
          this.formLengthError[field] = '';
        }
      }
    }
  }

  onSubmitAssign(e) {
    this.setValidate(e);
    if (!this.assignFormCourse.valid) {
      this.subscribeInputMessageWaring(e);
    } else {
      this.assignCourse();
    }
  }

  onSubmitApprove(e) {
    this.setValidate(e);
    if (!this.approveFormCourse.valid) {
      this.subscribeInputMessageWaring(e);
    } else {
      this.approvalCourse();
    }
  }

  onCancle(e) {
    if (e === 'as') {
      this.displayRegisterDialog = false;
      this.assignFormCourse.reset();
      Object.values(this.assignFormCourse.controls).forEach(df => {
        df.markAsPristine();
        df.setValidators(null);
        df.updateValueAndValidity();
      });
    } else {
      this.displayApproveDialog = false;
      this.approveFormCourse.reset();
      Object.values(this.approveFormCourse.controls).forEach(df => {
        df.markAsPristine();
        df.setValidators(null);
        df.updateValueAndValidity();
      });
    }
  }

  setValidate(e) {
    if (e === 'as') {
      Object.keys(this.assignFormCourse.controls).forEach(key => {
        const control = this.assignFormCourse.get(key);
        control.clearValidators();
        if (key === 'transportation') {
          control.setValidators(Validators.required);
        } else {
          control.setValidators([
            Validators.required,
            Validators.maxLength(100)
          ]);
        }
        control.updateValueAndValidity();
      });
    } else {
      Object.keys(this.approveFormCourse.controls).forEach(key => {
        const control = this.approveFormCourse.get(key);
        control.clearValidators();
        if (key === 'transportation') {
          control.setValidators(Validators.required);
        } else if (key === 'detail') {
          control.setValidators([
            Validators.required,
            Validators.maxLength(255)
          ]);
        } else {
          control.setValidators([
            Validators.required,
            Validators.maxLength(100)
          ]);
        }
        control.updateValueAndValidity();
      });
    }
  }

  public closeMessage() {
    // this.messageService.clear();
  }
}
