import { Component, OnInit, Input, Output } from '@angular/core';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { MemberApproval } from 'src/app/shared/interfaces/member-approval';
import { ActivatedRoute, Router } from '@angular/router';
import { ApprovalService } from '../approval.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-approval-form',
  templateUrl: './approval-form.component.html',
  styleUrls: ['./approval-form.component.scss']
})
export class ApprovalFormComponent implements OnInit {

  @Input() option: String;
  courseOutTime: any[] = [];
  @Input() member: MemberApproval[];
  @Input() cols: any[];
  @Input() fieldId: string;
  @Output() listData;
  // @Input() msgs: Message[] = [];
  public courseId: string;
  public nameCourse: string;
  public btnrej: boolean;
  public courseType: string;
  constructor(
    private breadCrumbService: BreadcrumbService,
    private route: ActivatedRoute,
    private approvalService: ApprovalService,
    private confirmationService: ConfirmationService,
    private messageServise: MessageService,
    private router: Router,
    public spinner: NgxSpinnerService

  ) { }

  ngOnInit() {
    this.option = '2';
    this.fieldId = 'specialApproveId';
    this.courseId = this.route.snapshot.paramMap.get('id');
    this.courseType = this.route.snapshot.queryParamMap.get('type');
    this.initMember();
    this.nameCourse = this.route.snapshot.queryParamMap.get('course');
    this.setBreadCrumb();

    this.cols = [
      { field: 'displayName', header: 'ชื่อ-นามสกุล' },
      { field: 'transportationName', header: 'การเดินทาง' },
      { field: 'detail', header: 'คำขออนุมัติพิเศษ' },
      { field: 'checked', header: '' }
    ];

    this.courseId = this.route.snapshot.paramMap.get('id');
    this.courseType = this.route.snapshot.queryParamMap.get('type');
    this.initMember();

    this.nameCourse = this.route.snapshot.queryParamMap.get('course');


  }
  setBreadCrumb() {
    if (this.courseType === 'OutTime') {
      this.breadCrumbService.setPath([{ label: 'การอนุมัตินอกเวลา', routerLink: '/approvalCourseOutTime' }, { label: 'อนุมัติผู้เรียนนอกเวลา' }]);
    } else {
      this.breadCrumbService.setPath([{ label: 'การอนุมัติพิเศษ', routerLink: '/approval' }, { label: 'อนุมัติผู้เรียนพิเศษ' }]);
    }

  }
  initMember() {
    if (this.courseType === 'OutTime') {
      this.approvalService.getMemberForApproveOutTime(+this.courseId)
        .subscribe(res => {
          console.log(res['data']['0']);

          if (res['status'] === 'Success') {
            this.courseOutTime = res['data']['0'];
          }
        });
    } else {
      this.approvalService.getMemberForApprove(+this.courseId)
        .subscribe(res => {
          if (res !== null) {
            if (res['status'] === 'Success') {
              this.member = res['data'];
              if (this.member.length === 0) {
                this.member = [{ displayName: 'ไม่มีข้อมูล' }];
              }
            }
          } else {
            this.member = [];
            if (this.member.length === 0) {
              this.member = [{ displayName: 'ไม่มีข้อมูล' }];
            }
          }
        });
    }
  }

  showDialog(e) {
    console.log(e);
    // เขียน api ตอบรับ outTime ใหม่
    this.spinner.show();
    this.btnrej = true;
    const message = e.status === 1 ? '' : 'ไม่';
    this.confirmationService.confirm({
      message: message + 'ต้องการอนุมัติพิเศษ',
      header: 'การอนุมัติพิเศษ',
      accept: () => {
        this.spinner.show();
        this.approvalService.approveStudents(e)
          .subscribe((res) => {
            // console.log(res);
            if (res['status'] === 'Success') {
              this.initMember();
              this.spinner.hide();
              this.messageServise.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการ' + message + 'อนุมัติพิเศษสำเร็จ' });
            } else {
              this.btnrej = false;
              this.spinner.hide();
              this.messageServise.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการ' + message + 'อนุมัติพิเศษไม่สำเร็จ' });
            }
          });
      },
      reject: () => {
        this.btnrej = false;
        this.messageServise.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการ' + message + 'อนุมัติพิเศษ' });
      }
    });
    this.spinner.hide();
  }
  showDialogOutTime(e) {
    console.log(e);
    // เขียน api ตอบรับ outTime ใหม่
    this.spinner.show();
    this.btnrej = true;
    const message = e.status == '1' ? '' : 'ไม่';
    this.confirmationService.confirm({
      message: message + 'ต้องการอนุมัตินอกเวลา',
      header: 'การอนุมัตินอกเวลา',
      accept: () => {
        this.spinner.show();
        this.approvalService.approveStudents(e)
          .subscribe((res) => {
            if (res['status'] === 'Success') {
              this.spinner.hide();
              console.log(res);
              this.messageServise.add({
                severity: 'success',
                key: 'ApproveAlertMessage',
                sticky: true,
                summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการเรียบร้อย'
              });
              // this.initMember();
            } else {
              this.btnrej = false;
              this.spinner.hide();
              this.messageServise.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการ' + message + 'อนุมัตินอกเวลาไม่สำเร็จ' });
            }

          });
      },
      reject: () => {
        this.btnrej = false;
        this.messageServise.add({ severity: 'info', summary: 'ข้อความจากระบบ', detail: 'ยกเลิกการ' + message + 'อนุมัตินอกเวลา' });
      }
    });
    this.spinner.hide();
  }
}
