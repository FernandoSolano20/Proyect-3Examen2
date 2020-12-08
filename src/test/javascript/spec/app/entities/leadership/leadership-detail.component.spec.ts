import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExamenTestModule } from '../../../test.module';
import { LeadershipDetailComponent } from 'app/entities/leadership/leadership-detail.component';
import { Leadership } from 'app/shared/model/leadership.model';

describe('Component Tests', () => {
  describe('Leadership Management Detail Component', () => {
    let comp: LeadershipDetailComponent;
    let fixture: ComponentFixture<LeadershipDetailComponent>;
    const route = ({ data: of({ leadership: new Leadership(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamenTestModule],
        declarations: [LeadershipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LeadershipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeadershipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load leadership on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.leadership).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
