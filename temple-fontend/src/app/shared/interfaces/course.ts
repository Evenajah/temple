import { TransportationTemple } from './transportation-temple';
import { Teacher } from './teacher';
import { Member } from './member';

export interface Course {
  id?: number;
  no?: number;
  name?: string;
  stDate?: Date;
  endDate?: Date;
  detail?: string;
  conditionMin?: number;
  memberId?: number;
  memberFname?: string;
  memberLname?: string;
  locationId?: number;
  locationName?: string;
  transportation?: TransportationTemple;
  status?: string;
  saStatus?: string;
  mhcStatus?: string;
  canRegister?: number;
  teacherList?:Member;
  numberOfMembers?: number;
}
