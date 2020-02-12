import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { TitleName } from '../../shared/interfaces/title-name';
import { TitleNameService } from 'src/app/shared/service/title-name.service';
import { MenuItem, Message, ConfirmationService, MessageService } from 'primeng/api';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged, finalize } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-managed-titlename',
  templateUrl: './managed-titlename.component.html',
  styleUrls: ['./managed-titlename.component.css']
})
export class ManagedTitlenameComponent implements OnInit, AfterViewInit {

  displayDialog: boolean;
  newtitleName: boolean;
  titleNames: TitleName[];
  titleNewName: TitleName;
  duplicateTitle: boolean;
  cols: any[];
  titleId: number;

  @ViewChild('titleName') titleNameField: ElementRef;

  // public msgs: Message[] = [];
  titlesForm = new FormGroup({
    titleName: new FormControl('', [Validators.required, Validators.maxLength(45)]),
    titleNameDisplay: new FormControl('', [Validators.required, Validators.maxLength(45)])
  });
  public formError = {
    titleName: '',
    titleNameDisplay: ''
  };
  public validationMessage = {
    titleName: {
      required: 'คำนำหน้า*'
    },
    titleNameDisplay: {
      required: 'คำย่อ*'
    }
  };
  public formLengthError = {
    titleName: '',
    titleNameDisplay: ''
  };
  t: any;

  constructor(
    private titleNamesService: TitleNameService,
    private breadCrumbService: BreadcrumbService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    public spinner: NgxSpinnerService
  ) {
  }

  ngOnInit() {
    this.breadCrumbService.setPath([
      { label: 'จัดการคำนำหน้า', routerLink: '/manageTitlename' },
    ]);

    this.getTitleName();
    this.cols = [
      { field: 'name', header: 'คำนำหน้า (ตัวเต็ม)' },
      { field: 'display', header: 'คำนำหน้า (ตัวย่อ)' }
    ];

  }
  ngAfterViewInit() {

  }

  getTitleName() {
    this.spinner.show();
    this.titleNamesService.getTitleNamesV2().toPromise()
      .then(res => {
        if (res['status'] === 'Success') {
          this.titleNames = res['data'];
          // this.msgs.push({severity:'success', summary:'ข้อความจากระบบ', detail:'การดำเนินการสำเร็จ'});
        }
      }
      ).catch(err => {
        console.log(err['error']['errorMessage']);
        // this.msgs.push({severity:'error', summary:'ข้อความจากระบบ', detail:'การดำเนินการสำเร็จ'});
      }
      ).finally(() => this.spinner.hide());
  }

  showDialogToAdd() {
    this.newtitleName = true;
    this.displayDialog = true;
  }

  save() {
    // this.messageService.clear;
    this.setValidate();
    const titleName = {
      name: this.titlesForm.get('titleName').value,
      display: this.titlesForm.get('titleNameDisplay').value
    };
    if (!this.titlesForm.valid) {
      this.subscribeInputMessageWaring();
    } else {
      this.spinner.show();
      this.titleNamesService.createTitleName(titleName)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe(res => {
          if (res['status'] === 'Success') {
            this.spinner.hide();
            this.messageService.add({
              severity: 'success',
              summary: 'ข้อความจากระบบ',
              detail: 'ดำเนินการเพิ่มสำเร็จ'
            });
            this.getTitleName();
          }
        },
          (e) => {
            this.spinner.hide();
            this.messageService.add({
              severity: 'error', summary: 'ข้อความจากระบบ',
              detail: 'ดำเนินการบันทึกไม่สำเร็จ (คำนำหน้าชื่อหรือคำย่ออาจมีอยู่แล้ว)', life: 5000
            });
          }
        );
      this.clear();
    }
  }

  clear() {
    this.duplicateTitle = false;
    this.displayDialog = false;
    this.titlesForm.reset();
    Object.values(this.titlesForm.controls).forEach(df => {
      df.markAsPristine();
      df.setValidators(null);
      df.updateValueAndValidity();
    });
  }

