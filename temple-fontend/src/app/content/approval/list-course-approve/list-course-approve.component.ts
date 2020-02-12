import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService, ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { ApprovalService } from '../approval.service';
import { BreadcrumbService } from '../../../shared/service/breadcrumb.service';
import { Course } from '../../../shared/interfaces/course';
import { of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-course-approve',
  templateUrl: './list-course-approve.component.html',
  styleUrls: ['./list-course-approve.component.scss']
})
export class ListCourseApproveComponent implements OnInit {
  public cols: any[];
  public courses: Course[];
  public coursesOutTime: Course[];
  public menu: MenuItem[];
  public totalRecords: number;
  public totalRecordsOutTime: number;
  public loading: boolean;
  public loadingOutTime: boolean;
  public selectedCourse: Course;
  public courseId: string;
  public title: string;
  url: string;
  goToCourse: string;
  textBreadCrumb;
  dataOutTimeReport: string;
  dataInTimeReport: number;
  reportNoData: string;
  public methodLazyLoad: string;
  constructor(
    private approvalService: ApprovalService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private router: Router,
  ) {
    this.url = this.router.url;




  }

  ngOnInit() {
    this.loading = true;
    this.loadingOutTime = true;
    this.getTotalRecord();
    this.getTotalRecordOutTime();
    this.setColumn();
    this.setBreadCrumb();
    this.checkTotalRecord();
    this.setData();

  }
  setData() {
    if (this.isInTime()) {
      this.title = 'จัดการอนุมัติพิเศษ';

    } else {
      this.title = 'จัดการอนุมัตินอกเวลา';

    }
  }

  public loadData(e: LazyLoadEvent) {
    let query = '';
    if (this.isInTime()) {
      if (e.globalFilter) {
        query = e.globalFilter;
      }
      this.getData(e.first, e.rows, query);
    } else {
      if (e.globalFilter) {
        query = e.globalFilter;
      }
      this.getDataOutTime(e.first, e.rows, query);
    }

  }

  private setColumn() {
    this.cols = [
      { field: 'name', header: 'ชื่อคอร์ส' },
      { field: 'conditionMin', header: 'หมายเหตุ' },
      { field: 'detail', header: 'รายละเอียด' },
    ];
  }

  private setBreadCrumb() {
    if (this.isInTime()) {
      this.textBreadCrumb = { label: 'การอนุมัติพิเศษ', routerLink: '/approval' };
    } else {
      this.textBreadCrumb = { label: 'การอนุมัตินอกเวลา', routerLink: '/approvalCourseOutTime' };
    }
    this.breadCrumbService.setPath([{ ...this.textBreadCrumb }
    ]);
  }

  public isInTime(): boolean {
    if (this.url === '/approval') {
      return true;
    } else if (this.url === '/approvalCourseOutTime') {
      return false;
    }
  }

  onRowSelect(e) {
    // console.log(e);
    if (this.isInTime()) {
      this.router.navigateByUrl(`/approval/${e.data.id}?course=${e.data.name}&&type=InTime`);
    } else {
      this.router.navigateByUrl(`/approvalCourseOutTime/${e.data.id}?course=${e.data.name}&&type=OutTime`);
    }
  }


  private getData(first = 0, rows = 5, query: string = '') {
    this.loading = true;
    of([first, rows, query]).pipe(
      switchMap(([firstCon, rowsCon, queryCon]: [number, number, string]) =>
        this.approvalService.getCoursesApproval(firstCon, rowsCon, queryCon))
    ).subscribe(res => {
      if (res['status'] === 'Success') {
        if (res['status'] !== [] || res['status'] !== null) {
          this.courses = [...res['data']];
          this.getTotalRecord();
          console.log(this.courses);

        } else {
          this.courses = null;
        }
        this.loading = false;
        const data = [...res.data];
        data.map(dataInTime => {
          this.dataInTimeReport = dataInTime.numberOfMembers;
        });
        this.reportNoData = this.dataInTimeReport === 0 ? '( ไม่มีข้อมูลคำขออนุมัติพิเศษ )' : '';
      }
    });
  }

  private getDataOutTime(first = 0, rows = 5, query: string = '') {
    this.loading = true;
    of([first, rows, query]).pipe(
      switchMap(([firstCon, rowsCon, queryCon]: [number, number, string]) =>
        this.approvalService.getCoursesApprovalOutTime(firstCon, rowsCon, queryCon))
    ).subscribe(res => {
      if (res['status'] === 'Success') {
        this.courses = [...res['data']];
        this.loading = false;
        this.getTotalRecordOutTime();
        console.log(this.courses);
        
        this.dataOutTimeReport = res['data'].length === 0 ? '( ไม่มีข้อมูลคำขออนุมัติพิเศษ )' : '';
      }
    });
  }

  private getTotalRecord() {
    this.approvalService.getTotalRecord().subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalRecords = res['data'][0]['totalRecord'];
        console.log(this.totalRecords);
        
      }
    });
  }

  private getTotalRecordOutTime() {
    this.approvalService.getTotalRecordOutTime().subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalRecords = res['data'][0]['totalRecord'];
      }
    });
  }

  checkTotalRecord() {
    if (this.url === 'approval') {
        
    } else {
      
    }
  }

}
