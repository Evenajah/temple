import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { RegisterComponent } from './register/register.component';
import { RePasswordComponent } from './re-password/re-password.component';

const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent,
        data: {
          urlback: '/auth/login',
          messageback: 'กลับสู่หน้า Login'
        }
      },
      {
        path: 'forget-password',
        component: ForgetPasswordComponent,
        data: {
          urlback: '/auth/login',
          messageback: 'กลับสู่หน้า Login'
        }
      },
      {
        path: 're-password',
        component: RePasswordComponent,
        data: {
          urlback: '/auth/login',
          messageback: 'กลับสู่หน้า Login'
        }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
