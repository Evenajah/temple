import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BreadcrumbService } from '../../../shared/service/breadcrumb.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { CourseService } from '../shared/course.service';
import { formatDate, DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationService } from '../../location/location.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { debounceTime, distinctUntilChanged, count, finalize } from 'rxjs/operators';
import { TransportService } from 'src/app/shared/service/transport.service';
import { TransportationTemple } from 'src/app/shared/interfaces/transportation-temple';
import { Spinner } from 'primeng/primeng';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-course-edit',
  templateUrl: './course-edit.component.html',
  styleUrls: ['./course-edit.component.css']
})
export class CourseEditComponent implements OnInit {

  public msgs: any;
  public noticearr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10'];
  public notice: Array<any> = [];
  public locations: Location[];
  public transport: TransportationTemple[];
  public transportTemple: any[];
  public filteredTeacher: any[];
  public teachers: any[];
  public teacher: any;
  public teacherLength;
  public obj = [];
  public pipe = new DatePipe('th-TH');
  public yearRange: string;
  public optionTime: any;
  public transportTempleList: any;

  @Input() displayEditDialog = false;
  @Input() courseId: number;
  @Output() closeDisplayEditDialog = new EventEmitter();

  formEdit = new FormGroup(
    {
      courseName: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      detail: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      location: new FormControl('', Validators.required),
      date: new FormControl('', Validators.required),
      conditionMin: new FormControl('', Validators.required),
      teachers: new FormControl('', Validators.required),
      transportTemple: new FormControl(null)
    }
  );

  public formError = {
    courseName: '',
    detail: '',
    location: '',
    date: '',
    teachers: '',
    conditionMin: ''
  };

  public formLengthError = {
    courseName: '',
    detail: '',
    location: '',
    date: '',
    teachers: '',
    conditionMin: ''
  };

  public validationMessage = {
    courseName: {
      required: 'ชื่อคอร์ส*'
    },
    detail: {
      required: 'รายละเอียด*'
    },
    location: {
      required: 'สถานที่*'
    },
    date: {
      required: 'วันที่เรียน*'
    },
    teachers: {
      required: 'ผู้สอน*'
    },
    conditionMin: {
      required: 'หมายเหตุ*'
    }
  };


  constructor(
    private breadCrumbService: BreadcrumbService,
    private formBuilder: FormBuilder,
    private courseService: CourseService,
    private route: ActivatedRoute,
    private locationService: LocationService,
    private transportTempleService: TransportService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    public spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    this.initNotice();
    /*     this.courseId = this.route.snapshot.paramMap.get('id');*/
    this.courseService.getTeachers().subscribe(
      res => {
        if (res.status === 'Success') {
          this.teachers = res['data'].map(res => {
            return {
              id: res.id,
              name: res.titleDisplay + res.fname + ' ' + res.lname
            };
          });
        }
      },
      error => {
        console.log(error['error']['errorMessage']);
      }
    );
    this.locationService.getLocation().subscribe(
      res => {
        if (res.status === 'Success') {
          this.locations = res.data;
        }
      },
      error => {
        console.log(error['error']['errorMessage']);

      }
    );

    const currentYear = this.pipe.transform(Date.now(), 'yyyy');
    const startYear = parseInt(currentYear) + 5;
    this.yearRange = currentYear + ':' + startYear;
  }

  private initNotice() {
    this.noticearr.map(res => {
      this.notice.push({ id: res });
    });
  }

  settingForm(id) {
    this.spinner.show();
    this.courseId = id;
    this.getTransportationTempleList(id);
    this.courseService.getCourseByid(id)
      .pipe(finalize(() => this.spinner.hide())).subscribe(res => {
        /*var result = [];
        var teacherLength;*/
        const teachers = res['data']['teacherList'].map(res => {
          return {
            id: res['id'],
            name: res['titleDisplay'] + res['fname'] + ' ' + res['lname'],
          };
        });
        for (let i = 0; i < teachers.length; i++) {
          this.obj.push(teachers[i]);
          // teacherLength= teachers.length
        }
        const dateJson = [];
        dateJson[0] = res['data']['stDate'];
        dateJson[1] = res['data']['endDate'];

        const datecon = new Date(dateJson[0]);
        const datecon2 = new Date(dateJson[1]);

        const date = [];
        date[0] = datecon;
        date[1] = datecon2;

        const location = {
          id: res['data']['locationId'],
          name: res['data']['locationName']
        };
        let tranTempSelect = null;
        if (res['data']['transportation'] !== null) {
          tranTempSelect = {
            id: res['data']['transportation'].id,
            name: res['data']['transportation'].name
              + ' เวลารับ : ' + new Date(res['data']['transportation'].timePickUp).toLocaleTimeString('th-TH', this.optionTime)
              + ' เวลาส่ง : ' + new Date(res['data']['transportation'].timeSend).toLocaleTimeString('th-TH', this.optionTime)
          };
        }
        this.formEdit.controls['courseName'].setValue(res['data']['name']);
        this.formEdit.controls['detail'].setValue(res['data']['detail']);
        this.formEdit.controls['location'].setValue(location);
        this.formEdit.controls['teachers'].patchValue(teachers);
        this.formEdit.controls['date'].patchValue(date);
        this.formEdit.controls['conditionMin'].setValue({ id: '' + (res['data']['conditionMin']) });
        this.formEdit.controls['transportTemple'].setValue(tranTempSelect);

      },
        err => console.log(err['error']['errorMessage'])
      );
  }

