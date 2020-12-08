import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ExamenTestModule } from '../../../test.module';
import { DeparmentUpdateComponent } from 'app/entities/deparment/deparment-update.component';
import { DeparmentService } from 'app/entities/deparment/deparment.service';
import { Deparment } from 'app/shared/model/deparment.model';

describe('Component Tests', () => {
  describe('Deparment Management Update Component', () => {
    let comp: DeparmentUpdateComponent;
    let fixture: ComponentFixture<DeparmentUpdateComponent>;
    let service: DeparmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamenTestModule],
        declarations: [DeparmentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DeparmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeparmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeparmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Deparment(123);
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
        const entity = new Deparment();
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
