import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {AuthService} from '../../shared/service/auth.service';
import {NgxSpinnerService} from 'ngx-spinner';
import {Router} from '@angular/router';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import {MessageService} from 'primeng/api';
import { ForgetPassService } from 'src/app/shared/service/forget-pass.service';


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    public form: FormGroup;
    public formError = {
        username: '',
        password: '',
    };
    public validationMassages = {
        username: {
            required: '*กรุณากรอกชื่อผู้ใช้'
        },
        password: {
            required: '*กรุณากรอกรหัสผ่าน'
        }
    };

    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService,
        private router: Router,
        private spinner: NgxSpinnerService,
        private messageService: MessageService,
        private forgetPassService: ForgetPassService
    ) {
    }

    ngOnInit() {
        this.forgetPassService.memberData = null;
        this.createForm();
    }

    onSubmit(e) {
        this.messageService.clear();
        e.preventDefault();
        const username = this.form.get('username').value;
        const password = this.form.get('password').value;
        if (this.form.valid) {
            this.spinner.show();
            this.authService.login(username, password).toPromise().then(res => {
                // access_token
                if (res['result'] === 'Success') {
                    const accessToken = res['access_token'];
                    localStorage.setItem('access-token', accessToken);
                    this.authService.isLoggedIn().next(true);
                    this.router.navigate(['/']);
                }
            }).catch(err => {
                if (err['error']['code'] == 401){
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
                this.form.setValue({'username': '','password': ''});
            }).finally(() => this.spinner.hide());
        } else {
            this.onValueChange();
        }
    }

    private createForm() {
        this.form = this.formBuilder.group({
            'username': ['', Validators.required],
            'password': ['', Validators.required],
        });

        this.form
            .valueChanges
            .pipe(
                debounceTime(500),
                distinctUntilChanged()
            )
            .subscribe(() => this.onValueChange());
        this.onValueChange();
    }

    onConfirm() {
        this.messageService.clear('alert');
    }

    private onValueChange() {
        if (!this.form) {
            return;
        }

        for (const field of Object.keys(this.formError)) {
            this.formError[field] = '';
            const control = this.form.get(field);
            if (control && !control.valid && control.dirty) {
                const messages = this.validationMassages[field];
                for (const key of Object.keys(control.errors)) {
                    this.formError[field] += messages[key] + ' ';
                }
            }
        }
    }
}
