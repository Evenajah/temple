import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MenuItem, MessageService } from 'primeng/api';
import { Course } from 'src/app/shared/interfaces/course';
import { CourseService } from '../courses/shared/course.service';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { Router, ActivatedRoute } from '@angular/router';
import { of, Observable, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { CourseCreateComponent } from '../courses/course-create/course-create.component';
import { CourseEditComponent } from '../courses/course-edit/course-edit.component';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-manage-course',
  templateUrl: './manage-course.component.html',
  styleUrls: ['./manage-course.component.scss']
})
export class ManageCourseComponent implements OnInit, OnDestroy {

  // public msgs: any[] = [];
  public courses: Course[];
  public cols: any[];
  public menu: MenuItem[];
  public totalRecords: number;
  public loading: boolean;
  public status: string;
  public selectedCourse: Course;
  public displayCreateDialog = false;
  public displayEditDialog = false;
  public courseId: number;
  @ViewChild('CourseEdit') CourseEdit: CourseEditComponent;
  @ViewChild('CourseCreate') CourseCreate: CourseCreateComponent;
  private courseSubscription: Subscription;

  constructor(
    private courseService: CourseService,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    public spinner: NgxSpinnerService
  ) {
  }

  ngOnInit() {
    this.status = '';
    this.getData();
    this.getTotalRecord();

    this.cols = [
      { field: 'createDate', header: 'วันที่สร้าง' },
      { field: 'lastUpdate', header: 'วันที่แก้ไขล่าสุด' },
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'locationName', header: 'สถานที่' },
      { field: 'transportTemple', header: 'การเดินทางของวัด' },
      { field: 'conditionMin', header: 'หมายเหตุ' },
    ];

    this.breadCrumbService.setPath([
      { label: 'จัดการคอร์ส', routerLink: '/manageCourse' },
    ]);
    this.loading = true;
  }

  createCourse() {
    this.CourseCreate.getTransport();
    this.displayCreateDialog = true;
  }

  editCourse(id) {
    this.CourseEdit.settingForm(id);
    this.displayEditDialog = true;
  }

  deleteCourse(id) {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'ยืนยันการปิดคอร์ส',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.messageService.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการปิดคอร์สสำเร็จ' });
      },
      reject: () => {
        this.messageService.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการปิดคอร์ส' });
      }
    });
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
    this.courseSubscription = this.courseService.getCourses().subscribe(res => {
      if (res['status'] === 'Success') {
        this.courses = res['data'];
        this.loading = false;
      }
    });
    this.spinner.hide();
  }

  public onRowSelect(e) {
    const course: Course = e.data;
    this.router.navigate(['/courses', course.id]);
  }

  public closeCreateDialog(e) {
    this.displayCreateDialog = false;
    if (e != null) {
      this.messageService.add(e[0]);
    }
    this.getData();
    this.getTotalRecord();
  }

  public closeEditDialog(e) {
    this.displayEditDialog = false;
    if (e != null) {
      this.messageService.add(e[0]);
    }
    this.getData();
    // this.getTotalRecord();
  }

  ngOnDestroy(): void {
    this.courseSubscription.unsubscribe();
  }
}
