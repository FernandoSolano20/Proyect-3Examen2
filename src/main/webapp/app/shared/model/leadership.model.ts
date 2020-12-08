import { Moment } from 'moment';
import { IDeparment } from 'app/shared/model/deparment.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface ILeadership {
  id?: number;
  startDate?: Moment;
  deparment?: IDeparment;
  employee?: IEmployee;
}

export class Leadership implements ILeadership {
  constructor(public id?: number, public startDate?: Moment, public deparment?: IDeparment, public employee?: IEmployee) {}
}
