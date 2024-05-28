/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import Wcc412ViewDetails from './wcc-412-view-details.vue';
import Wcc412ViewService from './wcc-412-view.service';
import AlertService from '@/shared/alert/alert.service';

type Wcc412ViewDetailsComponentType = InstanceType<typeof Wcc412ViewDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const wcc412ViewSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Wcc412View Management Detail Component', () => {
    let wcc412ViewServiceStub: SinonStubbedInstance<Wcc412ViewService>;
    let mountOptions: MountingOptions<Wcc412ViewDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      wcc412ViewServiceStub = sinon.createStubInstance<Wcc412ViewService>(Wcc412ViewService);

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
          wcc412ViewService: () => wcc412ViewServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        wcc412ViewServiceStub.find.resolves(wcc412ViewSample);
        route = {
          params: {
            wcc412ViewId: '' + 123,
          },
        };
        const wrapper = shallowMount(Wcc412ViewDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.wcc412View).toMatchObject(wcc412ViewSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        wcc412ViewServiceStub.find.resolves(wcc412ViewSample);
        const wrapper = shallowMount(Wcc412ViewDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
