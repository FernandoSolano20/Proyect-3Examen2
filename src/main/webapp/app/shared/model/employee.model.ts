import { Moment } from 'moment';
import { ILeadership } from 'app/shared/model/leadership.model';
import { IDeparment } from 'app/shared/model/deparment.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IEmployee {
  id?: number;
  name?: string;
  lastName?: string;
  secondLastName?: string;
  sex?: string;
  birthdayDate?: Moment;
  entryDate?: Moment;
  position?: string;
  salary?: number;
  state?: Status;
  leaders?: ILeadership[];
  deparment?: IDeparment;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public lastName?: string,
    public secondLastName?: string,
    public sex?: string,
    public birthdayDate?: Moment,
    public entryDate?: Moment,
    public position?: string,
    public salary?: number,
    public state?: Status,
    public leaders?: ILeadership[],
    public deparment?: IDeparment
  ) {}
}
