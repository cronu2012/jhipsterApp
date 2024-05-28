/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfUpdate from './cerf-update.vue';
import CerfService from './cerf.service';
import AlertService from '@/shared/alert/alert.service';

type CerfUpdateComponentType = InstanceType<typeof CerfUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CerfUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Cerf Management Update Component', () => {
    let comp: CerfUpdateComponentType;
    let cerfServiceStub: SinonStubbedInstance<CerfService>;

    beforeEach(() => {
      route = {};
      cerfServiceStub = sinon.createStubInstance<CerfService>(CerfService);
      cerfServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          cerfService: () => cerfServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CerfUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerf = cerfSample;
        cerfServiceStub.update.resolves(cerfSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfServiceStub.update.calledWith(cerfSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cerfServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CerfUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerf = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cerfServiceStub.find.resolves(cerfSample);
        cerfServiceStub.retrieve.resolves([cerfSample]);

        // WHEN
        route = {
          params: {
            cerfId: '' + cerfSample.id,
          },
        };
        const wrapper = shallowMount(CerfUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cerf).toMatchObject(cerfSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfServiceStub.find.resolves(cerfSample);
        const wrapper = shallowMount(CerfUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
