/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StickerMarkUpdate from './sticker-mark-update.vue';
import StickerMarkService from './sticker-mark.service';
import AlertService from '@/shared/alert/alert.service';

import StickerService from '@/entities/sticker/sticker.service';
import MarkService from '@/entities/mark/mark.service';

type StickerMarkUpdateComponentType = InstanceType<typeof StickerMarkUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stickerMarkSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StickerMarkUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('StickerMark Management Update Component', () => {
    let comp: StickerMarkUpdateComponentType;
    let stickerMarkServiceStub: SinonStubbedInstance<StickerMarkService>;

    beforeEach(() => {
      route = {};
      stickerMarkServiceStub = sinon.createStubInstance<StickerMarkService>(StickerMarkService);
      stickerMarkServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          stickerMarkService: () => stickerMarkServiceStub,
          stickerService: () =>
            sinon.createStubInstance<StickerService>(StickerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          markService: () =>
            sinon.createStubInstance<MarkService>(MarkService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StickerMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.stickerMark = stickerMarkSample;
        stickerMarkServiceStub.update.resolves(stickerMarkSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stickerMarkServiceStub.update.calledWith(stickerMarkSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        stickerMarkServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StickerMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.stickerMark = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stickerMarkServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        stickerMarkServiceStub.find.resolves(stickerMarkSample);
        stickerMarkServiceStub.retrieve.resolves([stickerMarkSample]);

        // WHEN
        route = {
          params: {
            stickerMarkId: '' + stickerMarkSample.id,
          },
        };
        const wrapper = shallowMount(StickerMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.stickerMark).toMatchObject(stickerMarkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stickerMarkServiceStub.find.resolves(stickerMarkSample);
        const wrapper = shallowMount(StickerMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
