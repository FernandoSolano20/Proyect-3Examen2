import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ExamenTestModule } from '../../../test.module';
import { LeadershipComponent } from 'app/entities/leadership/leadership.component';
import { LeadershipService } from 'app/entities/leadership/leadership.service';
import { Leadership } from 'app/shared/model/leadership.model';

describe('Component Tests', () => {
  describe('Leadership Management Component', () => {
    let comp: LeadershipComponent;
    let fixture: ComponentFixture<LeadershipComponent>;
    let service: LeadershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamenTestModule],
        declarations: [LeadershipComponent],
      })
        .overrideTemplate(LeadershipComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeadershipComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeadershipService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Leadership(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.leaderships && comp.leaderships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
