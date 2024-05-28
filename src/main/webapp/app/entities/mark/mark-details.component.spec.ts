/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MarkDetails from './mark-details.vue';
import MarkService from './mark.service';
import AlertService from '@/shared/alert/alert.service';

type MarkDetailsComponentType = InstanceType<typeof MarkDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const markSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Mark Management Detail Component', () => {
    let markServiceStub: SinonStubbedInstance<MarkService>;
    let mountOptions: MountingOptions<MarkDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      markServiceStub = sinon.createStubInstance<MarkService>(MarkService);

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
          markService: () => markServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        markServiceStub.find.resolves(markSample);
        route = {
          params: {
            markId: '' + 123,
          },
        };
        const wrapper = shallowMount(MarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.mark).toMatchObject(markSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        markServiceStub.find.resolves(markSample);
        const wrapper = shallowMount(MarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
