import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment as env } from '../../environments/environment';
import { UserResponse } from '../model/UserResponse';
import { User } from '../model/User';
import { Message } from '../model/Message';

@Injectable({
  providedIn: 'root'
})
export class UserService {

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
  getAllPageable(
    page: number,
    size: number,
    order: string,
    sortBy: string,
  ): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('ascending', order)
      .set('sortBy', sortBy)
    return this.http.get<HttpResponse<UserResponse>>
    (env.apiURL, {params, observe: 'response'});
  }

  getSingle(id: number): Observable<User> {
    return this.http.get<User>
    (env.apiURL + `/${id}`);
  }

  addUser(user: User) {
    return this.http.post<User>(env.apiURL, user);
  }

  updateUser(user: User) {
    return this.http.put<User>(env.apiURL + `/${user.id}`, user);
  }

  deleteUser(id: number): Observable<string> {
    return this.http.delete<string>(env.apiURL + `/${id}`);
  }

  loadData(value: number): Observable<Message> {
    return this.http.get<Message>(env.apiURL + `/load/${value}`);
  }
}
