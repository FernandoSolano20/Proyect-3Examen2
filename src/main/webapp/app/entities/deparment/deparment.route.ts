import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeparment, Deparment } from 'app/shared/model/deparment.model';
import { DeparmentService } from './deparment.service';
import { DeparmentComponent } from './deparment.component';
import { DeparmentDetailComponent } from './deparment-detail.component';
import { DeparmentUpdateComponent } from './deparment-update.component';

@Injectable({ providedIn: 'root' })
export class DeparmentResolve implements Resolve<IDeparment> {
  constructor(private service: DeparmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeparment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deparment: HttpResponse<Deparment>) => {
          if (deparment.body) {
            return of(deparment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Deparment());
  }
}

export const deparmentRoute: Routes = [
  {
    path: '',
    component: DeparmentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.deparment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeparmentDetailComponent,
    resolve: {
      deparment: DeparmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.deparment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeparmentUpdateComponent,
    resolve: {
      deparment: DeparmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.deparment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeparmentUpdateComponent,
    resolve: {
      deparment: DeparmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'examenApp.deparment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
