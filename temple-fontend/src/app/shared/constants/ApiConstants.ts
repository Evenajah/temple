import { environment } from 'src/environments/environment';

const API = environment.api;
export class ApiConstants {
    public static get baseURl(): string {
        return '/TempleAPI/v1';
    }
}
