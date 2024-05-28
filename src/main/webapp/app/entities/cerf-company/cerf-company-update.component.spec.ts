/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfCompanyUpdate from './cerf-company-update.vue';
import CerfCompanyService from './cerf-company.service';
import AlertService from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import CompanyService from '@/entities/company/company.service';

type CerfCompanyUpdateComponentType = InstanceType<typeof CerfCompanyUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfCompanySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CerfCompanyUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CerfCompany Management Update Component', () => {
    let comp: CerfCompanyUpdateComponentType;
    let cerfCompanyServiceStub: SinonStubbedInstance<CerfCompanyService>;

    beforeEach(() => {
      route = {};
      cerfCompanyServiceStub = sinon.createStubInstance<CerfCompanyService>(CerfCompanyService);
      cerfCompanyServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          cerfCompanyService: () => cerfCompanyServiceStub,
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
        const wrapper = shallowMount(CerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfCompany = cerfCompanySample;
        cerfCompanyServiceStub.update.resolves(cerfCompanySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfCompanyServiceStub.update.calledWith(cerfCompanySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cerfCompanyServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cerfCompany = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cerfCompanyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cerfCompanyServiceStub.find.resolves(cerfCompanySample);
        cerfCompanyServiceStub.retrieve.resolves([cerfCompanySample]);

        // WHEN
        route = {
          params: {
            cerfCompanyId: '' + cerfCompanySample.id,
          },
        };
        const wrapper = shallowMount(CerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cerfCompany).toMatchObject(cerfCompanySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfCompanyServiceStub.find.resolves(cerfCompanySample);
        const wrapper = shallowMount(CerfCompanyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
