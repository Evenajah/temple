import {Component, OnInit} from '@angular/core';
import {BreadcrumbService} from '../../service/breadcrumb.service';
import {MenuItem} from 'primeng/api';

@Component({
    selector: 'app-breadcrumb',
    templateUrl: './breadcrumb.component.html',
    styleUrls: ['./breadcrumb.component.css']
})
export class BreadcrumbComponent implements OnInit {
    public home: MenuItem;
    public items: MenuItem[];

    constructor(
        private breadcrumbService: BreadcrumbService,
    ) {
    }

    ngOnInit() {
        this.home = this.breadcrumbService.getHome();
        this.breadcrumbService.getPath().subscribe(res => this.items = res);
    }

}
