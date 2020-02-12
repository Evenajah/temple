import { Component, OnInit } from '@angular/core';
import { ManageUserService } from 'src/app/shared/service/manage-user.service';
import { MenuItem, MessageService } from 'primeng/api';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {

  public personal: any[];
  public menu: MenuItem[];
  cols: { field: string; header: string; }[];

  constructor(
    private manageUser: ManageUserService,
    private breadCrumbService: BreadcrumbService,
    private router: Router,
    public spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    this.breadCrumbService.setPath([
      { label: 'จัดการสมาชิก', routerLink: '/users' },
    ]);
    this.spinner.show();
    this.manageUser.getAllUsersWithOutImg().pipe(finalize(() => this.spinner.hide())).subscribe(res => {
      if (res['status'] === 'Success') {
        this.personal = res.data;
      }
    });


    this.menu = [
      { label: '', icon: 'pi pi-home', routerLink: '/' },
      { label: 'Manange Locations : จัดการสถานที่' },
    ];

    this.cols = [
      { field: 'name', header: 'name' },
      { field: 'rolename', header: 'rolename' },
      { field: 'contact', header: 'contact' }
    ];
  }

  deleteUser(id) {
    this.spinner.show();
    this.manageUser.deleteUser(id).pipe(finalize(() => this.spinner.hide())).subscribe(res => {
      if (res['status'] === 'Success') {
        const index = this.personal.findIndex(e => e.id === id);
        this.personal = [
          ...this.personal.slice(0, index),
          ...this.personal.slice(index + 1)
        ];
      }
    })
  }
  public onRowSelect(e) {
    this.router.navigate(['/profile', e.data['id']]);
  }
}
