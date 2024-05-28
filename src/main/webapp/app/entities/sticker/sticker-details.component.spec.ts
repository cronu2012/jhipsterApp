/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StickerDetails from './sticker-details.vue';
import StickerService from './sticker.service';
import AlertService from '@/shared/alert/alert.service';

type StickerDetailsComponentType = InstanceType<typeof StickerDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stickerSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Sticker Management Detail Component', () => {
    let stickerServiceStub: SinonStubbedInstance<StickerService>;
    let mountOptions: MountingOptions<StickerDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      stickerServiceStub = sinon.createStubInstance<StickerService>(StickerService);

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
          stickerService: () => stickerServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stickerServiceStub.find.resolves(stickerSample);
        route = {
          params: {
            stickerId: '' + 123,
          },
        };
        const wrapper = shallowMount(StickerDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.sticker).toMatchObject(stickerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stickerServiceStub.find.resolves(stickerSample);
        const wrapper = shallowMount(StickerDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
