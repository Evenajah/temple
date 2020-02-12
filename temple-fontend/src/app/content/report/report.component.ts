import { Component, OnInit, ViewChild } from '@angular/core';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { FormGroup, FormControl } from '@angular/forms';
import { ReportService } from 'src/app/shared/service/report.service';
import { Report } from 'src/app/shared/interfaces/report';
import { AutoComplete } from 'primeng/autocomplete';
import { count } from 'rxjs/operators';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})


export class ReportComponent implements OnInit {
  cols: any[];
  data: any;
  filteredCourse: any[];
  public course: any[];
  reportData: Report[];
  count: Report;
  formReport = new FormGroup({
    courses: new FormControl('')
  });
  descript: string; // --- variable for description No data -----
  courseCheck: any;
  disabled: boolean = false;


  constructor(
    private breadCrumbService: BreadcrumbService,
    private reportGenService: ReportService
  ) { }

  ngOnInit() {
    this.breadCrumbService.setPath([
      { label: 'ออกรายงาน', routerLink: '/report' },
    ]);

    this.cols = [
      { field: 'coursesName', header: 'ชื่อคอร์ส'},
      { field: 'genderFemale', header: 'หญิง' },
      { field: 'genderMale', header: 'หญิง' },
      { field: 'genderNotspec', header: 'ไม่ระบุ' },
      { field: 'tranTemple', header: 'การเดินทางของวัด' },
      { field: 'transport', header: 'การเดินทางด้วยตัวเอง'},
      { field: 'newStudent', header: 'คนใหม่'},
      { field: 'bangkok', header: 'กรุงเทพฯ'},
      { field: 'central', header: 'ภาคกลาง'},
      { field: 'sakon', header: 'สกลนคร'},
      { field: 'northEast', header: 'ภาคตะวันออกเฉียงเหนือ'},
      { field: 'north', header: 'ภาคเหนือ'},
      { field: 'east', header: 'ภาคตะวันออก'},
      { field: 'western', header: 'ภาคตะวันตก'},
      { field: 'south', header: 'ภาคใต้'},
  ];

  // ----------------- get Course Name -------------------
  this.reportGenService.getCourseName().subscribe( res => {
    if (res.data !== null && res.status !== null) {
      this.course = res.data;
      this.course = this.course.map(
        data => {
          return {id : data.coursesId, name: data.coursesName};
        }
      );
      this.getDataOfReport(null);
    } else {
        this.disabled = true;
        this.course = [{status : null , data: null}];
        this.descript = '(ไม่มีข้อมูลคอร์สเรียน)';
        this.count = {
          genderMale: 0,
          genderFemale: 0,
          /*** gender that not specified. * */
          genderNotspec: 0,
          tranTemple: 0,
          transport: 0,
          newStudent: 0,
          bangkok: 0,
          central: 0,
          /*** จังหวัด สกลนคร * */
          sakon: 0,
          northEast: 0,
          north: 0,
          south: 0,
          east: 0,
          western: 0,
      };
    }
  },
  err => {
    console.log(err['error']['message']);
  });
  }
 /***
  * ---------------getDataofReport(id):  Method for get Data for Report ------------------------
  * @param courseid
  *
  *  */
  getDataOfReport(id) {
    this.count = {
      genderMale: 0,
      genderFemale: 0,
      /*** gender that not specified. * */
      genderNotspec: 0,
      tranTemple: 0,
      transport: 0,
      newStudent: 0,
      bangkok: 0,
      central: 0,
      /*** จังหวัด สกลนคร * */
      sakon: 0,
      northEast: 0,
      north: 0,
      south: 0,
      east: 0,
      western: 0,
    };
    this.reportGenService.getDataByCourseId(id).subscribe(
      res => {
        this.reportData = [...res['data']];
        this.reportData.forEach((report: Report) => {
          this.count = {
            genderMale: this.count.genderMale + report.genderMale,
            genderFemale: this.count.genderFemale + report.genderFemale,
            /*** gender that not specified. * */
            genderNotspec: this.count.genderNotspec + report.genderNotspec,
            tranTemple: this.count.tranTemple + report.tranTemple,
            transport: this.count.transport + report.transport,
            newStudent: this.count.newStudent + report.newStudent,
            bangkok: this.count.bangkok + report.bangkok,
            central: this.count.central + report.central,
            /*** จังหวัด สกลนคร * */
            sakon: this.count.sakon + report.sakon,
            northEast: this.count.northEast + report.northEast,
            north: this.count.north + report.north,
            south: this.count.south + report.south,
            east: this.count.east + report.east,
            western: this.count.western + report.western,
          }
        });
      }
    );
    // for (let key of this.reportData) {
      
    // }
  }

  /**
   * รับค่าจากแป้นพิมพ์
   * @param event
   */
  filterCourseMultiple(event) {
    const query = event.query;
    this.filteredCourse = this.filterCourse(query, this.course);
  }

  filterCourse(query, course: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < course.length; i++) {
      const courses = course[i];
      if (courses.name.match(query)) {
        filtered.push(courses);
      }
    }
    return filtered;
  }

  onSelect(data) {
    const courseId = data.id;
    this.getDataOfReport(courseId);
  }
  onClear(event) {
    if (!event.data) {
      this.getDataOfReport(null);
    }
  }






}
