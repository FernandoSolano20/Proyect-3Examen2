import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeparment } from 'app/shared/model/deparment.model';
import { DeparmentService } from './deparment.service';
import { DeparmentDeleteDialogComponent } from './deparment-delete-dialog.component';

@Component({
  selector: 'jhi-deparment',
  templateUrl: './deparment.component.html',
})
export class DeparmentComponent implements OnInit, OnDestroy {
  deparments?: IDeparment[];
  eventSubscriber?: Subscription;

  constructor(protected deparmentService: DeparmentService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.deparmentService.query().subscribe((res: HttpResponse<IDeparment[]>) => (this.deparments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeparments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeparment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeparments(): void {
    this.eventSubscriber = this.eventManager.subscribe('deparmentListModification', () => this.loadAll());
  }

  delete(deparment: IDeparment): void {
    const modalRef = this.modalService.open(DeparmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deparment = deparment;
  }
}
