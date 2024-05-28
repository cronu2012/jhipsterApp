/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CountryCertDetails from './country-cert-details.vue';
import CountryCertService from './country-cert.service';
import AlertService from '@/shared/alert/alert.service';

type CountryCertDetailsComponentType = InstanceType<typeof CountryCertDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const countryCertSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CountryCert Management Detail Component', () => {
    let countryCertServiceStub: SinonStubbedInstance<CountryCertService>;
    let mountOptions: MountingOptions<CountryCertDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      countryCertServiceStub = sinon.createStubInstance<CountryCertService>(CountryCertService);

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
          countryCertService: () => countryCertServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        countryCertServiceStub.find.resolves(countryCertSample);
        route = {
          params: {
            countryCertId: '' + 123,
          },
        };
        const wrapper = shallowMount(CountryCertDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.countryCert).toMatchObject(countryCertSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        countryCertServiceStub.find.resolves(countryCertSample);
        const wrapper = shallowMount(CountryCertDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
