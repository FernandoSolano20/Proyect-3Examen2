import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILeadership, Leadership } from 'app/shared/model/leadership.model';
import { LeadershipService } from './leadership.service';
import { IDeparment } from 'app/shared/model/deparment.model';
import { DeparmentService } from 'app/entities/deparment/deparment.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IDeparment | IEmployee;

@Component({
  selector: 'jhi-leadership-update',
  templateUrl: './leadership-update.component.html',
})
export class LeadershipUpdateComponent implements OnInit {
  isSaving = false;
  deparments: IDeparment[] = [];
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    deparment: [],
    employee: [],
  });

  constructor(
    protected leadershipService: LeadershipService,
    protected deparmentService: DeparmentService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leadership }) => {
      if (!leadership.id) {
        const today = moment().startOf('day');
        leadership.startDate = today;
      }

      this.updateForm(leadership);

      this.deparmentService
        .query({ filter: 'leader-is-null' })
        .pipe(
          map((res: HttpResponse<IDeparment[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDeparment[]) => {
          if (!leadership.deparment || !leadership.deparment.id) {
            this.deparments = resBody;
          } else {
            this.deparmentService
              .find(leadership.deparment.id)
              .pipe(
                map((subRes: HttpResponse<IDeparment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDeparment[]) => (this.deparments = concatRes));
          }
        });

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(leadership: ILeadership): void {
    this.editForm.patchValue({
      id: leadership.id,
      startDate: leadership.startDate ? leadership.startDate.format(DATE_TIME_FORMAT) : null,
      deparment: leadership.deparment,
      employee: leadership.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leadership = this.createFromForm();
    if (leadership.id !== undefined) {
      this.subscribeToSaveResponse(this.leadershipService.update(leadership));
    } else {
      this.subscribeToSaveResponse(this.leadershipService.create(leadership));
    }
  }

  private createFromForm(): ILeadership {
    return {
      ...new Leadership(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      deparment: this.editForm.get(['deparment'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeadership>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
