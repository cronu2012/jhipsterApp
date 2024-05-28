/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdDetails from './prod-details.vue';
import ProdService from './prod.service';
import AlertService from '@/shared/alert/alert.service';

type ProdDetailsComponentType = InstanceType<typeof ProdDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Prod Management Detail Component', () => {
    let prodServiceStub: SinonStubbedInstance<ProdService>;
    let mountOptions: MountingOptions<ProdDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      prodServiceStub = sinon.createStubInstance<ProdService>(ProdService);

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
          prodService: () => prodServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        prodServiceStub.find.resolves(prodSample);
        route = {
          params: {
            prodId: '' + 123,
          },
        };
        const wrapper = shallowMount(ProdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.prod).toMatchObject(prodSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodServiceStub.find.resolves(prodSample);
        const wrapper = shallowMount(ProdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
