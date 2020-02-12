import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService, ConfirmationService } from 'primeng/api';
import {
  Validators,
  FormBuilder,
  FormGroup,
  FormControl
} from '@angular/forms';
import { formatDate } from '@angular/common';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { TitleNameService } from 'src/app/shared/service/title-name.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ManageUserService } from 'src/app/shared/service/manage-user.service';
import { AuthService } from '../../shared/service/auth.service';
import { BreadcrumbService } from 'src/app/shared/service/breadcrumb.service';
import { SelectItem } from 'primeng/api';
import { TitleName } from 'src/app/shared/interfaces/title-name';
import { Role } from 'src/app/shared/interfaces/role';
import { ManageRoleService } from 'src/app/shared/service/manage-role.service';
import { ProvinceService } from 'src/app/shared/service/province.service';
import { Ng2ImgMaxService } from 'ng2-img-max';
import { DomSanitizer } from '@angular/platform-browser';
import { NgxSpinnerService } from 'ngx-spinner';

interface Transportation {
  memberTransportation: string;
}
@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {
  myDate = new Date();
  public menu: MenuItem[];
  public titleName: any[];
  public th: any;
  public yearRange: string;
  public detailWarning: any[] = [];
  public registerSuccess: boolean;
  public showCancelMessage: boolean;
  public urlback: string;
  public showRole: boolean;
  public messageback: string;
  public filteredTitleName: any[];
  public filteredProvince: any[];
  public roles: Role[];
  showNoProfile: boolean = false;
  showLoadingPicture: boolean = true;
  profileString: string;
  currentId = 0;
  profile: any;
  courseName = '';
  location = '';
  courseHisList: any[] = [];
  bloodGroup: SelectItem[] = [
    { label: 'O', value: 'O' },
    { label: 'A', value: 'A' },
    { label: 'B', value: 'B' },
    { label: 'AB', value: 'AB' }
  ];
  public provinces: any[];
  public titleNames: any[];
  public displaySystemMessage = false;

  registerForm = new FormGroup({
    username: new FormControl(null, [
      Validators.required,
      Validators.minLength(6)
    ]),
    password: new FormControl(null, [
      Validators.required,
      Validators.minLength(6)
    ]),
    repassword: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ]),
    idCard: new FormControl(null, [Validators.required]),
    titleName: new FormControl('', [Validators.required]),
    fname: new FormControl(null, [Validators.required]),
    lname: new FormControl(null, [Validators.required]),
    job: new FormControl(null),
    role: new FormControl(null, [Validators.required]),
    gender: new FormControl('3', [Validators.required]),
    age: new FormControl(null, [Validators.required, Validators.min(0)]),
    address: new FormControl(null, [Validators.required]),
    province: new FormControl(null, [Validators.required]),
    postalCode: new FormControl(null, [
      Validators.required,
      Validators.pattern('[0-9]{5}')
    ]),
    phone: new FormControl(null, [Validators.required]),
    email: new FormControl(null, [Validators.email]),
    ordianDate: new FormControl(null, [Validators.min(0)]),
    ordianNumber: new FormControl(null),
    phoneEmergency: new FormControl(null, [Validators.required]),
    fnameEmergency: new FormControl(null, [Validators.required]),
    lnameEmergency: new FormControl(null, [Validators.required]),
    relationshipEmergency: new FormControl(null, [Validators.required]),
    other: new FormControl(null),
    foodsAllergy: new FormControl(null),
    drugsAllergy: new FormControl(null),
    underlyDisease: new FormControl(null),
    blood: new FormControl('', [Validators.required])
  });

  public formError = {
    username: '',
    idCard: '',
    password: '',
    repassword: '',
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
    age: {
      detail: 'กรุณากรอก อายุ',
      required: 'อายุ*'
    },
    role: {
      detail: 'กรุณาระบุ สิทธิการใช้งาน',
      required: 'สิทธิการใช้งาน*'
    },
    titleName: {
      detail: 'กรุณาระบุ คำนำหน้า',
      required: 'คำนำหน้า*'
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
  previewImg: string | ArrayBuffer;
  constructor(
    private messageService: MessageService,
    private titleService: TitleNameService,
    private router: Router,
    private manageUserService: ManageUserService,
    private route: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private breadCrumbService: BreadcrumbService,
    private roleService: ManageRoleService,
    private provinceService: ProvinceService,
    private ng2ImgMax: Ng2ImgMaxService,
    public sanitizer: DomSanitizer,
    public spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    // ----------------------------------------------
    // const email = this.registerForm.get('email');
    this.provinceService.getProvince().subscribe(
      res => {
        this.provinces = res.data;
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );
    // ------------ Get Role ----------------------------
    this.showRole = this.roleService.getRoleStatus();
    this.roles = this.roleService.getRoles();
    this.breadCrumbService.setPath([
      { label: 'จัดการสมาชิก', routerLink: '/users' },
      { label: 'ลงทะเบียนสมาชิก', routerLink: '/users/create' }
    ]);
    this.urlback = this.route.snapshot.data.urlback;
    this.registerSuccess = false;
    this.showCancelMessage = false;
    this.setBack();
    // this.createForm();
    this.titleService.getTitleNames().subscribe(
      res => {
        this.titleNames = res;
      },
      err => {
        console.log(err['error']['errorMessage']);
      }
    );

    this.menu = [
      { label: 'Login', url: 'auth/login' },
      { label: 'Register : สมัครสมาชิก' }
    ];
  }

  addCourseHis() {
    // this.courseName = '';
    // this.courseLocation = '';
    // const his = { 'courseName': this.courseName, 'courseLocation': this.courseLocation };
    this.courseHisList.push({});
  }

  delHisCourse(index) {
    this.courseHisList.splice(index, 1);
  }

  setBack() {
    this.urlback = this.route.snapshot.data.urlback;
    this.messageback = this.route.snapshot.data.messageback;
  }

  /* createForm() {
    this.registerForm = this.formBuilder.group(
      {
        username: ['', [Validators.required, Validators.minLength(5)]],
        password: ['', [Validators.required, Validators.minLength(5)]],
        repassword: ['', [Validators.required, Validators.minLength(5)]],
        titleName: ['', Validators.required],
        fname: ['', Validators.required],
        lname: ['', Validators.required],
        memberJob: ['', Validators.required],
        gender: ['', Validators.required],
        address: ['', Validators.required],
        phone: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        emergencyTel: ['', Validators.required],
        memberExpected: ['', Validators.required],
        memberCoursePassed: ['', Validators.required],
        memberNote: ['', Validators.required],
        // other: ['', Validators.required],
        memberTransportation: ['', Validators.required],
        memberExpPassed: ['', Validators.required],
      }
    );
  } */
  onSubmit(e) {
    /**
     * Check coursesHistory not blank or null in courses name
     */
    let temp = 0;
    this.courseHisList.filter(e => {
      if (e.courseName === '' || e.courseName === null || e.location === '' || e.location === null) {
        return temp = 1;
      }
    });
    if (!this.registerForm.valid) {
      this.subscribeInputMessageWaring();
      this.showMessage();
    } else if (temp === 1) {
      this.detailWarning = []
      const details = 'กรุณากรอกข้อมูลการปฏิบัติธรรมที่ผ่านมาให้ครบถ้วน';
      this.detailWarning.push(details);
      this.showMessage();
    } else {
      this.submitMessage(e);
    }
  }

  /**
   * ข้อความยืนยันสมัครสมาชิก
   * @param e ;
   */
  submitMessage(e) {
    const message = 'ยืนยันการสมัครสมาชิก';
    const type = 'submit';
    this.showDialog(message, type);
  }

  /**
   * แสดง ConfirmDialog ยืนยันการสมัครสมาชิก
   * @param message ;
   * @param type ;
   */
  showDialog(message, type) {
    this.confirmationService.confirm({
      message: message,
      header: 'ข้อความแจ้งเตือน',
      accept: () => {
        this.actionAccept(type);
      },
      reject: () => { }
    });
  }

  /**
   * create user
   */
  actionAccept(type) {
    switch (type) {
      case 'cancle': {
        this.router.navigateByUrl(this.urlback);
        break;
      }
      case 'submit': {
        this.spinner.show();
        // const dataUser = this.onSave(this.registerForm.getRawValue());
        const provinceCode = this.registerForm.get('province').value;
        const titleCode = this.registerForm.get('titleName').value;
        const emerName =
          this.registerForm.get('fnameEmergency').value +
          ' ' +
          this.registerForm.get('lnameEmergency').value;
        const bloodGroup = this.registerForm.get('blood').value;
        const dataUser = {
          username: this.registerForm.get('username').value,
          password: this.registerForm.get('password').value,
          idCard: this.registerForm.get('idCard').value,
          age: this.registerForm.get('age').value,
          fname: this.registerForm.get('fname').value,
          lname: this.registerForm.get('lname').value,
          job: this.registerForm.get('job').value,
          address: this.registerForm.get('address').value,
          postalCode: this.registerForm.get('postalCode').value,
          provinceId: parseInt(provinceCode.provinceId),
          ordianDate: this.registerForm.get('ordianDate').value,
          ordianNumber: this.registerForm.get('ordianNumber').value,
          tel: this.registerForm.get('phone').value,
          roleId: this.registerForm.get('role').value.roleId,
          emergencyTel: this.registerForm.get('phoneEmergency').value,
          emergencyName: emerName,
          emergencyRelationship: this.registerForm.get('relationshipEmergency')
            .value,
          email: this.registerForm.get('email').value,
          img: this.profileString,
          registerDate: new Date(),
          lastUpdate: null,
          genderId: this.registerForm.get('gender').value,
          titleId: parseInt(titleCode.id),
          historyDharma: this.courseHisList,
          other: this.registerForm.get('other').value,
          allergyFood: this.registerForm.get('foodsAllergy').value,
          allergyMedicine: this.registerForm.get('drugsAllergy').value,
          disease: this.registerForm.get('underlyDisease').value,
          blood: bloodGroup.value
        };
        this.manageUserService.createUser(dataUser).subscribe(
          res => {
            if (res['status'] === 'Success') {
              this.showToast('alertMessage', 'สมัครสมาชิกสำเร็จ', 'success');
            } else {
              if (res['errorMessage'].includes('member_username_UNIQUE')) {
                this.showToast(
                  'alertMessage',
                  'สมัครสมาชิกไม่สำเร็จเนื่องจากชื่อผู้ใช้ซ้ำ',
                  'error'
                );
              } else if (
                res['errorMessage'].includes('member_id_card_UNIQUE')
              ) {
                this.showToast(
                  'alertMessage',
                  'สมัครสมาชิกไม่สำเร็จเนื่องจากเลขที่บัตรประชาชนซ้ำ',
                  'error'
                );
              } else {
                this.showToast('alertMessage', 'สมัครสมาชิกไม่สำเร็จเนื่องจากระบบมีข้อผิดพลาด', 'error');
              }
            }
          },
          err => {
            if (err['error']['errorMessage'].includes('member_username_UNIQUE')) {
              this.showToast(
                'alertMessage',
                'สมัครสมาชิกไม่สำเร็จเนื่องจากชื่อผู้ใช้ซ้ำ',
                'error'
              );
            } else if (
              err['error']['errorMessage'].includes('member_id_card_UNIQUE')
            ) {
              this.showToast(
                'alertMessage',
                'สมัครสมาชิกไม่สำเร็จเนื่องจากเลขที่บัตรประชาชนซ้ำ',
                'error'
              );
            } else {
              this.showToast('alertMessage', 'สมัครสมาชิกไม่สำเร็จเนื่องจากระบบมีข้อผิดพลาด', 'error');
            }
          }
        );
        this.spinner.hide();
        break;
      }
      default: {
        this.spinner.hide();
        break;
      }
    }
  }

  CheckColors(val) {
    const element = document.getElementById('color');
    if (val === 'pick a color' || val === 'others') {
      element.style.display = 'block';
    } else {
      element.style.display = 'none';
    }
  }

  showMessage() {
    this.displaySystemMessage = true;
    // this.showToast('systemMessage', this.detailWarning);
  }

  onReject() {
    if (this.registerSuccess) {
      this.router.navigateByUrl(this.urlback);
    }
    this.showCancelMessage = false;
  }

  showCancel() {
    // this.showMessage('cancel');
    const message = 'ยกเลิกการสมัคร ?';
    const type = 'cancle';
    this.showDialog(message, type);
  }

  /**
   * ตรวจสอบค่าที่รับเข้ามาใหม่ในกรณีกรอกข้อมูลไม่ครบถ้วน
   */
  subscribeInputMessageWaring() {
    this.registerForm.valueChanges
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(() => this.waringMessage());
    this.waringMessage();
  }

  /**
   * ตรวจสอบข้อมูลที่กรอก
   */
  waringMessage() {
    if (!this.registerForm) {
      return;
    }
    this.detailWarning = [];
    let details: string;
    for (const field of Object.keys(this.formError)) {
      this.formError[field] = '';
      const control = this.registerForm.get(field);
      // if (field === 'repassword') {
      //   if (control.value == '') {
      //     this.detailWarning[0] = 'กรุณากรอกยืนยันรหัสผ่าน';
      //   } else if (control.value !== this.registerForm.get('password').value) {
      //     this.detailWarning[0] = 'กรุณากรอกรหัสผ่านให้ตรงกัน';
      //   } else {
      //     continue;
      //   }
      //   this.formError[field] = this.validationMessage[field].required;
      // } else 
      if (
        control &&
        !control.valid &&
        this.validationMessage[field].required
      ) {
        details = 'กรุณากรอกข้อมูลให้ครบถ้วน';
        // this.detailWarning[1] = details;
        this.detailWarning.push(this.validationMessage[field].detail);
        this.formError[field] = this.validationMessage[field].required;
      }
    }
  }

  /**
   * Toast สมัครสมาชิก
   * @param key ;
   * @param detail ;
   */
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
   * clear message diaog
   */
  clearMessageService() {
    this.messageService.clear();
  }

  /**
   * รับค่าจากแป้นพิมพ์
   * @param event
   */
  filterTitleNameMultiple(event) {
    const query = event.query;
    this.filteredTitleName = this.filterTitleName(query, this.titleNames);
  }
  /**
   * รับค่าจากแป้นพิมพ์
   * @param event
   */
  filterProvinceMultiple(event) {
    const query = event.query;
    this.filteredProvince = this.filterProvince(query, this.provinces);
  }
  /**
   * เปรียบเทียบค่าที่ได้จากแป้นพิมพ์ กับ ค่าที่ได้จากดาต้าเบส
   * @param query
   * @param titleNames
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

  onGenderSelect(event: TitleName) {
    const gender = this.registerForm.get('titleName').value;
    // const newObject = {...event};
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
      // ---------------------------------------------------------------
    } else if (fileList.length === 0) {
      this.showNoProfile = false;
      this.showLoadingPicture = true;
    }
  }
  // -------------------------------------------------------------------

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
  // --------------------------------------------------------------------

  // - Put data img Profile to Variable 'profileString' for Sent to database Function => Start. -
  _handleReaderLoaded(e) {
    const reader = e.target;
    const base64result = reader.result.substr(reader.result.indexOf(',') + 1);
    const id = this.currentId;
    if (id === 1) {
      this.profileString = base64result;
    }
  }
  // --------------------------------------------------------------------
}
