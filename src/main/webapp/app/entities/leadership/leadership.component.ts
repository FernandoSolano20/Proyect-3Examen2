import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeadership } from 'app/shared/model/leadership.model';
import { LeadershipService } from './leadership.service';
import { LeadershipDeleteDialogComponent } from './leadership-delete-dialog.component';

@Component({
  selector: 'jhi-leadership',
  templateUrl: './leadership.component.html',
})
export class LeadershipComponent implements OnInit, OnDestroy {
  leaderships?: ILeadership[];
  eventSubscriber?: Subscription;

  constructor(protected leadershipService: LeadershipService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.leadershipService.query().subscribe((res: HttpResponse<ILeadership[]>) => (this.leaderships = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLeaderships();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILeadership): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeaderships(): void {
    this.eventSubscriber = this.eventManager.subscribe('leadershipListModification', () => this.loadAll());
  }

  delete(leadership: ILeadership): void {
    const modalRef = this.modalService.open(LeadershipDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leadership = leadership;
  }
}
