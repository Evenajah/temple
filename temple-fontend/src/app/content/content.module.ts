import { NgModule } from "@angular/core";
import { ContentComponent } from "./content.component";
import { CoursesComponent } from "./courses/courses.component";
import { CourseResgisterComponent } from "./courses/course-resgister/course-resgister.component";
import { CourseFormComponent } from "./courses/course-form/course-form.component";
import { CourseEditComponent } from "./courses/course-edit/course-edit.component";
import { CourseCreateComponent } from "./courses/course-create/course-create.component";
import { CoursesListComponent } from "./courses/courses-list/courses-list.component";
import { CourseComponent } from "./courses/course/course.component";
import { CoreModule } from "../core/core.module";
import { SharedModule } from "../shared/shared.module";
import { ContentRoutingModule } from "./content-routing.module";
import { BaggagesComponent } from "./baggages/baggages.component";
import { ScheduleComponent } from "./schedule/schedule.component";
import { AuthGuard } from "../shared/guard/auth.guard";
import { LocationComponent } from "./location/location.component";
import { ManagedTitlenameComponent } from "./managed-titlename/managed-titlename.component";
import { ConfirmationService } from "primeng/api";
import { ManageUserComponent } from "./manage-user/manage-user.component";
import { EditFormComponent } from "./edit-form/edit-form.component";
import { HomeComponent } from "./home/home.component";
import { ApprovalComponent } from "./approval/approval.component";
import { ManagePassCourseComponent } from "./manage-pass-course/manage-pass-course.component";
import { ManagePassCourseFormComponent } from "./manage-pass-course-form/manage-pass-course-form.component";
import { ManageUserService } from "../shared/service/manage-user.service";
import { ListCourseApproveComponent } from "./approval/list-course-approve/list-course-approve.component";
import { HttpClientService } from "../shared/service/http-client.service";
import { ProfileComponent } from "./profile/profile.component";
import { ManageCourseComponent } from "./manage-course/manage-course.component";
import { LOCALE_ID } from "@angular/core";
import { registerLocaleData } from "@angular/common";
import localeTh from "@angular/common/locales/th";
import { ListAllowComponent } from "./list-allow/list-allow.component";
import { CourseApproveComponent } from "./approval/course-approve/course-approve.component";
import { ApprovalFormComponent } from "./approval/approval-form/approval-form.component";
import { ManageStorageComponent } from "./manage-storage/manage-storage.component";
import { ManageCourseForMonkComponent } from "./manage-course-for-monk/manage-course-for-monk.component";
import { FormatTHdatePipe } from "../shared/pipe/formatTHdate.pipe";
import { ManageTransportationComponent } from "./manage-transportation/manage-transportation.component";
import { CourseRegisterOutTimeComponent } from "./courses/course-register-out-time/course-register-out-time.component";
import { RadioButtonModule } from "primeng/radiobutton";
import { ThaiCalendarModule } from '../shared/directive/thai-calendar/thai-calendar.module';
import { TabMenuModule } from 'primeng/tabmenu';
import { Ng2ImgMaxModule } from 'ng2-img-max';
import { ReportComponent } from './report/report.component';
import { ApproveOutTimeComponent } from './approval/approve-out-time/approve-out-time.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { NotificationComponent } from './notification/notification.component';
import { NotificationDetailComponent } from './notification/notification-detail/notification-detail.component';
import { DialogOutTimeComponent } from './courses/course-register-out-time/dialog-out-time/dialog-out-time.component';


registerLocaleData(localeTh);

@NgModule({
  declarations: [
    ContentComponent,
    BaggagesComponent,
    CoursesComponent,
    CourseResgisterComponent,
    CourseFormComponent,
    CourseEditComponent,
    CourseCreateComponent,
    CoursesListComponent,
    CourseComponent,
    ScheduleComponent,
    LocationComponent,
    ManagedTitlenameComponent,
    ManageUserComponent,
    EditFormComponent,
    HomeComponent,
    ApprovalComponent,
    ProfileComponent,
    ManageCourseComponent,
    ManagePassCourseComponent,
    ManagePassCourseFormComponent,
    ListCourseApproveComponent,
    ListAllowComponent,
    CourseApproveComponent,
    ApprovalFormComponent,
    ManageStorageComponent,
    ManageCourseForMonkComponent,
    FormatTHdatePipe,
    ManageTransportationComponent,
    CourseRegisterOutTimeComponent,
    ReportComponent,
    ApproveOutTimeComponent,
    DashboardComponent,
    NotificationComponent,
    NotificationDetailComponent,
    DialogOutTimeComponent
  ],
  imports: [CoreModule, SharedModule, ContentRoutingModule, RadioButtonModule, ThaiCalendarModule, TabMenuModule, Ng2ImgMaxModule],
  exports: [ContentComponent],
  providers: [
    ConfirmationService,
    AuthGuard,
    ManageUserService,
    HttpClientService,
    { provide: LOCALE_ID, useValue: "th" }
  ]
})
export class ContentModule { }
