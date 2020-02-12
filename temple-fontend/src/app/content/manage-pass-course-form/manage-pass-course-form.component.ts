import {Component, OnInit, Input, Output} from '@angular/core';
import {BreadcrumbService} from 'src/app/shared/service/breadcrumb.service';
import {ApproveForMember} from 'src/app/shared/interfaces/approve-for-member';
import {ManagePassCourseService} from 'src/app/shared/service/manage-pass-course.service';
import {ActivatedRoute} from '@angular/router';

import {ConfirmationService, MessageService} from 'primeng/api';

@Component({
  selector: 'app-manage-pass-course-form',
  templateUrl: './manage-pass-course-form.component.html',
  styleUrls: ['./manage-pass-course-form.component.css']
})
export class ManagePassCourseFormComponent implements OnInit {

  @Input() option: String;
  @Input() member: ApproveForMember[];
  @Input() cols: any[];
  @Input() fieldId: string;
  @Output() listData;
  // @Input() msgs: Message[] = [];
  public courseId: string;
  public nameCourse:string;
  totalRecords: any;
  btnrej: boolean;
  

  constructor(
    private breadCrumbService: BreadcrumbService,
    private managePassCourse: ManagePassCourseService,
    private route: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
  ) {
  }

  ngOnInit() {
    this.option = '1';
    this.fieldId = 'mhcId';
    this.breadCrumbService.setPath([
      {label: 'อนุมัติการผ่านหลักสูตร',routerLink: '/managepasscourse'},
      {label: 'อนุมัติผู้เรียน'},
    ]);

    this.cols = [
      {field: 'fullname', header: 'ชื่อ-นามสกุล'},
      {field: 'checked', header: ''}
    ];

    this.courseId = this.route.snapshot.paramMap.get('id');
    this.initMember();

    this.nameCourse = this.route.snapshot.queryParamMap.get('course');

    
  }

  initMember() {
    this.totalRecords = 0;
    this.managePassCourse.getMemberInCourse(+this.courseId)
      .subscribe(res => {
        if (res['status'] === 'Success') {
          this.member = res['data'];
          this.totalRecords = this.member.length;
        }
        
      });
  }

  setMemberPassCourse(e) {
    const data = {
      mhcList: [
        ...e.member
      ],
      cId: e.courseId,
    };
    return this.managePassCourse.updateMemberPassCourse(data);
  }

  showDialog(e) {
    this.btnrej = true;
    this.confirmationService.confirm({
      message: 'คุณต้องการดำเนินการอนุมัติผ่านหลักสูตรใช่หรือไม่',
      header: 'อนุมัติการผ่านหลักสูตร',
      accept: () => {
        this.setMemberPassCourse(e)
          .subscribe((res) => {
            if (res['status'] === 'Success') {
              this.initMember();
              this.messageService.add({severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการอนุมัติการผ่านหลักสูตรสำเร็จ'});
            } else {
              this.btnrej = false;
              this.messageService.add({severity: 'error', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการอนุมัติการผ่านหลักสูตรไม่สำเร็จ'});
            }
          });
      },
      reject: () => {
        this.btnrej = false;
        this.messageService.add({severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการอนุมัติการผ่านหลักสูตร'});
      }
    });
    
  }


}
