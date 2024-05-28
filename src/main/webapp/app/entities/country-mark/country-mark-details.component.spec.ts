/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CountryMarkDetails from './country-mark-details.vue';
import CountryMarkService from './country-mark.service';
import AlertService from '@/shared/alert/alert.service';

type CountryMarkDetailsComponentType = InstanceType<typeof CountryMarkDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const countryMarkSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CountryMark Management Detail Component', () => {
    let countryMarkServiceStub: SinonStubbedInstance<CountryMarkService>;
    let mountOptions: MountingOptions<CountryMarkDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      countryMarkServiceStub = sinon.createStubInstance<CountryMarkService>(CountryMarkService);

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
          countryMarkService: () => countryMarkServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        countryMarkServiceStub.find.resolves(countryMarkSample);
        route = {
          params: {
            countryMarkId: '' + 123,
          },
        };
        const wrapper = shallowMount(CountryMarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.countryMark).toMatchObject(countryMarkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        countryMarkServiceStub.find.resolves(countryMarkSample);
        const wrapper = shallowMount(CountryMarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
