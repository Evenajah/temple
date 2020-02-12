import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CourseComponent } from './courses/course/course.component';
import { ContentComponent } from './content.component';
import { BaggagesComponent } from './baggages/baggages.component';
import { CoursesComponent } from './courses/courses.component';
import { CoursesListComponent } from './courses/courses-list/courses-list.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { LocationComponent } from './location/location.component';
import { ManagedTitlenameComponent } from './managed-titlename/managed-titlename.component';
import { AuthGuard } from '../shared/guard/auth.guard';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { EditFormComponent } from './edit-form/edit-form.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterFormComponent } from '../auth/register-form/register-form.component';
import { ApprovalComponent } from './approval/approval.component';
import { ManageCourseComponent } from './manage-course/manage-course.component';
import { ManagePassCourseComponent } from './manage-pass-course/manage-pass-course.component';
import { ManagePassCourseFormComponent } from './manage-pass-course-form/manage-pass-course-form.component';
import { CourseCreateComponent } from './courses/course-create/course-create.component';
import { CourseEditComponent } from './courses/course-edit/course-edit.component';
import { ApprovalFormComponent } from './approval/approval-form/approval-form.component';
import { ManageStorageComponent } from './manage-storage/manage-storage.component';
import { ManageCourseForMonkComponent } from './manage-course-for-monk/manage-course-for-monk.component';
import { ManageTransportationComponent } from './manage-transportation/manage-transportation.component'
import { CourseRegisterOutTimeComponent } from './courses/course-register-out-time/course-register-out-time.component';
import { ReportComponent } from './report/report.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { NotificationComponent } from './notification/notification.component';
import { NotificationDetailComponent } from './notification/notification-detail/notification-detail.component';

const routes: Routes = [
  {
    path: '',
    component: ContentComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: HomeComponent,
      },
      {
        path: 'courses',
        component: CoursesComponent,
        children: [
          {
            path: '',
            component: CoursesListComponent,
          },
          {
            path: ':id',
            component: CourseComponent,
          }
        ]
      },
      {
        path: 'baggages',
        component: BaggagesComponent,
      },
      {
        path: 'profile',
        children: [
          {
            path: ':id',
            component: ProfileComponent,
          },
          {
            path: ':id/edit',
            component: EditFormComponent,
            data: {
              urlback: '/profile/',
              messageback: 'กลับสู่หน้าข้อมูลส่วนตัว'
            }
          },
        ]
      },
      {
        path: 'schedule',
        component: ScheduleComponent,
      },
      {
        path: 'location',
        component: LocationComponent,
      },
      {
        path: 'manageTitlename',
        component: ManagedTitlenameComponent,
      },
      {
        path: 'users',
        children: [
          {
            path: '',
            component: ManageUserComponent,
          },
          {
            path: 'create',
            component: RegisterFormComponent,
            data: {
              urlback: '/users',
              messageback: 'กลับสู่หน้าจัดการผู้ใช้'
            }
          },
          {
            path: ':id/edit',
            component: EditFormComponent,
            data: {
              urlback: '/users',
              messageback: 'กลับสู่หน้าจัดการผู้ใช้'
            }
          },
        ]
      },
      {
        path: 'approval',
        children: [
          {
            path: '',
            component: ApprovalComponent,
          },
          {
            path: ':id',
            component: ApprovalFormComponent,
            data: {
              urlback: '/approval/',
              messageback: 'อนุมัติพิเศษ'
            }
          },
        ]
      },
      {
        path: 'approvalCourseOutTime',
        children: [
          {
            path: '',
            component: ApprovalComponent,
          },
          {
            path: ':id',
            component: ApprovalFormComponent,
            data: {
              urlback: '/approvalCourseOutTime/',
              messageback: 'กลับสู่หน้าอนุมัตินอกเวลา'
            }
          },
        ]
      }, {
        path: 'storage',
        component: ManageStorageComponent
      },
      {
        path: 'manageCourse',
        component: ManageCourseComponent
      },
      {
        path: 'managepasscourse/:id',
        component: ManagePassCourseFormComponent,
        data: {
          urlback: '/managepasscourse/',
          messageback: 'อนุมัติการผ่านหลักสูตร'
        }
      },
      {
        path: 'managepasscourse',
        component: ManagePassCourseComponent,
      },
      {
        path: 'createCourse',
        component: CourseCreateComponent
      }
      ,
      {
        path: 'editCourse/:id',
        component: CourseEditComponent
      },
      {
        path: 'manageCourseForMonk',
        component: ManageCourseForMonkComponent
      },
      {
        path: 'transportation',
        component: ManageTransportationComponent
      },
      {
        path: 'courseOutTime',
        component: CourseRegisterOutTimeComponent
      },
      {
        path: 'report',
        component: ReportComponent
      },{
        path: 'dashboard',
        component: DashboardComponent
      },{
        path: 'notification',
        component: NotificationDetailComponent
      }
    ]
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContentRoutingModule {
}
