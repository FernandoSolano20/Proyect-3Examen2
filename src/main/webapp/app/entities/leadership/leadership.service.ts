import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeadership } from 'app/shared/model/leadership.model';

type EntityResponseType = HttpResponse<ILeadership>;
type EntityArrayResponseType = HttpResponse<ILeadership[]>;

@Injectable({ providedIn: 'root' })
export class LeadershipService {
  public resourceUrl = SERVER_API_URL + 'api/leaderships';

  constructor(protected http: HttpClient) {}

  create(leadership: ILeadership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leadership);
    return this.http
      .post<ILeadership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(leadership: ILeadership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leadership);
    return this.http
      .put<ILeadership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILeadership>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILeadership[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(leadership: ILeadership): ILeadership {
    const copy: ILeadership = Object.assign({}, leadership, {
      startDate: leadership.startDate && leadership.startDate.isValid() ? leadership.startDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((leadership: ILeadership) => {
        leadership.startDate = leadership.startDate ? moment(leadership.startDate) : undefined;
      });
    }
    return res;
  }
}
