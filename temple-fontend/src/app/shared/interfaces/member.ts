import { HistoryDharma } from './historyDharma';

export interface Member {
  id?: Number;
  username?: String;
  password?: String;
  fname: String;
  lname: String;
  address?: String;
  tel?: String;
  emergencyTel?: String;
  email?: String;
  img?: String;
  age?: Number;
  roleId?: Number;
  roleName?: String;
  titleId?: Number;
  titleDisplay?: String;
  titleName?: String;
  ordianNumber?: Number;
  ordianDate?: Date;
  provinceId?: Number;
  provinceName?: String;
  postalCode?: String;
  regionId?: Number;
  regionName?: String;
  genderId?: Number;
  genderName?: String;
  job?: String;
  other?: String;
  blood?: String;
  allergyFood?: String;
  allergyMedicine?: String;
  disease?: String;
  emergencyName?: String;
  emergencyRelationship?: String;
  historydharma?: HistoryDharma;
}
