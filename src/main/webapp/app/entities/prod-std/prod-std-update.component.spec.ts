/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdStdUpdate from './prod-std-update.vue';
import ProdStdService from './prod-std.service';
import AlertService from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import StdService from '@/entities/std/std.service';

type ProdStdUpdateComponentType = InstanceType<typeof ProdStdUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodStdSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ProdStdUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ProdStd Management Update Component', () => {
    let comp: ProdStdUpdateComponentType;
    let prodStdServiceStub: SinonStubbedInstance<ProdStdService>;

    beforeEach(() => {
      route = {};
      prodStdServiceStub = sinon.createStubInstance<ProdStdService>(ProdStdService);
      prodStdServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          prodStdService: () => prodStdServiceStub,
          prodService: () =>
            sinon.createStubInstance<ProdService>(ProdService, {
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
        const wrapper = shallowMount(ProdStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prodStd = prodStdSample;
        prodStdServiceStub.update.resolves(prodStdSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodStdServiceStub.update.calledWith(prodStdSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        prodStdServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ProdStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prodStd = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodStdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        prodStdServiceStub.find.resolves(prodStdSample);
        prodStdServiceStub.retrieve.resolves([prodStdSample]);

        // WHEN
        route = {
          params: {
            prodStdId: '' + prodStdSample.id,
          },
        };
        const wrapper = shallowMount(ProdStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.prodStd).toMatchObject(prodStdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodStdServiceStub.find.resolves(prodStdSample);
        const wrapper = shallowMount(ProdStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
