export interface Notifications {
    id?: string;
    courseID: number;
    detail: string;
    memberID: number;
    notificationStatus: number;
    notificationTime: firebase.firestore.Timestamp;
    specialApproveID: number;
    specialApproveStatus: string;
}

