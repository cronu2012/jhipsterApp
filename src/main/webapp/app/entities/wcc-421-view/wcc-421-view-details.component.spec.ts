/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import Wcc421ViewDetails from './wcc-421-view-details.vue';
import Wcc421ViewService from './wcc-421-view.service';
import AlertService from '@/shared/alert/alert.service';

type Wcc421ViewDetailsComponentType = InstanceType<typeof Wcc421ViewDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const wcc421ViewSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Wcc421View Management Detail Component', () => {
    let wcc421ViewServiceStub: SinonStubbedInstance<Wcc421ViewService>;
    let mountOptions: MountingOptions<Wcc421ViewDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      wcc421ViewServiceStub = sinon.createStubInstance<Wcc421ViewService>(Wcc421ViewService);

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
          wcc421ViewService: () => wcc421ViewServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        wcc421ViewServiceStub.find.resolves(wcc421ViewSample);
        route = {
          params: {
            wcc421ViewId: '' + 123,
          },
        };
        const wrapper = shallowMount(Wcc421ViewDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.wcc421View).toMatchObject(wcc421ViewSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        wcc421ViewServiceStub.find.resolves(wcc421ViewSample);
        const wrapper = shallowMount(Wcc421ViewDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
