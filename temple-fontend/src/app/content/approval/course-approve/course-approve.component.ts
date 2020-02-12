import { Component, OnInit, Input, Output } from '@angular/core';

@Component({
  selector: 'app-course-approve',
  templateUrl: './course-approve.component.html',
  styleUrls: ['./course-approve.component.scss']
})
export class CourseApproveComponent implements OnInit {

  @Input() option:String;
  @Input() member:any[]
  @Output() listData;

  constructor() { }

  ngOnInit() {
  }

}
