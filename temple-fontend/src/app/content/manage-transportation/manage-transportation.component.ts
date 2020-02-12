import { Component, OnInit } from '@angular/core';
import { TransportService } from 'src/app/shared/service/transport.service';
import { ConfirmationService, Message, MessageService } from 'primeng/api';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { Transportation } from 'src/app/shared/interfaces/transportation';
import { combineLatest, pipe } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { finalize } from 'rxjs/operators';

@Component({
    selector: 'app-manage-transportation',
    templateUrl: './manage-transportation.component.html',
    styleUrls: ['./manage-transportation.component.scss']
})
export class ManageTransportationComponent implements OnInit {
    public displayDialog = false;
    // public filterData: any[];
    public transport: Transportation[]; // to diaplay data on table
    public transportTemple: Transportation[];
    public transportation: Transportation;
    public cols: any[];
    public colsTemple: any[];
    public displayTransportation = false;
    public newTransportation = '';
    public timePickUp = null;
    public timeSend = null;
    public temp: string;
    public tabIndex = 0;
    public typeTran: any;
    public status = true;

    constructor(
        private breadCrumbService: BreadcrumbService,
        private confirmationService: ConfirmationService,
        private transportationService: TransportService,
        private messageService: MessageService,
        public spinner: NgxSpinnerService
    ) { }

    ngOnInit() {
        this.breadCrumbService.setPath([
            { label: 'จัดการการเดินทางทั้งหมด', routerLink: '/transportation' }
        ]);
        this.getTransportation();
        this.getTransportationTemple();
        this.initTransportation();
        this.cols = [
            { field: 'name', header: 'ประเภทการเดินทาง' },
        ];
        this.colsTemple = [
            { field: 'name', header: 'ประเภทการเดินทาง' },
            { field: 'timePickUp', header: 'เวลารับ' },
            { field: 'timeSend', header: 'เวลาส่ง' }
        ];
    }

    onTabChange(event) {
        this.tabIndex = event.index;
    }

    showDialogToAdd() {
        this.displayTransportation = true;
        this.displayDialog = true;
        this.newTransportation = '';
    }
    getTransportation() {
        this.spinner.show();
        this.transportationService.getTranSportToEdit().toPromise()
            .then(res => {
                this.transport = res['data'];
            }).catch(err => {
                console.log(err['error']['errorMessage']);
            }).finally(() =>
                this.spinner.hide()
            );
        // combineLatest for process 2 service before subscribe
        // combineLatest(
        //   this.transportationService.getTranSportToEdit(),
        //   this.transportationService.getTranSportTempleToEdit()
        // ).subscribe(
        //   ([tranSport , tranSportTemple]) => {
        //     this.transport = [...tranSport.data , ...tranSportTemple.data];
        //   }
        // );
    }
    getTransportationTemple() {
        this.spinner.show();
        this.transportationService.getTranSportTemple(null)
            .pipe(finalize(() => this.spinner.hide()))
            .subscribe(
                res => {
                    this.transportTemple = res['data'];
                    this.transportTemple = this.transportTemple.map(data => {
                        return { id: data.id, name: data.name, timePickUp: data.timePickUp, timeSend: data.timeSend }
                    });
                });
    }

    public save() {
        this.transportation.name = this.newTransportation;
        if (this.timePickUp !== null && this.timeSend !== null) {
            this.transportation.timePickUp = new Date(this.timePickUp).getTime();
            this.transportation.timeSend = new Date(this.timeSend).getTime();
        }
        const checkArry = this.transport.filter(
            res => res.name === this.transportation.name
        );
        if (checkArry.length !== 0) {
            this.messageService.add({
                severity: 'error',
                summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการเพิ่มไม่สำเร็จ เนื่องจากข้อมูลซ้ำ'
            });
            return;
        }
        this.spinner.show();
        this.transportationService
            .createTransportation(this.transportation)
            .pipe(finalize(() => this.spinner.hide()))
            .subscribe(
                res => {
                    if (res['status'] === 'Success') {
                        this.spinner.hide();
                        this.messageService.add({
                            severity: 'success',
                            summary: 'ข้อความจากระบบ',
                            detail:
                                'ดำเนินการเพิ่มการเดินทาง: ' + res['data']['name'] + ' สำเร็จ'
                        });
                        this.getTransportation();
                        this.getTransportationTemple();
                        this.initTransportation();
                    }
                },
                e => {
                    this.spinner.hide();
                    this.messageService.add({
                        severity: 'error',
                        summary: 'ข้อความจากระบบ',
                        detail: 'ดำเนินการไม่สำเร็จ'
                    });
                }
            );
        this.clear();
    }

