import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ManageRoleService {

  constructor(
    private authservice: AuthService
  ) { }

  getRoleStatus() {
    return this.authservice.getRole().getValue() === "admin" ? true : false;
  }

  getUserRoleStatus() {
    return this.authservice.getRole().getValue() === 'user' ? true : false;
  }

  getRoles() {
    //return this.authservice.getRole().getValue();
    if (this.authservice.getRole().getValue() === "admin") {
      return [
        {
          roleId: 3,
          roleName: "user"
        },
        {
          roleId: 2,
          roleName: "monk"
        },
        {
          roleId: 1,
          roleName: "admin"
        },
      ]
    } else {
      return [
        {
          roleId: null,
          roleName: ''
        }
      ]
    }
  }
}
