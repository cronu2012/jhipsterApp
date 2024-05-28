/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FeeProdCerfCompanyDetails from './fee-prod-cerf-company-details.vue';
import FeeProdCerfCompanyService from './fee-prod-cerf-company.service';
import AlertService from '@/shared/alert/alert.service';

type FeeProdCerfCompanyDetailsComponentType = InstanceType<typeof FeeProdCerfCompanyDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const feeProdCerfCompanySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('FeeProdCerfCompany Management Detail Component', () => {
    let feeProdCerfCompanyServiceStub: SinonStubbedInstance<FeeProdCerfCompanyService>;
    let mountOptions: MountingOptions<FeeProdCerfCompanyDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      feeProdCerfCompanyServiceStub = sinon.createStubInstance<FeeProdCerfCompanyService>(FeeProdCerfCompanyService);

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
          feeProdCerfCompanyService: () => feeProdCerfCompanyServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        feeProdCerfCompanyServiceStub.find.resolves(feeProdCerfCompanySample);
        route = {
          params: {
            feeProdCerfCompanyId: '' + 123,
          },
        };
        const wrapper = shallowMount(FeeProdCerfCompanyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.feeProdCerfCompany).toMatchObject(feeProdCerfCompanySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        feeProdCerfCompanyServiceStub.find.resolves(feeProdCerfCompanySample);
        const wrapper = shallowMount(FeeProdCerfCompanyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
