import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeparment } from 'app/shared/model/deparment.model';

type EntityResponseType = HttpResponse<IDeparment>;
type EntityArrayResponseType = HttpResponse<IDeparment[]>;

@Injectable({ providedIn: 'root' })
export class DeparmentService {
  public resourceUrl = SERVER_API_URL + 'api/deparments';

  constructor(protected http: HttpClient) {}

  create(deparment: IDeparment): Observable<EntityResponseType> {
    return this.http.post<IDeparment>(this.resourceUrl, deparment, { observe: 'response' });
  }

  update(deparment: IDeparment): Observable<EntityResponseType> {
    return this.http.put<IDeparment>(this.resourceUrl, deparment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeparment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeparment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
