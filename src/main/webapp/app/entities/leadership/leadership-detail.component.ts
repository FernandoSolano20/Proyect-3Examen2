import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeadership } from 'app/shared/model/leadership.model';

@Component({
  selector: 'jhi-leadership-detail',
  templateUrl: './leadership-detail.component.html',
})
export class LeadershipDetailComponent implements OnInit {
  leadership: ILeadership | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leadership }) => (this.leadership = leadership));
  }

  previousState(): void {
    window.history.back();
  }
}
