/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdStickerDetails from './prod-sticker-details.vue';
import ProdStickerService from './prod-sticker.service';
import AlertService from '@/shared/alert/alert.service';

type ProdStickerDetailsComponentType = InstanceType<typeof ProdStickerDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodStickerSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ProdSticker Management Detail Component', () => {
    let prodStickerServiceStub: SinonStubbedInstance<ProdStickerService>;
    let mountOptions: MountingOptions<ProdStickerDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      prodStickerServiceStub = sinon.createStubInstance<ProdStickerService>(ProdStickerService);

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
          prodStickerService: () => prodStickerServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        prodStickerServiceStub.find.resolves(prodStickerSample);
        route = {
          params: {
            prodStickerId: '' + 123,
          },
        };
        const wrapper = shallowMount(ProdStickerDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.prodSticker).toMatchObject(prodStickerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodStickerServiceStub.find.resolves(prodStickerSample);
        const wrapper = shallowMount(ProdStickerDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
