/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfStdUpdate from './cerf-std-update.vue';
import CerfStdService from './cerf-std.service';
import AlertService from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import StdService from '@/entities/std/std.service';

type CerfStdUpdateComponentType = InstanceType<typeof CerfStdUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfStdSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CerfStdUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CerfStd Management Update Component', () => {
    let comp: CerfStdUpdateComponentType;
    let cerfStdServiceStub: SinonStubbedInstance<CerfStdService>;

    beforeEach(() => {
      route = {};
      cerfStdServiceStub = sinon.createStubInstance<CerfStdService>(CerfStdService);
      cerfStdServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          cerfStdService: () => cerfStdServiceStub,
          cerfService: () =>
            sinon.createStubInstance<CerfService>(CerfService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          stdService: () =>
            sinon.createStubInstance<StdService>(StdService, {
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
        const wrapper = shallowMount(CerfStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfStd = cerfStdSample;
        cerfStdServiceStub.update.resolves(cerfStdSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfStdServiceStub.update.calledWith(cerfStdSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cerfStdServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CerfStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfStd = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfStdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cerfStdServiceStub.find.resolves(cerfStdSample);
        cerfStdServiceStub.retrieve.resolves([cerfStdSample]);

        // WHEN
        route = {
          params: {
            cerfStdId: '' + cerfStdSample.id,
          },
        };
        const wrapper = shallowMount(CerfStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cerfStd).toMatchObject(cerfStdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfStdServiceStub.find.resolves(cerfStdSample);
        const wrapper = shallowMount(CerfStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
