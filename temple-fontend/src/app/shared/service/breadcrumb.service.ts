import {Injectable} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class BreadcrumbService {

    private items = new BehaviorSubject<MenuItem[]>([]);
    private home: MenuItem = {icon: 'pi pi-home', label: 'Home : หน้าหลัก', routerLink: '/'};

    constructor() {
    }

    setPath(items: MenuItem[]) {
        this.items.next(items);
    }

    getPath(): BehaviorSubject<MenuItem[]> {
        return this.items;
    }

    getHome(): MenuItem {
        return this.home;
    }

    clearPath() {
        this.items.next(null);
    }
}
