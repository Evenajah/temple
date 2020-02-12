import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import {
  Validators,
  FormBuilder,
  FormGroup,
  FormControl
} from '@angular/forms';
import {
  MessageService,
  MenuItem,
  ConfirmationService,
  Message,
  SelectItem
} from 'primeng/api';
import { TitleNameService } from 'src/app/shared/service/title-name.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ManageUserService } from 'src/app/shared/service/manage-user.service';
import { BreadcrumbService } from '../../shared/service/breadcrumb.service';
import { ManageRoleService } from 'src/app/shared/service/manage-role.service';
import { Role } from 'src/app/shared/interfaces/role';
import { AuthService } from '../../shared/service/auth.service';
import { ProvinceService } from 'src/app/shared/service/province.service';
import { HistoryDharmaService } from 'src/app/shared/service/history-dharma.service';
import { Ng2ImgMaxService } from 'ng2-img-max';
import { DomSanitizer } from '@angular/platform-browser';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.css']
})
export class EditFormComponent implements OnInit {
  public menu: MenuItem[];
  public titleName: any[];
  public th: any;
  public yearRange: string;
  public detailWarning: any[] = [];
  public registerSuccess: boolean;
  public showCancelMessage: boolean;
  public personalId: string;
  public previewImg: any = null;
  public onEdit: boolean;
  public pipe = new DatePipe('th-TH');
  public showRole: boolean;
  public roles: Role[];
  public msgs: Message[] = [];
  public urlback: string;
  public messageback: string;
  public filteredTitleName: any[];
  public displaySystemMessage = false;
  public filteredProvince: any[];
  public courseHisName = '';
  public courseHisLocation = '';
  public courseHisList: any[] = [];
  public provinces: any[];
  public courseHisDelList: any[] = [];

  // เรียกเอาไปใช้ใน calendar html
  year = new Date().getFullYear();


  showNoProfile = false;
  showLoadingPicture = true;
  currentId = 0;
  profile: any;
  profileString: any = null;
  titleNames: any[];
  bloodGroup: SelectItem[] = [
    { label: 'O', value: 'O' },
    { label: 'A', value: 'A' },
    { label: 'B', value: 'B' },
    { label: 'AB', value: 'AB' }
  ];

  editForm = new FormGroup({
    idCard: new FormControl(null, [Validators.required]),
    titleName: new FormControl('', [Validators.required]),
    fname: new FormControl(null, [Validators.required]),
    lname: new FormControl(null, [Validators.required]),
    job: new FormControl(null),
    gender: new FormControl(null, [Validators.required]),
    age: new FormControl(null, [Validators.required, Validators.min(0)]),
    address: new FormControl(null, [Validators.required]),
    province: new FormControl(null, [Validators.required]),
    postalCode: new FormControl(null, [
      Validators.required,
      Validators.pattern('[0-9]{5}')
    ]),
    ordianDate: new FormControl(null),
    ordianNumber: new FormControl(null),
    phone: new FormControl(null, [Validators.required]),
    email: new FormControl(null, [Validators.email]),
    phoneEmergency: new FormControl(null, [Validators.required]),
    fnameEmergency: new FormControl(null, [Validators.required]),
    lnameEmergency: new FormControl(null, [Validators.required]),
    relationshipEmergency: new FormControl(null, [Validators.required]),
    historyDharma: new FormControl(null),
    other: new FormControl(null),
    foodsAllergy: new FormControl(null),
    drugsAllergy: new FormControl(null),
    underlyDisease: new FormControl(null),
    blood: new FormControl('', [Validators.required]),
    role: new FormControl(null),
    imgProfile: new FormControl(null)
  });

  public formError = {
    username: '',
    password: '',
    repassword: '',
    idCard: '',
    role: '',
    titleName: '',
    age: '',
    fname: '',
    lname: '',
    phone: '',
    address: '',
    province: '',
    postalCode: '',
    blood: '',
    fnameEmergency: '',
    lnameEmergency: '',
    relationshipEmergency: '',
    phoneEmergency: '',
  };

