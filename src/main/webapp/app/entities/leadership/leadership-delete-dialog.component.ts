import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeadership } from 'app/shared/model/leadership.model';
import { LeadershipService } from './leadership.service';

@Component({
  templateUrl: './leadership-delete-dialog.component.html',
})
export class LeadershipDeleteDialogComponent {
  leadership?: ILeadership;

  constructor(
    protected leadershipService: LeadershipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leadershipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leadershipListModification');
      this.activeModal.close();
    });
  }
}
