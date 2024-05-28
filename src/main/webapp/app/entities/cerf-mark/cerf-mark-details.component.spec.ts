/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CerfMarkDetails from './cerf-mark-details.vue';
import CerfMarkService from './cerf-mark.service';
import AlertService from '@/shared/alert/alert.service';

type CerfMarkDetailsComponentType = InstanceType<typeof CerfMarkDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cerfMarkSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CerfMark Management Detail Component', () => {
    let cerfMarkServiceStub: SinonStubbedInstance<CerfMarkService>;
    let mountOptions: MountingOptions<CerfMarkDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cerfMarkServiceStub = sinon.createStubInstance<CerfMarkService>(CerfMarkService);

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
          cerfMarkService: () => cerfMarkServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cerfMarkServiceStub.find.resolves(cerfMarkSample);
        route = {
          params: {
            cerfMarkId: '' + 123,
          },
        };
        const wrapper = shallowMount(CerfMarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cerfMark).toMatchObject(cerfMarkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cerfMarkServiceStub.find.resolves(cerfMarkSample);
        const wrapper = shallowMount(CerfMarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
