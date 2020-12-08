import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeparment } from 'app/shared/model/deparment.model';
import { DeparmentService } from './deparment.service';

@Component({
  templateUrl: './deparment-delete-dialog.component.html',
})
export class DeparmentDeleteDialogComponent {
  deparment?: IDeparment;

  constructor(protected deparmentService: DeparmentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deparmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deparmentListModification');
      this.activeModal.close();
    });
  }
}
