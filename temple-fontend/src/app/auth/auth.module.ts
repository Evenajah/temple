import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';

import { AuthRoutingModule } from './auth-routing.module';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { RegisterComponent } from './register/register.component';
import {CalendarModule} from 'primeng/calendar';
import { ThaiCalendarModule } from '../shared/directive/thai-calendar/thai-calendar.module';
import { Ng2ImgMaxModule } from 'ng2-img-max';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { RePasswordComponent } from './re-password/re-password.component';

@NgModule({
  declarations: [
    AuthComponent,
    LoginComponent,
    ForgetPasswordComponent,
    RegisterFormComponent,
    RegisterComponent,
    RePasswordComponent
  ],
  imports: [
    SharedModule,
    AuthRoutingModule,
    CalendarModule,
    ThaiCalendarModule,
    Ng2ImgMaxModule,
    ProgressSpinnerModule
  ],
  exports: [
    AuthComponent
  ]
})
export class AuthModule { }
