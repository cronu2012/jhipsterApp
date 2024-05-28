/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfMarkUpdate from './cerf-mark-update.vue';
import CerfMarkService from './cerf-mark.service';
import AlertService from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import MarkService from '@/entities/mark/mark.service';

type CerfMarkUpdateComponentType = InstanceType<typeof CerfMarkUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfMarkSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CerfMarkUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CerfMark Management Update Component', () => {
    let comp: CerfMarkUpdateComponentType;
    let cerfMarkServiceStub: SinonStubbedInstance<CerfMarkService>;

    beforeEach(() => {
      route = {};
      cerfMarkServiceStub = sinon.createStubInstance<CerfMarkService>(CerfMarkService);
      cerfMarkServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          cerfMarkService: () => cerfMarkServiceStub,
          cerfService: () =>
            sinon.createStubInstance<CerfService>(CerfService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          markService: () =>
            sinon.createStubInstance<MarkService>(MarkService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CerfMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfMark = cerfMarkSample;
        cerfMarkServiceStub.update.resolves(cerfMarkSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfMarkServiceStub.update.calledWith(cerfMarkSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cerfMarkServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CerfMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfMark = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfMarkServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cerfMarkServiceStub.find.resolves(cerfMarkSample);
        cerfMarkServiceStub.retrieve.resolves([cerfMarkSample]);

        // WHEN
        route = {
          params: {
            cerfMarkId: '' + cerfMarkSample.id,
          },
        };
        const wrapper = shallowMount(CerfMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cerfMark).toMatchObject(cerfMarkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfMarkServiceStub.find.resolves(cerfMarkSample);
        const wrapper = shallowMount(CerfMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
