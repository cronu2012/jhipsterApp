/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfProdDetails from './cerf-prod-details.vue';
import CerfProdService from './cerf-prod.service';
import AlertService from '@/shared/alert/alert.service';

type CerfProdDetailsComponentType = InstanceType<typeof CerfProdDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfProdSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CerfProd Management Detail Component', () => {
    let cerfProdServiceStub: SinonStubbedInstance<CerfProdService>;
    let mountOptions: MountingOptions<CerfProdDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cerfProdServiceStub = sinon.createStubInstance<CerfProdService>(CerfProdService);

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
          cerfProdService: () => cerfProdServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cerfProdServiceStub.find.resolves(cerfProdSample);
        route = {
          params: {
            cerfProdId: '' + 123,
          },
        };
        const wrapper = shallowMount(CerfProdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cerfProd).toMatchObject(cerfProdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfProdServiceStub.find.resolves(cerfProdSample);
        const wrapper = shallowMount(CerfProdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
