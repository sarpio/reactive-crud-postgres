import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  apiURL = "http://localhost:8080/api/persons";

  constructor(private http: HttpClient) {
  }


  /**
   * Get persons list from API.
   * @param page Page number (default 0).
   * @param size Records per page (default 10).
   * @param order Sorting order (default 0 as ASC).
   * @param sortBy Sorting column
   * @returns Observable contains response
   */
  getPageableList(
    page: number,
    size: number,
    order: string,
    sortBy: string
  ): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('ascending', order)
      .set('sortBy', sortBy);
    return this.http.get(this.apiURL, {params, observe: 'response'});
  }

  getOne(id: number) {

  }

  postOne() {

  }

  updateOne(id: number, newData: any) {

  }
}