  update() {
    // this.messageService.clear();
    const titleName = {
      id: this.titleId,
      name: this.titlesForm.get('titleName').value,
      display: this.titlesForm.get('titleNameDisplay').value
    };

    if (!this.titlesForm.valid) {
      this.subscribeInputMessageWaring();
      this.duplicateTitle = false;
    } else {
      // เช็คซ้ำ
      let name;
      let display;
      this.titleNames.forEach(e => {
        if (e.id !== titleName.id) {
          if (e.name === titleName.name || e.name === titleName.display) {
            name = true;
          } else if (e.display === titleName.name || e.display === titleName.display) {
            display = true;
          }
        }
      });
      if (name || display) {
        this.duplicateTitle = true;
      } else {
        this.duplicateTitle = false;
        this.spinner.show();
        this.titleNamesService.updateTitleName(titleName)
          .pipe(finalize(() => this.spinner.hide())).subscribe(res => {
            if (res['status'] === 'Success') {
              this.spinner.hide();
              this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ: ', detail: 'ดำเนินการแก้ไขคำนำหน้าสำเร็จ' });
              const index = this.titleNames.findIndex(e => e.id === res['data']['id']);
              this.titleNames[index] = res['data'];
            }
          }, (e) => {
            this.spinner.hide();
            this.messageService.add({
              severity: 'error',
              summary: 'ข้อความจากระบบ: ',
              detail: 'ดำเนินการแก้ไขคำนำหน้าไม่สำเร็จ'
            });
          }
          );
        this.clear();
      }
    }

  }

  setEditForm(id) {
    this.titleId = id;
    const titleName = this.titleNames.filter(e => e.id === id)[0];
    Object.keys(this.titlesForm.controls).forEach(key => {
      const control = this.titlesForm.get(key);
      if (key === 'titleName') {
        control.setValue(titleName['name']);
      } else {
        control.setValue(titleName['display']);
      }
    });
    /* this.titlesForm.get('titleName').setValue(titleName['name']);
    this.titlesForm.get('titleDisplay').setValue(titleName['display']); */
    this.showEdit();
  }

  showEdit() {
    this.setValidate();
    this.newtitleName = false;
    this.displayDialog = true;
  }

  delete(id) {
    // this.messageService.clear();
    this.confirmationService.confirm({
      message: '(หาก) คำนำหน้าถูกใช้งานไม่สามารถลบคำนำหน้าได้',
      header: 'ยืนยันการลบคำนำหน้า',
      accept: () => {
        const index = this.titleNames.findIndex(e => e.id === id);
        this.spinner.show();
        this.titleNamesService.deleteTitleName(id)
          .pipe(finalize(() => this.spinner.hide()))
          .subscribe(res => {
            if (res['status'] === 'Success') {
              this.messageService.add({
                severity: 'success',
                summary: 'ข้อความจากระบบ: ',
                detail: 'ดำเนินการลบคำนำหน้าสำเร็จ'
              });
              this.titleNames = [
                ...this.titleNames.slice(0, index),
                ...this.titleNames.slice(index + 1)
              ];
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'ข้อความจากระบบ: ',
                detail: 'ดำเนินการลบคำนำหน้าไม่สำเร็จ'
              });
              // (e) ไม่แสดง
            }
          },
            (e) => {
              console.log(e['error']['errorMessage']);
              this.spinner.hide();
              if (e['error']['errorMessage'] === 'titleName is using') {
                this.messageService.add({
                  severity: 'error',
                  summary: 'ข้อความจากระบบ: ',
                  detail: 'ดำเนินการลบคำนำหน้าไม่สำเร็จเนื่องจากคำนำหน้าใช้งานอยู่'
                });
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'ข้อความจากระบบ: ',
                  detail: 'ดำเนินการลบคำนำหน้าไม่สำเร็จเนื่องจาก ' + e['error']['errorMessage']
                });
              }
            }
          );
      },
      reject: () => {
        this.messageService.clear();
      }
    });
  }

  /**
   * ตรวจสอบค่าที่รับเข้ามาใหม่ในกรณีกรอกข้อมูลไม่ครบถ้วน
   */
  subscribeInputMessageWaring() {
    this.titlesForm
      .valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged()
      ).subscribe(() => this.waringMessage());
    this.waringMessage();
  }

  /**
   * แสดงข้อความแจ้งเตือนเมื่อกรอกข้อมูลไม่ครบ
   */
  waringMessage() {
    if (!this.formError) {
      return;
    }
    for (const field of Object.keys(this.formError)) {
      this.formError[field] = '';
      const control = this.titlesForm.get(field);
      if (control && this.validationMessage[field] && !control.valid) {
        this.formError[field] = this.validationMessage[field].required;
        if (control.hasError('maxlength')) {
          this.formLengthError[field] = '*ข้อความต้องน้อยกว่า 45 ตัวอักษร';
        } else {
          this.formLengthError[field] = '';
        }
      }
    }
  }

  setValidate() {
    Object.keys(this.titlesForm.controls).forEach(key => {
      const control = this.titlesForm.get(key);
      control.clearValidators();
      control.setValidators([Validators.required, Validators.maxLength(45)]);
      control.updateValueAndValidity();
    });
  }
}

