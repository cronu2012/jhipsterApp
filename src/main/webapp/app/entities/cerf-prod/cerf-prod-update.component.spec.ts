/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfProdUpdate from './cerf-prod-update.vue';
import CerfProdService from './cerf-prod.service';
import AlertService from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import ProdService from '@/entities/prod/prod.service';

type CerfProdUpdateComponentType = InstanceType<typeof CerfProdUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfProdSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CerfProdUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CerfProd Management Update Component', () => {
    let comp: CerfProdUpdateComponentType;
    let cerfProdServiceStub: SinonStubbedInstance<CerfProdService>;

    beforeEach(() => {
      route = {};
      cerfProdServiceStub = sinon.createStubInstance<CerfProdService>(CerfProdService);
      cerfProdServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          cerfProdService: () => cerfProdServiceStub,
          cerfService: () =>
            sinon.createStubInstance<CerfService>(CerfService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          prodService: () =>
            sinon.createStubInstance<ProdService>(ProdService, {
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
        const wrapper = shallowMount(CerfProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfProd = cerfProdSample;
        cerfProdServiceStub.update.resolves(cerfProdSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfProdServiceStub.update.calledWith(cerfProdSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cerfProdServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CerfProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfProd = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfProdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cerfProdServiceStub.find.resolves(cerfProdSample);
        cerfProdServiceStub.retrieve.resolves([cerfProdSample]);

        // WHEN
        route = {
          params: {
            cerfProdId: '' + cerfProdSample.id,
          },
        };
        const wrapper = shallowMount(CerfProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cerfProd).toMatchObject(cerfProdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfProdServiceStub.find.resolves(cerfProdSample);
        const wrapper = shallowMount(CerfProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
