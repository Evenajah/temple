import {Component, OnInit} from '@angular/core';
import {CourseService} from '../shared/course.service';

import {BreadcrumbService} from '../../../shared/service/breadcrumb.service';
import { Course } from 'src/app/shared/interfaces/course';

@Component({
    selector: 'app-course-form',
    templateUrl: './course-form.component.html',
    styleUrls: ['./course-form.component.css']
})
export class CourseFormComponent implements OnInit {
    displayDialog: boolean;
    newCourse: boolean;
    courses: Course[];
    course: Course;
    cols: any[];

    constructor(
        private courseService: CourseService,
        private breadCrumbService: BreadcrumbService,

    ) {
    }

    ngOnInit() {
        //this.courses = this.courseService.getCourses();
        this.cols = [
            {field: 'date', header: 'วันที่'},
            {field: 'name', header: 'ชื่อคอร์ส'},
            {field: 'location ', header: 'สถานที่'},
            {field: 'annotation', header: 'หมายเหตุ'},
        ];
        this.breadCrumbService.setPath([
            {label: 'การอนุมัติ', routerLink: '/approval'},
        ]);
    }

    showDialogToAdd() {
        this.newCourse = true;
        this.course = {};
        this.displayDialog = true;
    }

    save() {
        //this.courses = this.courseService.save(this.course .name);
        this.course = {};
        this.displayDialog = false;
    }

    clear() {
        this.course = {};
    }

    showEdit(id) {
        this.newCourse = false;
        this.course = this.courses.filter(e => e.id == id)[0];
        this.displayDialog = true;
    }

}
