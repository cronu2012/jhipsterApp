/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FeeProdCerfCompanyUpdate from './fee-prod-cerf-company-update.vue';
import FeeProdCerfCompanyService from './fee-prod-cerf-company.service';
import AlertService from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import CerfService from '@/entities/cerf/cerf.service';
import CompanyService from '@/entities/company/company.service';

type FeeProdCerfCompanyUpdateComponentType = InstanceType<typeof FeeProdCerfCompanyUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const feeProdCerfCompanySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FeeProdCerfCompanyUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('FeeProdCerfCompany Management Update Component', () => {
    let comp: FeeProdCerfCompanyUpdateComponentType;
    let feeProdCerfCompanyServiceStub: SinonStubbedInstance<FeeProdCerfCompanyService>;

    beforeEach(() => {
      route = {};
      feeProdCerfCompanyServiceStub = sinon.createStubInstance<FeeProdCerfCompanyService>(FeeProdCerfCompanyService);
      feeProdCerfCompanyServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          feeProdCerfCompanyService: () => feeProdCerfCompanyServiceStub,
          prodService: () =>
            sinon.createStubInstance<ProdService>(ProdService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          cerfService: () =>
            sinon.createStubInstance<CerfService>(CerfService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          companyService: () =>
            sinon.createStubInstance<CompanyService>(CompanyService, {
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
        const wrapper = shallowMount(FeeProdCerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.feeProdCerfCompany = feeProdCerfCompanySample;
        feeProdCerfCompanyServiceStub.update.resolves(feeProdCerfCompanySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(feeProdCerfCompanyServiceStub.update.calledWith(feeProdCerfCompanySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        feeProdCerfCompanyServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FeeProdCerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.feeProdCerfCompany = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(feeProdCerfCompanyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        feeProdCerfCompanyServiceStub.find.resolves(feeProdCerfCompanySample);
        feeProdCerfCompanyServiceStub.retrieve.resolves([feeProdCerfCompanySample]);

        // WHEN
        route = {
          params: {
            feeProdCerfCompanyId: '' + feeProdCerfCompanySample.id,
          },
        };
        const wrapper = shallowMount(FeeProdCerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.feeProdCerfCompany).toMatchObject(feeProdCerfCompanySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        feeProdCerfCompanyServiceStub.find.resolves(feeProdCerfCompanySample);
        const wrapper = shallowMount(FeeProdCerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
