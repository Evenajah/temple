import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { Course } from 'src/app/shared/interfaces/course';
import { MenuItem, MessageService, ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { ApprovalService } from '../approval/approval.service';
import { Router } from '@angular/router';
import { switchMap, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { ManagePassCourseService } from 'src/app/shared/service/manage-pass-course.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-manage-pass-course',
  templateUrl: './manage-pass-course.component.html',
  styleUrls: ['./manage-pass-course.component.scss']
})
export class ManagePassCourseComponent implements OnInit {

  public cols: any[];
  public courses: Course[];
  public menu: MenuItem[];
  public totalRecords: number;
  public loading: boolean;
  public selectedCourse: Course;
  public courseId: string;

  private totalRec: number;

  constructor(
    private managePassCourse: ManagePassCourseService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private router: Router,
    private spinner: NgxSpinnerService
  ) {
  }

  ngOnInit() {
    this.loading = true;
    this.setColumn();
    this.getTotalRecord();

    this.breadCrumbService.setPath([
      { label: 'อนุมัติการผ่านหลักสูตร' },
    ]);
  }

  private setColumn() {
    this.cols = [
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'detail', header: 'รายละเอียด' },
      { field: 'numberOfMembers', header: 'จำนวนนักเรียน' },
    ];
  }

  onRowSelect(e) {
    this.router.navigateByUrl(`/managepasscourse/${e.data.id}?course=${e.data.name}`);
  }

  private getData(first = 0, rows = 5, query: string = '') {
    this.loading = true;
    this.spinner.show();
    of([first, rows, query]).pipe(
      switchMap(([firstCon, rowsCon, queryCon]: [number, number, string]) =>
        this.managePassCourse.getAllCourse(firstCon, rowsCon, queryCon))
    ).pipe(finalize(() => this.spinner.hide())).subscribe(res => {
      if (res['status'] === 'Success') {
        this.courses = res['data'];
        this.loading = false;
      }
    });
  }

  private getTotalRecord() {
    this.spinner.show();
    this.managePassCourse.getTotalRecord().pipe(finalize(() => this.spinner.hide())).subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalRecords = res['data'][0]['totalRecord'];
      }
    });
  }

  public loadData(e: LazyLoadEvent) {
    let query = '';
    if (e.globalFilter) {
      query = e.globalFilter;
    }
    this.getData(e.first, e.rows, query);
  }
}
