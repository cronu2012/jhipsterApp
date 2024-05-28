/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdStdDetails from './prod-std-details.vue';
import ProdStdService from './prod-std.service';
import AlertService from '@/shared/alert/alert.service';

type ProdStdDetailsComponentType = InstanceType<typeof ProdStdDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodStdSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ProdStd Management Detail Component', () => {
    let prodStdServiceStub: SinonStubbedInstance<ProdStdService>;
    let mountOptions: MountingOptions<ProdStdDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      prodStdServiceStub = sinon.createStubInstance<ProdStdService>(ProdStdService);

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
          prodStdService: () => prodStdServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        prodStdServiceStub.find.resolves(prodStdSample);
        route = {
          params: {
            prodStdId: '' + 123,
          },
        };
        const wrapper = shallowMount(ProdStdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.prodStd).toMatchObject(prodStdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodStdServiceStub.find.resolves(prodStdSample);
        const wrapper = shallowMount(ProdStdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
