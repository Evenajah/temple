import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from '../../shared/service/auth.service';
import { SidebarService } from '../service/sidebar.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  public openSideBar: boolean;
  public modalSidebar: boolean;
  public showCloseIconSidebar: boolean;
  public isLoggedIn: boolean;
  public screenWidth: number;
  public role: String;

  constructor(
    private sidebarService: SidebarService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.setSidebar();
    this.sidebarService.sidebar().subscribe(res => this.openSideBar = res);
    this.authService.isLoggedIn().subscribe(res => this.isLoggedIn = res);
    this.authService.getRole().subscribe(res => this.role = res)

  }

  showSideBar(...role) {
    if (role.includes(this.role)) {
      return true;
    } else {
      return false;
    }
  }

  clickOutsideBar() {
    const data = this.openSideBar;
    this.sidebarService.switchBar(!data);
  }

  clickMenu() {
    if (this.screenWidth > 1024) {
      this.sidebarService.switchBar(true);
    } else {
      this.sidebarService.switchBar(false);
    }
  }

  @HostListener('window:resize', ['$event'])
  setSidebar(event?) {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth > 1024) {
      this.openSideBar = true;
      this.modalSidebar = false;
      this.showCloseIconSidebar = false;
      this.sidebarService.openSideBar();
    } else {
      this.openSideBar = false;
      this.modalSidebar = true;
      this.showCloseIconSidebar = false;
      this.sidebarService.closeSideBar();
    }
  }

  // ngOnDestroy() {
  //   this.sidebarService.destroy();
  // }

  logout(e) {
    e.preventDefault();
    this.authService.logout();
    if (this.screenWidth > 1024) {
      this.sidebarService.switchBar(true);
    } else {
      this.sidebarService.switchBar(false);
    }
  }
}
