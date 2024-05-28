/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfDetails from './cerf-details.vue';
import CerfService from './cerf.service';
import AlertService from '@/shared/alert/alert.service';

type CerfDetailsComponentType = InstanceType<typeof CerfDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Cerf Management Detail Component', () => {
    let cerfServiceStub: SinonStubbedInstance<CerfService>;
    let mountOptions: MountingOptions<CerfDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cerfServiceStub = sinon.createStubInstance<CerfService>(CerfService);

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
          cerfService: () => cerfServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cerfServiceStub.find.resolves(cerfSample);
        route = {
          params: {
            cerfId: '' + 123,
          },
        };
        const wrapper = shallowMount(CerfDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cerf).toMatchObject(cerfSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfServiceStub.find.resolves(cerfSample);
        const wrapper = shallowMount(CerfDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
