import { Component, OnInit, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { formatDate } from '@angular/common';
import { CourseService } from '../../shared/course.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Transportation } from 'src/app/shared/interfaces/transportation';
import { TransportService } from 'src/app/shared/service/transport.service';
import { NgxSpinnerService } from 'ngx-spinner';
@Component({
  selector: 'app-dialog-out-time',
  templateUrl: './dialog-out-time.component.html',
  styleUrls: ['./dialog-out-time.component.scss']
})
export class DialogOutTimeComponent implements OnInit, OnDestroy {
  @Input('displayDialog') displayDialog: boolean = false;
  @Input() courseId;
  @Output() CloseDialog = new EventEmitter();
  filteredTransportation: any[];
  public transportations: Transportation[];
  public formOutTime: FormGroup;
  // เอาไว้ใช้ใน calendar html 
  year = new Date().getFullYear();
  constructor(private confirmationService: ConfirmationService,
    private formBuilder: FormBuilder, private courseService: CourseService
    , private messageService: MessageService, private TransService: TransportService
    , private spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    this.createForm();
    this.getTranSport();
  }
  onClose() {
    this.formOutTime.reset();
    this.CloseDialog.emit(false);

  }
  public assignCourseOutTime() {
    const tranId = this.formOutTime.get('tranId').value;
    const date = this.formOutTime.get('date').value;
    const outTimeCourse = {
      courseId: this.courseId,
      expected: this.formOutTime.get('expected').value,
      experience: this.formOutTime.get('experience').value,
      detail: this.formOutTime.get('detail').value,
      transportationId: tranId.id,
      stDate: formatDate(date[0], 'yyyy-MM-dd', 'en'),
      endDate: formatDate(date[1], 'yyyy-MM-dd', 'en'),
      date: date.map(res => formatDate(res, "yyyy-MM-dd", 'en')).sort()
    };

    this.confirmationService.confirm({
      message: 'ยืนยันการขออนุมัติคอร์สนอกเวลา',
      header: 'ข้อความจากระบบ',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner.show();
        this.courseService.registerCourseOutTime(outTimeCourse).subscribe(res => {
          if (res.status === 'Success') {
            this.spinner.hide();
            this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ขออนุมัตินอกเวลาสำเร็จ' });
            this.onClose();
          } else {
            this.spinner.hide();
            this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: res['errorMessage'] });
          }
        });
        this.displayDialog = false;
      },
      reject: () => { }
    });
  }
  createForm() {
    this.formOutTime = this.formBuilder.group(
      {
        tranId: ['', Validators.required],
        date: ['', Validators.required],
        expected: ['', Validators.required],
        experience: ['', Validators.required],
        detail: ['', Validators.required]
      }
    );
  }
  filterTransportationMultiple(event) {
    const query = event.query;
    this.filteredTransportation = this.filterTransportation(query, this.transportations);
  }
  /**
 * รับค่าจากแป้นพิมพ์
 * @param event ;
 */

  filterTransportation(query, transportations: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < transportations.length; i++) {
      const tranId = transportations[i];
      if (tranId.name.match(query)) {
        filtered.push(tranId);
      }
    }
    return filtered;
  }
  /**
     * เปรียบเทียบค่าที่ได้จากแป้นพิมพ์ กับ ค่าที่ได้จากดาต้าเบส
     * @param query ;
     * @param titleNames ;
     */

  // get การเดินทาง
  private getTranSport() {
    this.TransService.getTranSport().subscribe(res => {
      this.transportations = [...res];
    });
  }
  ngOnDestroy() {
    //memory leak
    this.CloseDialog.unsubscribe();
  }

}
