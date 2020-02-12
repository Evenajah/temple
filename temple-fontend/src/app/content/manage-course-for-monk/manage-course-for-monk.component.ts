import { Component, OnInit } from '@angular/core';
import { LazyLoadEvent, ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { switchMap, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { CourseService } from '../courses/shared/course.service';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/shared/interfaces/course';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-manage-course-for-monk',
  templateUrl: './manage-course-for-monk.component.html',
  styleUrls: ['./manage-course-for-monk.component.scss']
})
export class ManageCourseForMonkComponent implements OnInit {

  // msgs: any[] = [];
  courses: Course[];
  cols: any[];
  public menu: MenuItem[];
  public totalRecords: number;
  public status: string;

  constructor(
    private courseService: CourseService,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute,
    private spinner: NgxSpinnerService
  ) {
  }

  ngOnInit() {
    this.status = '0';
    this.getData();
    this.getTotalRecord();

    this.cols = [
      { field: 'stDate', header: 'วันที่' },
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'locationName', header: 'สถานที่' },
      { field: 'conditionMin', header: 'หมายเหตุ' },
    ];

    this.breadCrumbService.setPath([
      { label: 'จัดการคอร์ส', routerLink: '/manageCourseForMonk' },
    ]);
  }

  // แก้ลบข้อมูลให้ออกจากตาราง
  deleteCourse(id) {
    this.closeMessage();
    this.confirmationService.confirm({
      message: 'คุณแน่ใจหรือไม่ ? ที่จะทำการปิดคอร์ส นักเรียนที่ขออนุมัติเข้าเรียนจะถูกยกเลิก และนักเรียนที่กำลังเรียนจะไม่ถูกอนุมัติให้ผ่านการเรียน',
      header: 'ข้อความจากระบบ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.deleteCourse(id)
          .pipe(finalize(() => this.spinner.hide())).subscribe(res => {
            if (res['status'] === 'Success') {
              this.spinner.hide();
              this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการปิดคอร์สสำเร็จ' });
              const index = this.courses.findIndex(e => e.id === id);
              const upd = this.courses[index];
              this.courses = [
                ...this.courses.slice(0, index),
                ...this.courses.slice(index + 1)
              ];
            } else {
              this.spinner.hide();
              this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการปิดคอร์สไม่สำเร็จ' });
            }
          }

          );

      },
      reject: () => {
        this.messageService.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการปิดคอร์ส' });
      }
    });
  }

  showToast(key, detail) {
    // this.messageService.clear();
    this.messageService.add(
      {
        key: key,
        sticky: true,
        summary: 'ข้อความจากระบบ ',
        detail: detail
      }
    );
  }
  private getTotalRecord() {
    this.courseService.getTotalRecord(this.status).subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalRecords = res['data'][0]['totalRecord'];

      }
    });
  }
  private getData() {
    this.spinner.show();
    this.courseService.getCourses()
      .pipe(finalize(() => this.spinner.hide())).subscribe(res => {
        if (res['status'] === 'Success') {
          this.courses = res['data'];
        }
      });
  }

  public onRowSelect(e) {
    const course: Course = e.data;
    this.router.navigate(['/courses', course.id]);
  }

  public closeMessage() {
    this.messageService.clear();
  }
}


