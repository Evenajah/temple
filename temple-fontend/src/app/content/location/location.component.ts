import { Component, OnInit } from '@angular/core';
import { Location } from '../../shared/interfaces/location';
import { LocationService } from './location.service';
import { MessageService, MenuItem, ConfirmationService } from 'primeng/api';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  displayDialog: boolean;
  public filterData: any[];
  newLocation: boolean;
  location: Location;
  locations: Location[];
  cols: any[];
  public menu: MenuItem[];

  locationNameEdit: String;

  constructor(
    private locationService: LocationService,
    private messageService: MessageService,
    private breadCrumbService: BreadcrumbService,
    private confirmationService: ConfirmationService,
    public spinner: NgxSpinnerService
  ) {
  }

  ngOnInit() {
    this.breadCrumbService.setPath([
      { label: 'จัดการสถานที่', routerLink: '/location' },
    ]);

    this.getLocation();
    this.cols = [
      { field: 'name', header: 'สถานที่' },
    ];

    this.menu = [
      { label: '', icon: 'pi pi-home', routerLink: '/' },
      { label: 'Manange Locations : จัดการสถานที่' },
    ];
  }

  showDialogToAdd() {
    this.newLocation = true;
    this.location = {};
    this.displayDialog = true;
  }

  save() {
    if (this.locations.findIndex(res => res.name == this.locationNameEdit) < 0) {
      // this.messageService.clear();
      this.location['name'] = this.locationNameEdit;
      this.spinner.show();
      this.locationService.save(this.location).toPromise().then(res => {
        if (res['status'] === 'Success') {
          this.locations.push(res['data']);
          this.messageService.add({
            severity: 'success', summary: 'ข้อความจากระบบ',
            detail: 'ดำเนินการเพิ่ม สถานที่ : ' + res['data']['name'] + ' สำเร็จ'
          });
          this.getLocation();
        } else {
          this.messageService.add({
            severity: 'error', summary: 'ข้อความจากระบบ',
            detail: 'ดำเนินการเพิ่มไม่สำเร็จ เนื่องจากระบบมีข้อผิดพลาด'
          });
        }
      }).finally(() => this.spinner.hide());
    } else {
      this.messageService.add({ severity: 'error', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการเพิ่มไม่สำเร็จ สถานที่ซ้ำ' });
    }
    this.clear();
  }




  clear() {
    this.location = {};
    this.locationNameEdit = '';
    this.displayDialog = false;
  }

  showEdit(id) {
    this.newLocation = false;
    this.location = this.locations.filter(e => e.id === id)[0];
    this.locationNameEdit = this.location['name'];
    this.displayDialog = true;
  }


  /**
   * @param id ;
   * Delete location
   */
  delete(id) {
    this.confirmationService.confirm({
      message: 'ยืนยันการลบ',
      header: 'ข้อความจากระบบ',
      accept: () => {
        // this.messageService.clear();
        const index = this.locations.findIndex(e => e.id === id);
        this.spinner.show();
        this.locationService.delete(id).toPromise()
          .then(res => {
            if (res['status'] === 'Success') {
              this.locations.splice(index, 1);
              this.spinner.hide();
              this.messageService.add({ severity: 'success', summary: 'ข้อความจากระบบ', detail: 'ดำเนินการลบสำเร็จ' });
              this.getLocation();
            } else {
              this.spinner.hide();
              this.messageService.add({
                severity: 'error', summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการลบไม่สำเร็จ เนื่องจากมีการใช้สถานที่นี้อยู่หรือระบบมีข้อผิดพลาด ', life: 8000
              });
            }
          })
          .catch(e => {
            if (e['error']['result'] === 'Fail') {
              if (e['error']['errorMessage'] === 'location is using') {
                this.spinner.hide();
                this.messageService.add({
                  severity: 'error', summary: 'ข้อความจากระบบ',
                  detail: 'ดำเนินการลบไม่สำเร็จ เนื่องจากมีการใช้สถานที่นี้อยู่', life: 8000
                });
              } else {
                this.spinner.hide();
                this.messageService.add({
                  severity: 'error', summary: 'ข้อความจากระบบ',
                  detail: 'ดำเนินการลบไม่สำเร็จ เนื่องจากระบบมีข้อผิดพลาด', life: 8000
                });
              }
            } else {
              this.spinner.hide();
              this.messageService.add({
                severity: 'error', summary: 'ข้อความจากระบบ',
                detail: 'ดำเนินการไม่สำเร็จ เนื่องจากระบบมีข้อผิดพลาด', life: 8000
              });
            }
          }).finally(() => this.spinner.hide());
      },
      reject: () => {
        // this.messageService.add({ severity: 'info', summary: 'ยกเลิกการลบ' });
      }
    });
  }

  getLocation() {
    this.spinner.show();
    this.locationService.getLocation()
      .toPromise().then(res => {
        if (res['status'] === 'Success') {
          this.locations = res['data'];
        }
      }).finally(() => this.spinner.hide());
  }
  // public searchData(event) {
  //   if (!event) {
  //     this.filterData = this.locations;
  //   } else {
  //     this.filterData = this.locations.filter(res => {
  //       return res['name'].trim().toLowerCase().includes(event.trim().toLowerCase());
  //     });
  //   }
  // }

  /**
   * Updata Location
   * Getdata from Formcontrol
   */
  update() {
    // this.messageService.clear();
    if (this.locations.findIndex(res => res.name == this.locationNameEdit) < 0 || this.location['name'] == this.locationNameEdit) {
      this.location['name'] = this.locationNameEdit;
      this.spinner.show();
      this.locationService.update(this.location).toPromise().then(res => {
        if (res['status'] === 'Success') {
          const index = this.locations.findIndex(e => e.id === res['data']['id']);
          this.locations[index].name = res['data']['name'];
          this.spinner.hide();
          this.messageService.add({
            severity: 'success', summary: 'ข้อความจากระบบ',
            detail: 'ดำเนินการแก้ไข สถานที่ : ' + res['data']['name'] + ' สำเร็จ'
          });
        } else {
          this.spinner.hide();
          this.messageService.add({
            severity: 'error', summary: 'ข้อความจากระบบ',
            detail: 'ดำเนินการแก้ไขไม่สำเร็จ เนื่องจากระบบมีข้อผิดพลาด '
          });
        }

      }).finally(() => this.spinner.hide());
      this.clear();
    } else {
      this.messageService.add({
        severity: 'error', summary: 'ข้อความจากระบบ',
        detail: 'ดำเนินการแก้ไขไม่สำเร็จ เนื่องจากสถานที่ซ้ำ'
      });
    }
  }
}
