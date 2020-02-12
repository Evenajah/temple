import { Component, OnInit } from '@angular/core';
import {BreadcrumbService} from '../../../shared/service/breadcrumb.service';

@Component({
  selector: 'app-course-resgister',
  templateUrl: './course-resgister.component.html',
  styleUrls: ['./course-resgister.component.css']
})
export class CourseResgisterComponent implements OnInit {

  constructor(
      private breadCrumbService: BreadcrumbService,

  ) { }

  ngOnInit() {
      this.breadCrumbService.setPath([
          {label: 'การอนุมัติ', routerLink: '/approval'},
      ]);
  }

}
