/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfCompanyDetails from './cerf-company-details.vue';
import CerfCompanyService from './cerf-company.service';
import AlertService from '@/shared/alert/alert.service';

type CerfCompanyDetailsComponentType = InstanceType<typeof CerfCompanyDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfCompanySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CerfCompany Management Detail Component', () => {
    let cerfCompanyServiceStub: SinonStubbedInstance<CerfCompanyService>;
    let mountOptions: MountingOptions<CerfCompanyDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cerfCompanyServiceStub = sinon.createStubInstance<CerfCompanyService>(CerfCompanyService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          cerfCompanyService: () => cerfCompanyServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cerfCompanyServiceStub.find.resolves(cerfCompanySample);
        route = {
          params: {
            cerfCompanyId: '' + 123,
          },
        };
        const wrapper = shallowMount(CerfCompanyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cerfCompany).toMatchObject(cerfCompanySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfCompanyServiceStub.find.resolves(cerfCompanySample);
        const wrapper = shallowMount(CerfCompanyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
