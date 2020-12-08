import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILeadership, Leadership } from 'app/shared/model/leadership.model';
import { LeadershipService } from './leadership.service';
import { LeadershipComponent } from './leadership.component';
import { LeadershipDetailComponent } from './leadership-detail.component';
import { LeadershipUpdateComponent } from './leadership-update.component';

@Injectable({ providedIn: 'root' })
export class LeadershipResolve implements Resolve<ILeadership> {
  constructor(private service: LeadershipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeadership> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((leadership: HttpResponse<Leadership>) => {
          if (leadership.body) {
            return of(leadership.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Leadership());
  }
}

export const leadershipRoute: Routes = [
  {
    path: '',
    component: LeadershipComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.leadership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LeadershipDetailComponent,
    resolve: {
      leadership: LeadershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.leadership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LeadershipUpdateComponent,
    resolve: {
      leadership: LeadershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.leadership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LeadershipUpdateComponent,
    resolve: {
      leadership: LeadershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.leadership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
