/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StickerUpdate from './sticker-update.vue';
import StickerService from './sticker.service';
import AlertService from '@/shared/alert/alert.service';

type StickerUpdateComponentType = InstanceType<typeof StickerUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stickerSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StickerUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Sticker Management Update Component', () => {
    let comp: StickerUpdateComponentType;
    let stickerServiceStub: SinonStubbedInstance<StickerService>;

    beforeEach(() => {
      route = {};
      stickerServiceStub = sinon.createStubInstance<StickerService>(StickerService);
      stickerServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          stickerService: () => stickerServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.sticker = stickerSample;
        stickerServiceStub.update.resolves(stickerSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stickerServiceStub.update.calledWith(stickerSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        stickerServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.sticker = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stickerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        stickerServiceStub.find.resolves(stickerSample);
        stickerServiceStub.retrieve.resolves([stickerSample]);

        // WHEN
        route = {
          params: {
            stickerId: '' + stickerSample.id,
          },
        };
        const wrapper = shallowMount(StickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.sticker).toMatchObject(stickerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stickerServiceStub.find.resolves(stickerSample);
        const wrapper = shallowMount(StickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
