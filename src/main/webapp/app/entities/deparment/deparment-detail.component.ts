import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeparment } from 'app/shared/model/deparment.model';

@Component({
  selector: 'jhi-deparment-detail',
  templateUrl: './deparment-detail.component.html',
})
export class DeparmentDetailComponent implements OnInit {
  deparment: IDeparment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deparment }) => (this.deparment = deparment));
  }

  previousState(): void {
    window.history.back();
  }
}
