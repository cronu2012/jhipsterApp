/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CountryStdDetails from './country-std-details.vue';
import CountryStdService from './country-std.service';
import AlertService from '@/shared/alert/alert.service';

type CountryStdDetailsComponentType = InstanceType<typeof CountryStdDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const countryStdSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CountryStd Management Detail Component', () => {
    let countryStdServiceStub: SinonStubbedInstance<CountryStdService>;
    let mountOptions: MountingOptions<CountryStdDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      countryStdServiceStub = sinon.createStubInstance<CountryStdService>(CountryStdService);

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
          countryStdService: () => countryStdServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        countryStdServiceStub.find.resolves(countryStdSample);
        route = {
          params: {
            countryStdId: '' + 123,
          },
        };
        const wrapper = shallowMount(CountryStdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.countryStd).toMatchObject(countryStdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        countryStdServiceStub.find.resolves(countryStdSample);
        const wrapper = shallowMount(CountryStdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
