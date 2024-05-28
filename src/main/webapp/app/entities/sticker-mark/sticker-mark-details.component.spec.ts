/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StickerMarkDetails from './sticker-mark-details.vue';
import StickerMarkService from './sticker-mark.service';
import AlertService from '@/shared/alert/alert.service';

type StickerMarkDetailsComponentType = InstanceType<typeof StickerMarkDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stickerMarkSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('StickerMark Management Detail Component', () => {
    let stickerMarkServiceStub: SinonStubbedInstance<StickerMarkService>;
    let mountOptions: MountingOptions<StickerMarkDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      stickerMarkServiceStub = sinon.createStubInstance<StickerMarkService>(StickerMarkService);

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
          stickerMarkService: () => stickerMarkServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stickerMarkServiceStub.find.resolves(stickerMarkSample);
        route = {
          params: {
            stickerMarkId: '' + 123,
          },
        };
        const wrapper = shallowMount(StickerMarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.stickerMark).toMatchObject(stickerMarkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stickerMarkServiceStub.find.resolves(stickerMarkSample);
        const wrapper = shallowMount(StickerMarkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
