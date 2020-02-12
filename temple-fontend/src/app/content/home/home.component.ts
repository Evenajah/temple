import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {BreadcrumbService} from '../../shared/service/breadcrumb.service';
import {AuthService} from 'src/app/shared/service/auth.service';
import { PrePathService } from 'src/app/shared/service/pre-path.service';
import { CourseService } from '../courses/shared/course.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public userId: String;
  public role: string;
  total: number;


  constructor(
    private breadCrumbService: BreadcrumbService,
    private authService: AuthService,
    private pathService: PrePathService,
    private courseService: CourseService,
    public spinner: NgxSpinnerService
  ) {
  }

  ngOnInit() {
    this.breadCrumbService.clearPath();
    this.userId = localStorage.getItem('userId');
    this.authService.getRole().subscribe(res => this.role = res);
    localStorage.setItem('preurl', JSON.stringify( this.pathService.setPreviousUrl()));
    this.getCountGraduatedCourse();
  }

  showHomeMenu(...role) {
    return role.includes(this.role);
  }

  // count graduated course
  private getCountGraduatedCourse() {
    this.courseService.getTotalRecord('1').subscribe(res => {
      if (res['status'] === 'Success') {
        this.total = res['data'][0]['totalRecord'];
      }
    });
}

}
