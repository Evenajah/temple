import { Component, OnInit, OnDestroy, Input, AfterViewInit } from '@angular/core';

import { CourseService } from '../shared/course.service';
import { ActivatedRoute, Router, NavigationEnd, RoutesRecognized, RouterEvent, NavigationStart } from '@angular/router';
import { MenuItem, ConfirmationService, SelectItem, MessageService } from 'primeng/api';
import { BreadcrumbService } from '../../../shared/service/breadcrumb.service';
import { Course } from 'src/app/shared/interfaces/course';
import { SpecialApprove } from '../../../shared/interfaces/special-approve';
import { switchMap, filter, pairwise, debounceTime, distinctUntilChanged, finalize } from 'rxjs/operators';
import { AuthService } from 'src/app/shared/service/auth.service';
import { ManageUserService } from 'src/app/shared/service/manage-user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { PrePathService } from 'src/app/shared/service/pre-path.service';
import { TransportService } from 'src/app/shared/service/transport.service';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { saveAs } from 'file-saver';
import { combineLatest, pipe } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';

const MIME_TYPE = {
  xls: 'application/vnd.ms-excel'
};

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit, OnDestroy {
  public course: Course;
  public memberIdList = [];
  public memberList = [];
  // public msgs: any[] = [];
  public menu: MenuItem[];
  public displayApproveDialog: boolean;
  public specialApprove: SpecialApprove;
  public role: string;
  public courseId: number;
  public id: string;
  public displayRegisterDialog: boolean;
  public total: number;
  public status: string;
  public memberId: number;
  public previousUrl: string;
  public url: string;
  public title: string;
  public totalMember: number;


  public breadCrumb: void;
  public detail: string;
  public currentUrl: string;
  public urlback: any;
  public transports: any[];
  public optionTime: any;
  public transportId: any;

  public cols = [
    { field: 'name', header: 'ชื่อ-นามสกุล' },
  ];

  assignFormCourse = new FormGroup({
    transportation: new FormControl('', Validators.required),
    experience: new FormControl('', [Validators.required, Validators.maxLength(100)]),
    expected: new FormControl('', [Validators.required, Validators.maxLength(100)]),
  });

  approveFormCourse = new FormGroup({
    transportation: new FormControl('', Validators.required),
    experience: new FormControl('', [Validators.required, Validators.maxLength(100)]),
    expected: new FormControl('', [Validators.required, Validators.maxLength(100)]),
    detail: new FormControl('', [Validators.required, Validators.maxLength(255)]),
  });

  public formLengthError = {
    transportation: '',
    experience: '',
    expected: '',
    detail: ''
  };

  public formAssignError = {
    transportation: '',
    experience: '',
    expected: '',
  };

  public formApproveError = {
    transportation: '',
    experience: '',
    expected: '',
    detail: '',
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
    },
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
    },
  };

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private router: Router,
    private authService: AuthService,
    private manageUserService: ManageUserService,
    private location: Location,
    private pathService: PrePathService,
    private transportation: TransportService,
    private excelService: ExcelService,
    private messageService: MessageService,
    public spinner: NgxSpinnerService,
  ) { }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getData();
    this.authService.getRole().subscribe(res => this.role = res);
    this.getMemberByCourseId();
    this.getCountGraduatedCourse();
    this.setUrl();
  }

  ngOnDestroy() {
  }

  /*------------------------------------------*/

  downloadFile(fileName) {
    this.spinner.show();
    const EXT = fileName.substr(fileName.lastIndexOf('.') + 1);
    this.courseService.downloadFile({ 'fileName': fileName })
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(data => {
        // บันทึกในเครื่อง client
        saveAs(new Blob([data], { type: MIME_TYPE[EXT] }), fileName);
      });
  }

  /*------------------------------------------*/

  private setUrl() {
    this.previousUrl = this.pathService.setPreviousUrl();
    // get path ก่อนหน้า
    // ตรวจสอบเพิ่มในกรณีไม่มีการรรีเฟรชหน้าเดิม ทำการเก็บข้อมูล pre url
    if (this.previousUrl != null) {
      localStorage.removeItem('preurl');
      localStorage.setItem('preurl', JSON.stringify(this.previousUrl));
    }
    this.setBreadCrumb();
  }

  private setBreadCrumb() {
    if (JSON.parse(localStorage.getItem('preurl')) != null) {
      this.url = JSON.parse(localStorage.getItem('preurl'));
    }
    if (this.role === 'monk') {
      this.title = 'ตารางสอน';
      this.detail = 'รายชื่อผู้เรียน';
    } else if (this.role === 'user') {
      this.title = 'ตารางเรียน';
      this.detail = 'รายละเอียดคอร์ส';
    }

    if (this.url === '/schedule') {
      this.breadCrumbService.setPath([
        { label: `${this.title}`, routerLink: '/schedule' },
        { label: `${this.detail}` },
      ]);
    } else if (this.url === '/manageCourseForMonk') {
      this.breadCrumbService.setPath([
        { label: 'จัดการคอร์ส', routerLink: '/manageCourseForMonk' },
        { label: 'รายชื่อผู้เรียน' },
      ]);
    } else if (this.url === '/courses') {
      this.breadCrumbService.setPath([
        { label: 'ลงทะเบียนเรียน', routerLink: '/courses' },
        { label: 'รายละเอียดคอร์ส' },
      ]);
    } else if (this.url === '/manageCourse') {
      this.breadCrumbService.setPath([
        { label: 'จัดการคอร์ส', routerLink: '/manageCourse' },
        { label: 'รายชื่อผู้เรียน' },
      ]);
    } else if (this.url === ('/profile/' + localStorage.getItem('userId'))) {
      this.breadCrumbService.setPath([
        { label: 'ข้อมูลส่วนตัว', routerLink: ['/profile', localStorage.getItem('userId')] },
        { label: 'รายละเอียดคอร์ส' },
      ]);
    }
  }

  showButtonBack(...role) {
    return role.includes(this.role);
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
      header: 'ลงทะเบียน',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.assignCourse(dataCourse).toPromise()
          .then((res) => {
            if (res['result'] === 'Success') {
              this.course.status = 'กำลังศึกษา';
              this.course.canRegister = 0;
              this.course.mhcStatus = '2';
              this.onCancle('as');
              this.spinner.hide();
              this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการลงทะเบียนสำเร็จ' });
            } else if (res['result'] === 'Fail') {
              this.onCancle('as');
              this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: res['errorMessage'] });
            }
          }).catch(err => {
            this.onCancle('as');
            this.spinner.hide();
            this.messageService.add({
              severity: 'error', summary: 'ข้อความจากระบบ',
              detail: 'ดำเนินการลงทะเบียนไม่สำเร็จเนื่องจาก' + err['errorMessage']
            });
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        this.onCancle('as');
        // this.messageService.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการลงทะเบียน' });
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
      header: 'ขออนุมัติพิเศษ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.approvalCourse(dataCourse).toPromise()
          .then((res) => {
            if (res['result'] === 'Success') {
              this.course.status = 'รออนุมัติ';
              this.course.canRegister = 0;
              this.course.saStatus = '2';
              this.onCancle('ap');
              this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการขออนุมัติพิเศษสำเร็จ' });
            } else if (res['result'] === 'Fail') {
              this.onCancle('ap');
              this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: res['errorMessage'] });
            }
          }).catch(err => {
            this.messageService.add({
              severity: 'error', summary: 'ข้อความจากระบบ',
              detail: 'ดำเนินการขออนุมัติพิเศษไม่สำเร็จเนื่องจาก ' + err['errorMessage']
            });
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        this.onCancle('ap');
        // this.messageService.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการขออนุมัติพิเศษ' });
      }
    });
  }

  public cancelApprovalCourse(id) {
    this.closeMessage();
    this.confirmationService.confirm({
      message: 'ยืนยันการยกเลิกการขออนุมัติพิเศษ',
      header: 'ยกเลิกการขออนุมัติพิเศษ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.cancelApprovalCourse(id).toPromise()
          .then((res) => {
            if (res['result'] === 'Success') {
              this.course.status = 'ยังไม่ได้ลงทะเบียน';
              this.course.canRegister = 1;
              this.course.saStatus = null;
              this.spinner.hide();
              this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการขออนุมัติพิเศษสำเร็จ' });
            } else if (res['result'] === 'Fail') {
              this.spinner.hide();
              this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: res['errorMessage'] });
            }
          }
          ).catch(err => {
            this.spinner.hide();
            this.messageService.add({
              severity: 'error', summary: 'ข้อความจากระบบ',
              detail: 'ยกเลิกการขออนุมัติพิเศษไม่สำเร็จเนื่องจาก ' + err['error']['errorMessage']
            });
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        // this.messageService.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ปฏิเสธยกเลิกการขออนุมัติพิเศษ' });
      }
    });
  }

  public saCourse(courseId: number) {
    this.closeMessage();
    this.displayApproveDialog = true;
    this.courseId = courseId;
  }

  public rgCourse(courseId: number) {
    this.spinner.show();
    this.closeMessage();
    this.courseId = courseId;
    this.courseService.getCourseByid(courseId).toPromise()
      .then(res => {
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
      }
      ).catch(err => {
        console.log(err['error']['errorMessage']);
      }).finally(() => this.spinner.hide());
    this.displayRegisterDialog = true;
  }

  private getData() {
    this.spinner.show();
    this.route.params.pipe(switchMap(param =>
      this.courseService.getCourseByid(param.id).toPromise()
        .then(res => {
          if (res.status === 'Success') {
            this.course = res['data'];
            this.status = this.course.mhcStatus;
            this.memberId = this.course.memberId;
          }
        }).catch(err => {
          console.log(err['error']['errorMessage']);
        }).finally(() =>
          this.spinner.hide()))).subscribe();
  }

  private getMemberByCourseId() {
    this.spinner.show();
    this.courseService.getUserByCourseId(this.id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(res => {
        // tslint:disable-next-line: forin
        for (let key in res.data) {
          this.memberIdList.push(res.data[key].memberId);
          this.totalMember = this.memberIdList.length;
          this.manageUserService.getMemberById(res.data[key].memberId).subscribe(res => {

            this.memberList.push(res);
          });
        }
      });


  }

  // count graduated course
  private getCountGraduatedCourse() {
    this.spinner.show();
    this.courseService.getTotalRecord('1')
      .pipe(finalize(() => this.spinner.hide())).subscribe(res => {
        if (res['status'] === 'Success') {
          this.total = res['data'][0]['totalRecord'];
        }
      });
  }

  createExcel() {
    this.spinner.show();
    this.excelService.createExcel(this.id).
      pipe(finalize(() => this.spinner.hide())).subscribe(res => {
        if (res['result'] === 'Success') {
          this.downloadFile('temple.xls');
          this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการบันทึกไฟล์สำเร็จ', sticky: true });
        } else {
          this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการบันทึกไฟล์ไม่สำเร็จ', life: 5000 });
        }
      });
  }

  goToProfile() {
    const path = `/profile/${this.memberId}`;
    this.router.navigate([path]);
  }

  getBack() {
    this.location.back();
  }

  /**
   * ตรวจสอบค่าที่รับเข้ามาใหม่ในกรณีกรอกข้อมูลไม่ครบถ้วน
   */
  subscribeInputMessageWaring(e) {
    if (e === 'as') {
      this.assignFormCourse
        .valueChanges
        .pipe(
          debounceTime(500),
          distinctUntilChanged()
        ).subscribe(() => this.waringAssignMessage());
      this.waringAssignMessage();
    } else {
      this.approveFormCourse
        .valueChanges
        .pipe(
          debounceTime(500),
          distinctUntilChanged()
        )
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
        this.formAssignError[field] = this.validationAssignMessage[field].required;
        if (field !== 'transportation' && control.hasError('maxlength')) {
          this.formLengthError[field] = '**ข้อความต้องน้อยกว่า 100 ตัวอักษร';
        } else {
          this.formLengthError[field] = '';
        }
      }
    }
  }

  waringApproveMessage() {
    if (!this.approveFormCourse) {
      return;
    }
    for (const field of Object.keys(this.formApproveError)) {
      this.formApproveError[field] = '';
      const control = this.approveFormCourse.get(field);
      if (control && !control.valid && this.validationApproveMessage[field]) {
        this.formApproveError[field] = this.validationApproveMessage[field].required;
        if (field !== 'transportation' && control.hasError('maxlength')) {
          this.formLengthError[field] = '*ข้อความต้องมีตัวอักษรน้อยกว่า 100 ตัว';
        } else {
          this.formLengthError[field] = '';
        }
      }
    }
  }

  onSubmit(e) {
    this.setValidate(e);
    if (!this.assignFormCourse.valid && !this.approveFormCourse.valid) {
      this.subscribeInputMessageWaring(e);
    } else {
      if (e === 'as') {
        this.assignCourse();
      } else {
        this.approvalCourse();
      }
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
          control.setValidators([Validators.required, Validators.maxLength(100)]);
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
          control.setValidators([Validators.required, Validators.maxLength(255)]);
        } else {
          control.setValidators([Validators.required, Validators.maxLength(100)]);
        }
        control.updateValueAndValidity();
      });
    }
  }
  public closeMessage() {
    this.messageService.clear();
  }
}
