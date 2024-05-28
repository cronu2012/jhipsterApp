/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdUpdate from './prod-update.vue';
import ProdService from './prod.service';
import AlertService from '@/shared/alert/alert.service';

type ProdUpdateComponentType = InstanceType<typeof ProdUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ProdUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Prod Management Update Component', () => {
    let comp: ProdUpdateComponentType;
    let prodServiceStub: SinonStubbedInstance<ProdService>;

    beforeEach(() => {
      route = {};
      prodServiceStub = sinon.createStubInstance<ProdService>(ProdService);
      prodServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          prodService: () => prodServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prod = prodSample;
        prodServiceStub.update.resolves(prodSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodServiceStub.update.calledWith(prodSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        prodServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prod = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        prodServiceStub.find.resolves(prodSample);
        prodServiceStub.retrieve.resolves([prodSample]);

        // WHEN
        route = {
          params: {
            prodId: '' + prodSample.id,
          },
        };
        const wrapper = shallowMount(ProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.prod).toMatchObject(prodSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodServiceStub.find.resolves(prodSample);
        const wrapper = shallowMount(ProdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
