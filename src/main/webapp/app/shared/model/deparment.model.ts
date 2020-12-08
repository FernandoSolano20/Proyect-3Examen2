import { IEmployee } from 'app/shared/model/employee.model';
import { ILeadership } from 'app/shared/model/leadership.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IDeparment {
  id?: number;
  name?: string;
  description?: string;
  state?: Status;
  employees?: IEmployee[];
  leader?: ILeadership;
}

export class Deparment implements IDeparment {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public state?: Status,
    public employees?: IEmployee[],
    public leader?: ILeadership
  ) {}
}