  // ------------ Get List of Transportation Temple ------------
  getTransportationTempleList(id) {
    this.spinner.show();
    this.optionTime = { hour: '2-digit', minute: '2-digit' };
    this.transportTempleService.getTranSportTemple(id).pipe(finalize(() => this.spinner.hide())).subscribe(
      res => {
        this.transport = res['data'].map(data => {
          return {
            id: data.id,
            name: data.name + ' เวลารับ : ' + new Date(data.timePickUp).toLocaleTimeString('th-TH', this.optionTime) +
              ' เวลาส่ง : ' + new Date(data.timeSend).toLocaleTimeString('th-TH', this.optionTime)
          };
        });
      },
      error => {
        console.log(error['error']['errorMessage']);
      }
    );
  }

  onSubmit() {
    this.setValidate();
    if (!this.formEdit.valid) {
      this.subscribeInputMessageWaring();
      console.log(this.formEdit.get('transportTemple').errors);
    } else {
      this.confirmationService.confirm({
        message: 'ยืนยันการแก้ไขคอร์ส',
        header: 'แก้ไขคอร์ส',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
          this.spinner.show();
          const date2 = this.formEdit.get('date').value;
          const stDate = formatDate(date2[0], 'yyyy-MM-dd', 'th');
          let endDate = '';
          let datesort = [];

          if (date2[1] != null) {
            endDate = formatDate(date2[1], 'yyyy-MM-dd', 'th');
            datesort = date2.map(res => formatDate(res, 'yyyy-MM-dd', 'th')).sort();
          } else {
            endDate = stDate;
            for (let i = 0; i < 2; i++) {
              datesort.push(stDate);
            }
          }
          const id = this.courseId;
          // const lastUpdate = formatDate(Date.now(), 'yyyy-MM-dd hh:mm:ss', 'en');
          let transportTempleId = null;
          if (this.formEdit.get('transportTemple').value !== null) {
            transportTempleId = this.formEdit.get('transportTemple').value.id;
          }
          const course = {
            name: this.formEdit.get('courseName').value,
            detail: this.formEdit.get('detail').value,
            locationId: this.formEdit.get('location').value.id,
            conditionMin: this.formEdit.get('conditionMin').value.id,
            date: datesort,
            stDate: stDate,
            endDate: endDate,
            // lastUpdate: lastUpdate,
            teacher: this.formEdit.get('teachers').value.map(res => res.id),
            // tslint:disable-next-line: max-line-length
            transportation: { id: transportTempleId },
          };

          this.courseService.editCourse(id, course).pipe(finalize(() => {
            this.spinner.hide();
          })).subscribe(res => {
            if (res['result'] === 'Success') {
              this.msgs = [{ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการแก้ไขคอร์สสำเร็จ' }];
              this.onCancle(this.msgs);
            } else if (res['result'] === 'Fail') {
              this.msgs = [{ severity: 'error', summary: 'ข้อความจากระบบ', detail: res['errorMessage'] }];
              this.onCancle(this.msgs);
            }
          });

        },
        reject: () => {
          // this.msgs = [{ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการแก้ไขคอร์ส' }];
          // this.onCancle(this.msgs);
        }
      });
    }
  }

  /**
   * ตรวจสอบค่าที่รับเข้ามาใหม่ในกรณีกรอกข้อมูลไม่ครบถ้วน
   */
  subscribeInputMessageWaring() {
    this.formEdit
      .valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged()
      ).subscribe(() => this.waringMessage());
    this.waringMessage();
  }

  waringMessage() {
    if (!this.formError) {
      return;
    }
    for (const field of Object.keys(this.formError)) {
      this.formError[field] = '';
      const control = this.formEdit.get(field);
      if (control && this.validationMessage[field]) {
        if (!control.valid) {
          this.formError[field] = this.validationMessage[field].required;
          if (field === 'courseName') {
            if (control.hasError('maxlength')) {
              this.formLengthError[field] = '**ข้อความต้องน้อยกว่า 255 ตัวอักษร';
            }
          }
          if (field === 'detail') {
            if (control.hasError('maxlength')) {
              this.formLengthError[field] = '**ข้อความต้องน้อยกว่า 255 ตัวอักษร';
            }
          }
        } else {
          this.formLengthError[field] = '';
        }
      }
    }
  }

  filterTeacherMultiple(event) {
    const query = event.query;
    this.filteredTeacher = this.filterTeacher(query, this.teachers);
  }

  filterTeacher(query, teachers: any): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < teachers.length; i++) {
      const teacher = teachers[i];
      if ((teacher.name).toLowerCase().indexOf(query.toLowerCase()) === 0) {
        filtered.push(teacher);
      }
    }
    return filtered;
  }

  onCancle(message) {
    this.closeDisplayEditDialog.emit(message);
    this.formEdit.reset();
    Object.values(this.formEdit.controls).forEach(df => {
      df.markAsPristine();
      df.setValidators(null);
      df.updateValueAndValidity();
    });
  }

  setValidate() {
    Object.keys(this.formEdit.controls).forEach(key => {
      const control = this.formEdit.get(key);
      control.clearValidators();
      if (key === 'courseName' || key === 'detail') {
        control.setValidators([Validators.required, Validators.maxLength(255)]);
      } else if (key !== 'transportTemple') {
        control.setValidators(Validators.required);
      }
      control.updateValueAndValidity();
    });
  }
}
