import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../shared/service/auth.service';

@Component({
    selector: 'app-topbar',
    templateUrl: './topbar.component.html',
    styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {
    public isLoggedIn: boolean;

    constructor(
        private authService: AuthService,
    ) {
    }

    ngOnInit() {
        this.authService.isLoggedIn().subscribe(res => this.isLoggedIn = res);
    }

    logout() {
        this.authService.logout();
    }
}
