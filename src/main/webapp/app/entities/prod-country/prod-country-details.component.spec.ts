/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdCountryDetails from './prod-country-details.vue';
import ProdCountryService from './prod-country.service';
import AlertService from '@/shared/alert/alert.service';

type ProdCountryDetailsComponentType = InstanceType<typeof ProdCountryDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodCountrySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ProdCountry Management Detail Component', () => {
    let prodCountryServiceStub: SinonStubbedInstance<ProdCountryService>;
    let mountOptions: MountingOptions<ProdCountryDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      prodCountryServiceStub = sinon.createStubInstance<ProdCountryService>(ProdCountryService);

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
          prodCountryService: () => prodCountryServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        prodCountryServiceStub.find.resolves(prodCountrySample);
        route = {
          params: {
            prodCountryId: '' + 123,
          },
        };
        const wrapper = shallowMount(ProdCountryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.prodCountry).toMatchObject(prodCountrySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodCountryServiceStub.find.resolves(prodCountrySample);
        const wrapper = shallowMount(ProdCountryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
