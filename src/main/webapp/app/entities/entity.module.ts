import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.ExamenEmployeeModule),
      },
      {
        path: 'deparment',
        loadChildren: () => import('./deparment/deparment.module').then(m => m.ExamenDeparmentModule),
      },
      {
        path: 'leadership',
        loadChildren: () => import('./leadership/leadership.module').then(m => m.ExamenLeadershipModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ExamenEntityModule {}
