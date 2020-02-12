import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MenuItem, LazyLoadEvent } from 'primeng/api';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { ManageUserService } from 'src/app/shared/service/manage-user.service';
import { Member } from 'src/app/shared/interfaces/member';
import { Router, ActivatedRoute } from '@angular/router';
import { map, switchMap } from 'rxjs/operators';
import { CourseService } from '../courses/shared/course.service';
import { Course } from 'src/app/shared/interfaces/course';
import { of } from 'rxjs';
import { ManageRoleService } from 'src/app/shared/service/manage-role.service';
import { HistoryDharmaService } from 'src/app/shared/service/history-dharma.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public menu: MenuItem;
  public userData: Member;
  public userId: string;
  public graduatedcourses: Course[];
  public cols: any[];
  public showRole: boolean;
  public values: Course[] = [];
  public totalGraduatedRecords: number;
  public selectedCourse: Course;
  public courseHisList: any[] = [];
  private personalId: string;
  @Output() status = new EventEmitter();
  constructor(
    private breadCrumbService: BreadcrumbService,
    private manageUser: ManageUserService,
    private router: Router,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private roleService: ManageRoleService,
    private historyDharmaService: HistoryDharmaService,
    public spinner: NgxSpinnerService,
  ) {
  }

  ngOnInit() {
    this.getData();
    this.getCoursesHistoryDharma();
    this.showRole = this.roleService.getUserRoleStatus();
    if (this.showRole) {
      this.getGraduatedData();
      this.getGraduatedRecord();
    }
    this.userId = localStorage.getItem('userId');
    this.showRole = this.roleService.getUserRoleStatus();
    this.breadCrumbService.setPath([
      { label: 'ข้อมูลส่วนตัว' },
    ]);

    this.cols = [
      { field: 'stDate', header: 'วันที่สำเร็จการอบรม' },
      { field: 'name', header: 'ชื่อคอร์ส' }
    ];
  }

  getCoursesHistoryDharma() {
    this.personalId = this.route.snapshot.paramMap.get('id');
    this.historyDharmaService.getHistoryDharmaByMemberId(this.personalId).subscribe(
      res => {
        if (res.status === 'Success') {
          this.courseHisList = res.data;
        }
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );
  }

  getData() {
    this.spinner.show();
    this.route.params.pipe(map(res => res.id)).subscribe(id => {
      this.manageUser.getUser(id).toPromise()
      .then(res => {
          if (res['status'] === 'Success') {
            this.userData = res['data'];
          }
        }
      ).catch((err) => {
          console.log(err['error']['errorMessage']);
        }
      ).finally(() => this.spinner.hide());
    });
  }

  private getGraduatedData() {
    this.values = [];
    this.courseService.getCoursesUser('1')
      .subscribe(res => {
        if (res['status'] === 'Success') {
          this.graduatedcourses = res['data'];
        }
      });
  }

  private getGraduatedRecord() {
    // status = 1 / สำเร็จศึกษา
    this.courseService.getTotalRecord('1').subscribe(res => {
      if (res['status'] === 'Success') {
        this.totalGraduatedRecords = res['data'][0]['totalRecord'];
      }
    });
  }

  goToEdit() {
    const path = `/profile/${this.userId}/edit`;
    this.router.navigate([path]);
  }

  public onRowSelect(e) {
    const course: Course = e.data;
    this.router.navigate(['/courses', course.id]);
  }

}
