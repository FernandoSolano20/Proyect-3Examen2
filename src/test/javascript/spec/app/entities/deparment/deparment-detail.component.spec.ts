import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExamenTestModule } from '../../../test.module';
import { DeparmentDetailComponent } from 'app/entities/deparment/deparment-detail.component';
import { Deparment } from 'app/shared/model/deparment.model';

describe('Component Tests', () => {
  describe('Deparment Management Detail Component', () => {
    let comp: DeparmentDetailComponent;
    let fixture: ComponentFixture<DeparmentDetailComponent>;
    const route = ({ data: of({ deparment: new Deparment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamenTestModule],
        declarations: [DeparmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeparmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeparmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deparment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deparment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