  public validationMessage = {
    username: {
      detail: 'กรุณากรอก ชื่อผู้ใช้',
      required: 'ชื่อผู้ใช้*'
    },
    password: {
      detail: 'กรุณากรอก รหัสผ่าน',
      required: 'รหัสผ่าน*'
    },
    repassword: {
      detail: 'กรุณากรอก ยืนยันรหัสผ่าน',
      required: 'ยืนยันรหัสผ่าน*'
    },
    idCard: {
      detail: 'กรุณากรอก เลขประจำตัวประชาชน',
      required: 'เลขประจำตัวประชาชน*'
    },
    role: {
      detail: 'กรุณาระบุ สิทธิการใช้งาน',
      required: 'สิทธิการใช้งาน*'
    },
    titleName: {
      detail: 'กรุณาระบุ คำนำหน้า',
      required: 'คำนำหน้า*'
    },
    age: {
      detail: 'กรุณากรอก อายุ',
      required: 'อายุ*'
    },
    fname: {
      detail: 'กรุณากรอก ชื่อ',
      required: 'ชื่อ*'
    },
    lname: {
      detail: 'กรุณากรอก นามสกุล',
      required: 'นามสกุล*'
    },
    phone: {
      detail: 'กรุณากรอก เบอร์โทร',
      required: 'เบอร์โทรศัพท์*'
    },
    address: {
      detail: 'กรุณากรอก ที่อยู่',
      required: 'ที่อยู่*'
    },
    province: {
      detail: 'กรุณาระบุ จังหวัด',
      required: 'จังหวัด*'
    },
    postalCode: {
      detail: 'กรุณากรอก รหัสไปรษณีย์',
      required: 'รหัสไปรษณีย์*'
    },
    blood: {
      detail: 'กรุณาระบุ กรุ๊ปเลือด',
      required: 'กรุ๊ปเลือด*'
    },
    fnameEmergency: {
      detail: 'กรุณากรอก ชื่อผู้ติดต่อฉุกเฉิน',
      required: 'ชื่อผู้ติดต่อฉุกเฉิน*'
    },
    lnameEmergency: {
      detail: 'กรุณากรอก นามสกุลผู้ติดต่อฉุกเฉิน',
      required: 'นามสกุลผู้ติดต่อฉุกเฉิน*'
    },
    relationshipEmergency: {
      detail: 'กรุณากรอก ความสัมพันธ์กับผู้ติดต่อฉุกเฉิน',
      required: 'ความสัมพันธ์*'
    },
    phoneEmergency: {
      detail: 'กรุณากรอก เบอร์ติดต่อฉุกเฉิน',
      required: 'เบอร์ติดต่อฉุกเฉิน*'
    },
  };

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private titleService: TitleNameService,
    private router: Router,
    private manageUserService: ManageUserService,
    private route: ActivatedRoute,
    private breadCrumbService: BreadcrumbService,
    private confirmationService: ConfirmationService,
    private roleService: ManageRoleService,
    private authService: AuthService,
    private provinceService: ProvinceService,
    private historyDharmaService: HistoryDharmaService,
    private ng2ImgMax: Ng2ImgMaxService,
    public sanitizer: DomSanitizer,
    public spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    this.settingCalendarTH();
    this.showRole = this.roleService.getRoleStatus();
    this.roles = this.roleService.getRoles();
    this.personalId = this.route.snapshot.paramMap.get('id');
    this.setBack();
    this.registerSuccess = false;
    this.showCancelMessage = false;
    this.onEdit = false;
    this.settingForm();
    this.historyDharmaService.getHistoryDharmaByMemberId(this.personalId).subscribe(
      res => {
        if (res.status === 'Success') {
          this.courseHisList = res.data;
        }
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );
    this.provinceService.getProvince().subscribe(
      res => {
        this.provinces = res.data;
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );
    this.titleService.getTitleNames().subscribe(
      res => {
        this.titleNames = res;
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );

    if (this.authService.getRole().value === 'admin') {
      this.breadCrumbService.setPath([
        { label: 'จัดการสมาชิก', routerLink: '/users' },
        { label: 'แก้ไขข้อมูลส่วนตัว' }
      ]);
    } else {
      this.breadCrumbService.setPath([
        {
          label: 'ข้อมูลส่วนตัว',
          routerLink: ['/profile', localStorage.getItem('userId')]
        },
        { label: 'แก้ไขข้อมูลส่วนตัว' }
      ]);
    }
  }

  addCourseHis() {
    this.courseHisName = '';
    this.courseHisLocation = '';
    const his = {
      courseName: this.courseHisName,
      courseLocation: this.courseHisLocation
    };
    this.courseHisList.push(his);
  }

  delHisCourse(index, id) {
    this.courseHisList.splice(index, 1);
    this.courseHisDelList.push({ 'id': id });
  }

  setBack() {
    const route =
      this.authService.getRole().value === 'admin' ? '' : this.personalId;
    this.urlback = this.route.snapshot.data.urlback + route;
    this.messageback = this.route.snapshot.data.messageback;
  }

  settingForm() {
    this.spinner.show();
    this.courseHisDelList = [];
    this.historyDharmaService.getHistoryDharmaByMemberId(this.personalId).toPromise()
      .then(res => {
        if (res.status === 'Success') {
          this.courseHisList = res.data;
        }
      }
      ).catch(err => {
        console.log(err['error']['errorMessage']);
      }
      ).finally(() => this.spinner.hide());
    this.spinner.show();
    this.manageUserService.getUser(this.personalId).toPromise()
      .then(res => {
        const titlename = {
          id: res['data']['titleId'],
          display: res['data']['titleDisplay'],
          name: res['data']['titleName']
        };
        const role = {
          roleId: res['data']['roleId'],
          roleName: res['data']['roleName']
        };
        const blood = {
          label: res['data']['blood'],
          value: res['data']['blood']
        };
        const province = {
          provinceId: res['data']['provinceId'],
          provinceName: res['data']['provinceName']
        };
        let emerName = res['data']['emergencyName'];
        if (res['data']['emergencyName'] !== null) {
          emerName = emerName.split(' ', 2);
          this.editForm.controls['fnameEmergency'].setValue(emerName[0]);
          this.editForm.controls['lnameEmergency'].setValue(emerName[1]);
        } else {
          this.editForm.controls['fnameEmergency'].setValue(null);
          this.editForm.controls['lnameEmergency'].setValue(null);
        }
        let ordianDate = res['data']['ordianDate'];
        if (res['data']['ordianDate'] !== null) {
          ordianDate = new Date(ordianDate);
          this.editForm.controls['ordianDate'].patchValue(ordianDate);
        } else {
          this.editForm.controls['ordianDate'].patchValue(null);
        }
        if (res['data']['ordianNumber'] !== null) {
          this.editForm.controls['ordianNumber'].patchValue(
            res['data']['ordianNumber']
          );
        } else {
          this.editForm.controls['ordianNumber'].patchValue(null);
        }

        this.editForm.get('role').patchValue(role);
        this.editForm.get('titleName').patchValue(titlename);
        this.editForm.get('fname').setValue(res['data']['fname']);

        this.editForm.controls['lname'].setValue(res['data']['lname']);
        this.editForm.controls['job'].setValue(res['data']['job']);
        this.editForm.controls['gender'].setValue(res['data']['genderId']);
        this.editForm.controls['phone'].setValue(res['data']['tel']);
        this.editForm.controls['email'].setValue(res['data']['email']);
        this.editForm.controls['address'].setValue(res['data']['address']);
        this.editForm.controls['phoneEmergency'].setValue(
          res['data']['emergencyTel']
        );
        this.editForm.controls['imgProfile'].setValue(res['data']['img']);
        this.editForm.controls['relationshipEmergency'].setValue(
          res['data']['emergencyRelationship']
        );
        this.editForm.controls['other'].setValue(res['data']['other']);
        this.editForm.controls['foodsAllergy'].setValue(
          res['data']['allergyFood']
        );
        this.editForm.controls['drugsAllergy'].setValue(
          res['data']['allergyMedicine']
        );
        this.editForm.controls['underlyDisease'].setValue(
          res['data']['disease']
        );
        this.editForm.controls['blood'].patchValue(blood);
        this.editForm.controls['idCard'].patchValue(res['data']['idCard']);
        this.editForm.controls['age'].patchValue(res['data']['age']);
        this.editForm.controls['postalCode'].patchValue(
          res['data']['postalCode']
        );
        this.editForm.controls['province'].patchValue(province);
      }
      ).catch(err => {
        console.log(err['error']['errorMessage']);
        this.messageService.add({
          severity: 'error',
          summary: 'ข้อความจากระบบ',
          detail: 'ระบบขัดข้อง โปรดติดต่อผู้ดูแลระบบ'
        });
      }).finally(() => this.spinner.hide());
  }

  settingCalendarTH() {
    this.th = {
      firstDayOfWeek: 1,
      dayNames: [
        'อาทิตย์',
        'จันทร์',
        'อังคาร',
        'พุธ',
        'พฤหัสบดี',
        'ศุกร์',
        'เสาร์'
      ],
      dayNamesShort: [
        'อาทิต',
        'จัน',
        'อังคาร',
        'พุธ',
        'พฤหัส',
        'ศุกร์',
        'เสาร์'
      ],
      dayNamesMin: ['อา', 'จ', 'อ', 'พ', 'พฤ', 'ศ', 'ส'],
      monthNames: [
        'มกราคม',
        'กุมภาพันธ์',
        'มีนาคม',
        'เมษายน',
        'พฤษภาคม',
        'มิถุนายน',
        'กรกฎาคม',
        'สิงหาคม',
        'กันยายน',
        'ตุลาคม',
        'พฤศจิกายน',
        'ธันวาคม'
      ],
      monthNamesShort: [
        'มกรา',
        'กุมภา',
        'มีนา',
        'เมษา',
        'พฤษภา',
        'มิถุนา',
        'กรกฎา',
        'สิงหา',
        'กันยา',
        'ตุลา',
        'พฤศจิกา',
        'ธันวา'
      ],
      monthNamesMin: [
        'ม.ค.',
        'ก.พ.',
        'มี.ค.',
        'เม.ย.',
        'พ.ค.',
        'มิ.ย.',
        'ก.ค.',
        'ส.ค.',
        'ก.ย.',
        'ต.ค.',
        'พ.ย.',
        'ธ.ค.'
      ],
      today: 'Today',
      clear: 'Clear'
    };
    this.th = {
      firstDayOfWeek: 1,
      dayNamesMin: ['อา', 'จ', 'อ', 'พ', 'พฤ', 'ศ', 'ส'],
      monthNames: [
        'มกราคม ',
        'กุมภาพันธ์ ',
        'มีนาคม ',
        'เมษายน ',
        'พฤษภาคม  ',
        'มิถุนายน ',
        'กรกฎาคม ',
        'สิงหาคม ',
        'กันยายน ',
        'ตุลาคม ',
        'พฤศจิกายน ',
        'ธันวาคม '
      ],
      today: 'Today',
      clear: 'Clear'
    };

    // const currentYear = new Date().toLocaleString('en-Us',{timeZone:'Asia/Bangkok'})
    // const aestTime = new Date(currentYear)
    // const currentYear = parseInt(formatDate(Date.now(),'yyyy','th'))
    const currentYear = this.pipe.transform(Date.now(), 'yyyy', 'th');
    const startYear = parseInt(currentYear) - 100;
    this.yearRange = startYear + ':' + currentYear;
  }


  onSubmit(e) {
    let temp = 0;
    this.courseHisList.filter(e => {
      if (e.courseName === '' || e.courseName === null || e.location === '' || e.location === null) {
        return temp = 1;
      }
    });
    if (!this.editForm.valid) {
      this.subscribeInputMessageWaring();
      this.showMessageWrongValidate();
    } else if (temp === 1) {
      this.detailWarning = []
      const details = 'กรุณากรอกข้อมูลการปฏิบัติธรรมที่ผ่านมาให้ครบถ้วน';
      this.detailWarning.push(details);
      this.showMessageWrongValidate();
    } else {
      this.submitMessage(e);
    }
  }

  showMessageWrongValidate() {
    this.displaySystemMessage = true;
    // this.showToast('systemMessage', this.detailWarning);
  }

  submitMessage(e) {
    const message = 'ยืนยันการแก้ไขข้อมูลส่วนตัว ?';
    const type = 'submit';
    this.showDialog(message, type);
  }
  onEditprofile() {
    this.registerSuccess = true;
    this.router.navigateByUrl(this.urlback + this.personalId);
  }

  onCancel() {
    this.router.navigateByUrl(this.urlback + this.personalId);
  }

  onReject() {
    if (this.registerSuccess) {
      this.router.navigateByUrl(this.urlback + this.personalId);
    }
    this.messageService.clear('systemMessage');
    this.showCancelMessage = false;
  }

  showCancelConfirm() {
    let message;
    if (this.authService.getRole().value === 'admin') {
      message = 'ยกเลิกการแก้ไขข้อมูลส่วนตัว และกลับสู่หน้าจัดการสมาชิก ?';
    } else {
      message = 'ยกเลิกการแก้ไขข้อมูลส่วนตัว และกลับสู่หน้าข้อมูลส่วนตัว ?';
    }
    const type = 'cancle';
    this.showDialog(message, type);
  }

  // ---------------- Profile Picture Fuction => Start. --------------
  profileSelect(event, field) {
    this.showNoProfile = true;
    this.showLoadingPicture = false;
    this.currentId = field;
    const fileList: FileList = event.target.files;
    const pattern = /image-*/;
    if (fileList.length > 0) {
      const file: File = fileList[0];
      // ------------------- Type File Check => Start. -----------------
      if (!file.type.match(pattern)) {
        this.showNoProfile = false;
        this.showLoadingPicture = true;
        this.messageService.add({
          severity: 'error',
          summary: 'ข้อความจากระบบ',
          detail: 'ไฟล์ผิดประเภท!'
        });
        return;
      }
      // -------------------------------------------------------------
      // --------- Check size and Resize to Image => Start. ----------
      else if (file.size < 2.5 * 1024 * 1024) {
        this.ng2ImgMax.resizeImage(file, 400, 300).subscribe(result => {
          this.profile = result;
          this.handleInputChange(this.profile); // turn into base64
        });
      } else {
        this.showNoProfile = false;
        this.showLoadingPicture = true;
        this.messageService.add({
          severity: 'error',
          summary: 'ข้อความจากระบบ',
          detail: 'ไฟล์เกินขนาด!'
        });
      }
    } else if (fileList.length === 0) {
      this.showNoProfile = false;
      this.showLoadingPicture = true;
    }
    // ---------------------------------------------------------------
  }
  // ---------------------------------------------------------------

  // ----------- Upload Profile to Display Function => Start. ----------

  handleInputChange(files) {
    const file = files;
    const reader = new FileReader();
    reader.onloadend = this._handleReaderLoaded.bind(this);
    reader.onload = () => {
      this.showNoProfile = false;
      this.showLoadingPicture = true;
      this.previewImg = reader.result;
    };
    reader.readAsDataURL(file);
  }
  // เปลี่ยนรูปจาก image เป็น base64 แล้ว post ลงฐานข้อมูล ในรูปแบบ String เป็น Type Blob
  _handleReaderLoaded(e) {
    const reader = e.target;
    const base64result = reader.result.substr(reader.result.indexOf(',') + 1);
    // this.imageSrc = base64result;
    const id = this.currentId;
    if (id === 1) {
      this.profileString = base64result;
      this.editForm.controls['imgProfile'].setValue(this.profileString);
    }
  }

  subscribeInputMessageWaring() {
    this.editForm.valueChanges
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(() => this.waringMessage());
    this.waringMessage();
  }

  waringMessage() {
    if (!this.editForm) {
      return;
    }
    this.detailWarning = [];
    let details: string;
    for (const field of Object.keys(this.formError)) {
      this.formError[field] = '';
      const control = this.editForm.get(field);
      if (control && !control.valid) {
        // details = 'กรุณากรอกข้อมูลให้ครบถ้วน';
        // this.detailWarning[1] = details;
        this.detailWarning.push(this.validationMessage[field].detail);
        this.formError[field] = this.validationMessage[field].required;
      }
    }
  }

  showClearConfirm() {
    const message = 'คืนค่าการแก้ไขทั้งหมด';
    const type = 'clear';
    this.showDialog(message, type);
  }

  showDialog(message, type) {
    this.confirmationService.confirm({
      message: message,
      header: 'ข้อความจากระบบ',
      accept: () => {
        this.actionAccept(type);
      },
      reject: () => { }
    });
  }

  actionAccept(type) {
    switch (type) {
      case 'clear': {
        this.settingForm();
        this.messageService.add({
          severity: 'success',
          summary: 'ข้อความจากระบบ',
          detail: 'ดำเนินการคืนค่าข้อมูลส่วนตัวสำเร็จ'
        });
        break;
      }
      case 'cancle': {
        if (this.authService.getRole().value === 'admin') {
          this.router.navigateByUrl(`/users`);
        } else {
          this.router.navigateByUrl(`/profile/${this.personalId}`);
        }
        break;
      }
      case 'submit': {
        this.spinner.show();
        /**
         * process delete data course history
         */
        /*** Delete ***/
        this.historyDharmaService.delHistoryDharmaById(this.courseHisDelList).subscribe();
        /**
         * Deconstruct Data
         */
        const provinceCode = this.editForm.get('province').value;
        const titleCode = this.editForm.get('titleName').value;
        const role = this.editForm.get('role').value;
        const emerName =
          this.editForm.get('fnameEmergency').value +
          ' ' +
          this.editForm.get('lnameEmergency').value;
        const bloodGroup = this.editForm.get('blood').value;
        const dataUser = {
          fname: this.editForm.get('fname').value,
          lname: this.editForm.get('lname').value,
          age: this.editForm.get('age').value,
          idCard: this.editForm.get('idCard').value,
          job: this.editForm.get('job').value === '' ? null : this.editForm.get('job').value,
          address: this.editForm.get('address').value,
          provinceId: parseInt(provinceCode.provinceId),
          postalCode: this.editForm.get('postalCode').value,
          tel: this.editForm.get('phone').value,
          ordianDate: this.editForm.get('ordianDate').value === '' ? null : this.editForm.get('ordianDate').value,
          ordianNumber: this.editForm.get('ordianNumber').value === '' ? null : this.editForm.get('ordianNumber').value,
          emergencyTel: this.editForm.get('phoneEmergency').value,
          email: this.editForm.get('email').value === '' ? null : this.editForm.get('email').value,
          img: this.editForm.get('imgProfile').value,
          registerDate: null,
          lastUpdate: new Date(),
          genderId: this.editForm.get('gender').value,
          titleId: +titleCode.id,
          roleId: +role.roleId,
          historyDharma: this.courseHisList,
          emergencyName: emerName,
          emergencyRelationship: this.editForm.get('relationshipEmergency').value,
          other: this.editForm.get('other').value === '' ? null : this.editForm.get('other').value,
          allergyFood: this.editForm.get('foodsAllergy').value === '' ? null : this.editForm.get('foodsAllergy').value,
          allergyMedicine: this.editForm.get('drugsAllergy').value === '' ? null : this.editForm.get('drugsAllergy').value,
          disease: this.editForm.get('underlyDisease').value === '' ? null : this.editForm.get('underlyDisease').value,
          blood: bloodGroup.value
        };
        this.manageUserService.updateUser(this.personalId, dataUser).toPromise()
          .then(res => {
            if (res['status'] === 'Success') {
              this.showToast(
                'alertMessage',
                'แก้ไขข้อมูลส่วนตัวสำเร็จ',
                'success'
              );
            } else {
              this.showToast(
                'alertMessage',
                'แก้ไขข้อมูลส่วนตัวไม่สำเร็จ',
                'error'
              );
            }
          }).catch(
            err => {
              this.showToast(
                'alertMessage',
                'แก้ไขข้อมูลส่วนตัวไม่สำเร็จ',
                'error'
              );
            }
          ).finally(() => this.spinner.hide());
        break;
      }
      default: {
        this.spinner.hide();
        break;
      }
    }
  }

  showToast(key, detail, severity) {
    this.messageService.clear();
    this.messageService.add({
      severity: severity,
      key: key,
      sticky: true,
      summary: 'ข้อความจากระบบ',
      detail: detail
    });
  }

  /**
   * รับค่าจากแป้นพิมพ์
   * @param event ;
   */
  filterTitleNameMultiple(event) {
    const query = event.query;
    this.filteredTitleName = this.filterTitleName(query, this.titleNames);
  }
  /**
   * รับค่าจากแป้นพิมพ์
   * @param event ;
   */
  filterProvinceMultiple(event) {
    const query = event.query;
    this.filteredProvince = this.filterProvince(query, this.provinces);
  }

  /**
   * เปรียบเทียบค่าที่ได้จากแป้นพิมพ์ กับ ค่าที่ได้จากดาต้าเบส
   * @param query ;
   * @param titleNames ;
   */
  filterTitleName(query, titleNames: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < titleNames.length; i++) {
      const titleName = titleNames[i];
      if (titleName.display.match(query)) {
        filtered.push(titleName);
      }
    }
    return filtered;
  }

  filterProvince(query, provinces: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < provinces.length; i++) {
      const province = provinces[i];
      if (province.provinceName.match(query)) {
        filtered.push(province);
      }
    }
    return filtered;
  }
}