    update() {
        if (this.tabIndex === 0) {
            this.typeTran = this.transportTemple;
        } else {
            this.typeTran = this.transport;
        }
        // this.transportation.name = this.newTransportation;
        if (
            this.typeTran.findIndex(res => res.name === this.newTransportation) <
            0 ||
            this.transportation.name === this.newTransportation
        ) {
            // this.transportation.status = this.status;

            if (this.tabIndex === 0) {
                this.spinner.show();
                this.transportation.name = this.newTransportation;
                this.transportation.timePickUp = this.timePickUp;
                this.transportation.timeSend = this.timeSend;
                this.transportationService
                    .updateTransportationTemple(this.transportation)
                    .pipe(finalize(() => this.spinner.hide()))
                    .subscribe(
                        res => {
                            if (res['status'] === 'Success') {
                                this.spinner.hide();
                                this.messageService.add({
                                    severity: 'success',
                                    summary: 'ข้อความจากระบบ',
                                    detail: 'ดำเนินการแก้ไขสำเร็จ'
                                });
                                const index = this.transportTemple.findIndex(
                                    e => e.id === res['data']['id']
                                );

                                this.transportTemple[index] = res['data'];
                                // this.filterData[index] = res["data"];
                            }
                        },
                        e => {
                            this.spinner.hide();
                            this.messageService.add({
                                severity: 'error',
                                summary: 'ข้อความจากระบบ',
                                detail: 'ดำเนินการแก้ไขไม่สำเร็จ'
                            });
                        }
                    );
            } else {
                this.transportation.name = this.newTransportation;
                this.transportation.timePickUp = null;
                this.transportation.timeSend = null;
                this.spinner.show();
                this.transportationService.updateTransportation(this.transportation)
                    .pipe(finalize(() => this.spinner.hide()))
                    .subscribe(
                        res => {
                            if (res['status'] === 'Success') {
                                this.spinner.hide();
                                this.messageService.add({
                                    severity: 'success',
                                    summary: 'ข้อความจากระบบ',
                                    detail: 'ดำเนินการแก้ไขสำเร็จ'
                                });
                                const index = this.transport.findIndex(
                                    e => e.id === res['data']['id']
                                );

                                this.transport[index] = res['data'];
                                // this.filterData[index] = res["data"];
                            }
                        },
                        e => {
                            this.spinner.hide();
                            this.messageService.add({
                                severity: 'error',
                                summary: 'ข้อความจากระบบ',
                                detail: 'ดำเนินการแก้ไขไม่สำเร็จ'
                            });
                        }
                    );
            }

            this.clear();
        } else {
            // this.messageService.clear;
            this.messageService.add({
                severity: 'error',
                summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการไม่สำเร็จ เนื่องจากข้อมูลซ้ำ'
            });
        }
    }

    delete(id) {
        if (this.tabIndex === 0) {
            this.typeTran = this.transportTemple;
        } else {
            this.typeTran = this.transport;
        }
        this.confirmationService.confirm({
            message:
                'ยืนยันการลบข้อมูล : ' +
                this.typeTran.filter(res => res.id === id)[0].name,
            header: 'ข้อความจากระบบ',
            accept: () => {
                this.spinner.show();
                if (this.tabIndex === 0) {
                    this.transportationService.deleteTransportationTemple(id)
                        .pipe(finalize(() => this.spinner.hide())).subscribe(
                            res => {
                                if (res['status'] === 'Success') {
                                    this.spinner.hide();
                                    this.messageService.add({
                                        severity: 'success',
                                        summary: 'ข้อความจากระบบ',
                                        detail: 'ดำเนินการลบสำเร็จ'
                                    });
                                    this.getTransportationTemple();
                                }
                            },
                            (e) => {
                                if (e['error']['errorMessage'] === 'transportationTemple is using') {
                                    this.spinner.hide();
                                    this.messageService.add({
                                        severity: 'error',
                                        summary: 'ข้อความจากระบบ: ',
                                        detail: 'ดำเนินการลบการเดินทางของวัดไม่สำเร็จเนื่องจากการเดินทางของวัดถูกใช้งานอยู่'
                                    });
                                } else {
                                    this.spinner.hide();
                                    this.messageService.add({
                                        severity: 'error',
                                        summary: 'ข้อความจากระบบ: ',
                                        detail: 'ดำเนินการลบคำนำหน้าไม่สำเร็จเนื่องจาก ' + e['error']['errorMessage']
                                    });
                                }
                            }
                        );
                } else {
                    this.transportationService.deleteTransportation(id)
                        .pipe(finalize(() => this.spinner.hide())).subscribe(
                            res => {
                                this.spinner.hide();
                                if (res['status'] === 'Success') {
                                    this.messageService.add({
                                        severity: 'success',
                                        summary: 'ข้อความจากระบบ',
                                        detail: 'ดำเนินการลบสำเร็จ'
                                    });
                                    this.getTransportation();
                                }
                            },
                            (e) => {
                                console.log(e['error']['errorMessage']);
                                if (e['error']['errorMessage'] === 'transportation is using') {
                                    this.spinner.hide();
                                    this.messageService.add({
                                        severity: 'error',
                                        summary: 'ข้อความจากระบบ: ',
                                        detail: 'ดำเนินการลบการเดินทางด้วยตัวเองไม่สำเร็จเนื่องจากการเดินทางด้วยตัวเองถูกใช้งานอยู่'
                                    });
                                } else {
                                    this.spinner.hide();
                                    this.messageService.add({
                                        severity: 'error',
                                        summary: 'ข้อความจากระบบ: ',
                                        detail: 'ดำเนินการลบคำนำหน้าไม่สำเร็จเนื่องจาก ' + e['error']['errorMessage']
                                    });
                                }
                            }
                        );
                }
            }
        });
        this.clear();
    }

    showEdit(id) {
        this.displayDialog = true;
        this.displayTransportation = false;
        if (this.tabIndex === 0) {
            this.typeTran = this.transportTemple;
        } else {
            this.typeTran = this.transport;
        }
        this.transportation = this.typeTran.filter(e => e.id === id)[0];
        this.newTransportation = this.transportation.name;
        this.timePickUp = new Date(this.transportation.timePickUp);
        this.timeSend = new Date(this.transportation.timeSend);
        this.temp = this.transportation.name;
    }

    clear() {
        this.initTransportation();
        this.newTransportation = '';
        // this.messageService.clear();
    }
    private initTransportation() {
        this.displayDialog = false;
        this.transportation = {
            id: null,
            name: ''
        };
        this.timePickUp = null;
        this.timeSend = null;
    }
}
