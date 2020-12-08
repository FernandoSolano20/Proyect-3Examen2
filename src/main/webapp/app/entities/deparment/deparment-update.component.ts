import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeparment, Deparment } from 'app/shared/model/deparment.model';
import { DeparmentService } from './deparment.service';

@Component({
  selector: 'jhi-deparment-update',
  templateUrl: './deparment-update.component.html',
})
export class DeparmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    state: [null, [Validators.required]],
  });

  constructor(protected deparmentService: DeparmentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deparment }) => {
      this.updateForm(deparment);
    });
  }

  updateForm(deparment: IDeparment): void {
    this.editForm.patchValue({
      id: deparment.id,
      name: deparment.name,
      description: deparment.description,
      state: deparment.state,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deparment = this.createFromForm();
    if (deparment.id !== undefined) {
      this.subscribeToSaveResponse(this.deparmentService.update(deparment));
    } else {
      this.subscribeToSaveResponse(this.deparmentService.create(deparment));
    }
  }

  private createFromForm(): IDeparment {
    return {
      ...new Deparment(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      state: this.editForm.get(['state'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeparment>>): void {
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
}
