/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfStdDetails from './cerf-std-details.vue';
import CerfStdService from './cerf-std.service';
import AlertService from '@/shared/alert/alert.service';

type CerfStdDetailsComponentType = InstanceType<typeof CerfStdDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfStdSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CerfStd Management Detail Component', () => {
    let cerfStdServiceStub: SinonStubbedInstance<CerfStdService>;
    let mountOptions: MountingOptions<CerfStdDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cerfStdServiceStub = sinon.createStubInstance<CerfStdService>(CerfStdService);

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
          cerfStdService: () => cerfStdServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cerfStdServiceStub.find.resolves(cerfStdSample);
        route = {
          params: {
            cerfStdId: '' + 123,
          },
        };
        const wrapper = shallowMount(CerfStdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cerfStd).toMatchObject(cerfStdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfStdServiceStub.find.resolves(cerfStdSample);
        const wrapper = shallowMount(CerfStdDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
