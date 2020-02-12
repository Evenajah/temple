import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { ForgetPassService } from '../../shared/service/forget-pass.service';
import { debounceTime, distinctUntilChanged, min } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
    selector: 'app-forget-password',
    templateUrl: './forget-password.component.html',
    styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {
    form: FormGroup;
    @Input() formTitle: string;
    @Output() data = new EventEmitter<Object>();
    constructor(
        private forgetPassService: ForgetPassService,
        private formBuilder: FormBuilder,
        public spinner: NgxSpinnerService,
        private messageService: MessageService,
        private router: Router
    ) { }

    public formError = {
        username: '',
        idCard: '',
        phoneNumber: ''
    };

    public validationMessage = {
        username: {
            // detail: 'กรุณากรอก Username',
            required: 'ชื่อผู้ใช้*'
        },
        idCard: {
            required: 'เลขประจำตัวประชาชน*'
        },
        phoneNumber: {
            required: 'เบอร์โทรศัพท์*'
        }
    };

    ngOnInit() {
        this.createForm();
    }

    onSubmit(e) {
        this.messageService.clear();
        e.preventDefault();
        const idCard = this.form.get('idCard').value;
        const username = this.form.get('username').value;
        const phoneNumber = this.form.get('phoneNumber').value;
        if (this.form.invalid) {
            this.messageService.add({
                key: 'alert',
                sticky: true,
                severity: 'warn',
                summary: 'กรุณากรอกข้อมูลให้ครบถ้วน'
            });
            this.subscribeInputMessageWaring();
        } else if (this.form.valid) {
            this.spinner.show();
            this.forgetPassService.getUserForgetInfo(idCard, username, phoneNumber).toPromise().then(res => {
                if (res['result'] === 'Success') {
                    if (res['code'] === 200) {
                        // Do something
                        this.messageService.clear();
                        this.messageService.add({
                            key: 'alert',
                            sticky: true,
                            severity: 'success',
                            summary: 'ยืนยันการเปลี่ยนรหัสผ่าน'
                        });
                        this.forgetPassService.memberData = res['data'];
                    } else if (res['code'] === 204) {
                        this.messageService.clear();
                        this.messageService.add({
                            key: 'alert',
                            sticky: true,
                            severity: 'error',
                            summary: 'ไม่มีชื่อผู้ใช้อยู่ในระบบหรือข้อมูลไม่ตรงกัน'
                        });
                    }
                }
            }).catch(err => {
                // Message Not found user or email in system
                if (err['error']['code'] === 401) {
                    this.messageService.add({
                        key: 'alert',
                        sticky: true,
                        severity: 'error',
                        summary: err['error']['errorMessage']
                    });
                } else {
                    this.messageService.add({
                        key: 'alert',
                        sticky: true,
                        severity: 'error',
                        summary: 'ระบบขัดข้อง โปรดติดต่อผู้ดูแลระบบ'
                    });
                }
            }).finally(() => this.spinner.hide());
            this.data.emit({ idCard, username, phoneNumber });
        }
    }

    onConfirm() {
        this.messageService.clear();
        // this.router.navigate(['/auth/re-password']);
    }

    subscribeInputMessageWaring() {
        this.form.valueChanges
            .pipe(debounceTime(500), distinctUntilChanged())
            .subscribe(() => this.waringMessage());
        this.waringMessage();
    }
    waringMessage() {
        for (const field of Object.keys(this.formError)) {
            this.formError[field] = '';
            const control = this.form.get(field);
            if ((control &&
                !control.valid &&
                this.validationMessage[field].required) &&
                control.hasError) {
                this.formError[field] = this.validationMessage[field].required;
            }
        }
    }

    private createForm() {
        this.form = this.formBuilder.group({
            'idCard': ['', [Validators.required, Validators.minLength(17)]],
            'username': ['', [Validators.required, Validators.minLength(6)]],
            'phoneNumber': ['', [Validators.required, Validators.minLength(8)]],
        });
    }
}
