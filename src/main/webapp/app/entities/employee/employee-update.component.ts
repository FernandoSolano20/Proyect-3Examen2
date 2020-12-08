import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IDeparment } from 'app/shared/model/deparment.model';
import { DeparmentService } from 'app/entities/deparment/deparment.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  deparments: IDeparment[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    secondLastName: [null, [Validators.required]],
    sex: [null, [Validators.required]],
    birthdayDate: [null, [Validators.required]],
    entryDate: [null, [Validators.required]],
    position: [null, [Validators.required]],
    salary: [null, [Validators.required, Validators.min(1)]],
    state: [null, [Validators.required]],
    deparment: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected deparmentService: DeparmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      if (!employee.id) {
        const today = moment().startOf('day');
        employee.birthdayDate = today;
        employee.entryDate = today;
      }

      this.updateForm(employee);

      this.deparmentService.query().subscribe((res: HttpResponse<IDeparment[]>) => (this.deparments = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      name: employee.name,
      lastName: employee.lastName,
      secondLastName: employee.secondLastName,
      sex: employee.sex,
      birthdayDate: employee.birthdayDate ? employee.birthdayDate.format(DATE_TIME_FORMAT) : null,
      entryDate: employee.entryDate ? employee.entryDate.format(DATE_TIME_FORMAT) : null,
      position: employee.position,
      salary: employee.salary,
      state: employee.state,
      deparment: employee.deparment,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      secondLastName: this.editForm.get(['secondLastName'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      birthdayDate: this.editForm.get(['birthdayDate'])!.value
        ? moment(this.editForm.get(['birthdayDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      entryDate: this.editForm.get(['entryDate'])!.value ? moment(this.editForm.get(['entryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      position: this.editForm.get(['position'])!.value,
      salary: this.editForm.get(['salary'])!.value,
      state: this.editForm.get(['state'])!.value,
      deparment: this.editForm.get(['deparment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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

  trackById(index: number, item: IDeparment): any {
    return item.id;
  }
}
