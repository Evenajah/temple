import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {NgxSpinnerModule} from 'ngx-spinner';
import {ButtonModule} from 'primeng/button';
import {RadioButtonModule} from 'primeng/radiobutton';
import {DropdownModule} from 'primeng/dropdown';
import {TitleNameService} from './service/title-name.service';
import {CardModule} from 'primeng/card';
import {InputTextModule} from 'primeng/inputtext';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {InputMaskModule} from 'primeng/inputmask';
import {CalendarModule} from 'primeng/calendar';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/components/common/messageservice';
import {SidebarModule} from 'primeng/sidebar';
import {PasswordModule} from 'primeng/password';
import {TableModule} from 'primeng/table';
import {StepsModule} from 'primeng/steps';
import {FullCalendarModule} from 'primeng/fullcalendar';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {DialogModule} from 'primeng/dialog';
import {FileUploadModule} from 'primeng/fileupload';
import {CookieService} from 'ngx-cookie-service';
import {BreadcrumbModule} from 'primeng/breadcrumb';
import {CheckboxModule} from 'primeng/checkbox';
import {ListboxModule} from 'primeng/listbox';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {TabViewModule} from 'primeng/tabview';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {library} from '@fortawesome/fontawesome-svg-core';
import {
  faUser,
  faBars,
  faBookOpen,
  faArchive,
  faLock,
  faLandmark,
  faPlus,
  faUsers,
  faAt,
  faHome,
  faSearch,
} from '@fortawesome/free-solid-svg-icons';
import {
  faCalendarAlt,
  faIdCard,
} from '@fortawesome/free-regular-svg-icons';


import {PersonalInfoService} from './service/personal-info.service';
import {BreadcrumbComponent} from './component/breadcrumb/breadcrumb.component';
import {HttpClientService} from './service/http-client.service';

import {PanelModule} from 'primeng/panel';
import {ToggleButtonModule} from 'primeng/togglebutton';
import { ClearAutoCompleteDirective } from './directive/clear-auto-complete.directive';
import { ChartModule } from 'primeng/chart';
@NgModule({
  declarations: [
    BreadcrumbComponent,
    ClearAutoCompleteDirective
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    ButtonModule,
    CardModule,
    DropdownModule,
    InputTextModule,
    RadioButtonModule,
    InputTextareaModule,
    InputMaskModule,
    CalendarModule,
    ToastModule,
    FormsModule,
    FileUploadModule,
    HttpClientModule,
    BreadcrumbModule,
    PanelModule,
    TabViewModule,
    ToggleButtonModule,ChartModule
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    RouterModule,
    ButtonModule,
    BreadcrumbComponent,
    RadioButtonModule,
    DropdownModule,
    CardModule,
    InputTextModule,
    InputTextareaModule,
    InputMaskModule,
    ToastModule,
    SidebarModule,
    PasswordModule,
    FontAwesomeModule,
    TableModule,
    StepsModule,
    FormsModule,
    FullCalendarModule,
    ConfirmDialogModule,
    MessageModule,
    MessagesModule,
    DialogModule,
    FullCalendarModule,
    HttpClientModule,
    NgxSpinnerModule,
    BreadcrumbModule,
    FileUploadModule,
    CalendarModule,
    CheckboxModule,
    ListboxModule,
    AutoCompleteModule,
    PanelModule,
    TabViewModule,
    ToggleButtonModule,
    ClearAutoCompleteDirective,
    ChartModule
  ],
  providers: [
    TitleNameService,
    MessageService,
    PersonalInfoService,
    CookieService,
    HttpClientService,
  ]
})
export class SharedModule {
  constructor() {
    library.add(
      faUser,
      faBars,
      faBookOpen,
      faArchive,
      faLock,
      faCalendarAlt,
      faLandmark,
      faPlus,
      faIdCard,
      faAt,
      faUsers,
      faHome,
      faSearch,
    );
  }
}
