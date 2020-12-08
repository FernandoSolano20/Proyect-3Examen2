import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ExamenTestModule } from '../../../test.module';
import { DeparmentComponent } from 'app/entities/deparment/deparment.component';
import { DeparmentService } from 'app/entities/deparment/deparment.service';
import { Deparment } from 'app/shared/model/deparment.model';

describe('Component Tests', () => {
  describe('Deparment Management Component', () => {
    let comp: DeparmentComponent;
    let fixture: ComponentFixture<DeparmentComponent>;
    let service: DeparmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamenTestModule],
        declarations: [DeparmentComponent],
      })
        .overrideTemplate(DeparmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeparmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeparmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Deparment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deparments && comp.deparments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
