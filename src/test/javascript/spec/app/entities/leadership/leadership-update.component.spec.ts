import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ExamenTestModule } from '../../../test.module';
import { LeadershipUpdateComponent } from 'app/entities/leadership/leadership-update.component';
import { LeadershipService } from 'app/entities/leadership/leadership.service';
import { Leadership } from 'app/shared/model/leadership.model';

describe('Component Tests', () => {
  describe('Leadership Management Update Component', () => {
    let comp: LeadershipUpdateComponent;
    let fixture: ComponentFixture<LeadershipUpdateComponent>;
    let service: LeadershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamenTestModule],
        declarations: [LeadershipUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LeadershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeadershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeadershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Leadership(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Leadership();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
