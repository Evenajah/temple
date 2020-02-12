import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ForgetPassService } from 'src/app/shared/service/forget-pass.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
    selector: 'app-re-password',
    templateUrl: './re-password.component.html',
    styleUrls: ['./re-password.component.css']
})
export class RePasswordComponent implements OnInit {
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
        password: '',
        rePassword: '',
    };

    public validationMessage = {
        password: {
            required: 'รหัสผ่านใหม่*'
        },
        rePassword: {
            required: 'ยืนยันรหัสผ่าน*'
        }
    };

    ngOnInit() {
        this.createForm();
        if (!this.forgetPassService.memberData) {
            this.router.navigate(['/']);
        }
    }

    private createForm() {
        this.form = this.formBuilder.group({
            'password': ['', [Validators.required, Validators.minLength(6)]],
            'rePassword': ['', [Validators.required, Validators.minLength(6)]],
        });
    }
    
    onSubmit(e) {
        this.messageService.clear();
        e.preventDefault();
        const password = this.form.get('password').value;
        const rePassword = this.form.get('rePassword').value;
        if (password !== rePassword) {
            this.messageService.add({
                key: 'alert',
                sticky: true,
                severity: 'warn',
                summary: 'รหัสผ่านไม่ตรงกัน'
            });
        } else if (this.form.invalid) {
            this.messageService.add({
                key: 'alert',
                sticky: true,
                severity: 'warn',
                summary: 'กรุณากรอกข้อมูลให้ครบถ้วน'
            });
            this.subscribeInputMessageWaring();
        } else if (this.form.valid) {
            this.spinner.show();
            this.forgetPassService.changePassword(password).toPromise().then(res => {
                if (res['result'] === 'Success') {
                    if (res['code'] === 200) {
                        // Do something
                        this.messageService.clear();
                        this.messageService.add({
                            key: 'alert',
                            sticky: true,
                            severity: 'success',
                            summary: 'เปลี่ยนรหัสผ่านสำเร็จ'
                        });
                    } else {
                        this.messageService.clear();
                        this.messageService.add({
                            key: 'alert',
                            sticky: true,
                            severity: 'error',
                            summary: 'ระบบขัดข้อง โปรดติดต่อผู้ดูแลระบบ'
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
        }
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

    onConfirm() {
        this.messageService.clear();
    }

}
