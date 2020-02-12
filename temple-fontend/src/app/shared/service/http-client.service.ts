import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class HttpClientService {

  constructor(
    private http: HttpClient
  ) {
  }

  private getHeader() {
    return {
      Authorization: `Bearer ${localStorage.getItem('access-token')}`
    };
  }

  get(url) {
    const header = this.getHeader();
    return this.http.get(url, {
      headers: header
    });
  }

  post(url, data) {
    const header = this.getHeader();
    return this.http.post(url, data, {
      headers: header
    });
  }


  put(url, data) {
    const header = this.getHeader();
    return this.http.put(url, data, {
      headers: header
    });
  }

  patch(url, data) {
    const header = this.getHeader();
    return this.http.patch(url, data, {
      headers: header
    });
  }


  delete(url) {
    const header = this.getHeader();
    return this.http.delete(url, {
      headers: header
    });
  }
}
