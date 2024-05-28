/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StdDetails from './std-details.vue';
import StdService from './std.service';
import AlertService from '@/shared/alert/alert.service';

type StdDetailsComponentType = InstanceType<typeof StdDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stdSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Std Management Detail Component', () => {
    let stdServiceStub: SinonStubbedInstance<StdService>;
    let mountOptions: MountingOptions<StdDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      stdServiceStub = sinon.createStubInstance<StdService>(StdService);

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
          stdService: () => stdServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stdServiceStub.find.resolves(stdSample);
        route = {
          params: {
            stdId: '' + 123,
          },
        };
        const wrapper = shallowMount(StdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.std).toMatchObject(stdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stdServiceStub.find.resolves(stdSample);
        const wrapper = shallowMount(StdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
