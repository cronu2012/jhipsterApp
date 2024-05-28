/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdStickerUpdate from './prod-sticker-update.vue';
import ProdStickerService from './prod-sticker.service';
import AlertService from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import StickerService from '@/entities/sticker/sticker.service';

type ProdStickerUpdateComponentType = InstanceType<typeof ProdStickerUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodStickerSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ProdStickerUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ProdSticker Management Update Component', () => {
    let comp: ProdStickerUpdateComponentType;
    let prodStickerServiceStub: SinonStubbedInstance<ProdStickerService>;

    beforeEach(() => {
      route = {};
      prodStickerServiceStub = sinon.createStubInstance<ProdStickerService>(ProdStickerService);
      prodStickerServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          prodStickerService: () => prodStickerServiceStub,
          prodService: () =>
            sinon.createStubInstance<ProdService>(ProdService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          stickerService: () =>
            sinon.createStubInstance<StickerService>(StickerService, {
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
        const wrapper = shallowMount(ProdStickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prodSticker = prodStickerSample;
        prodStickerServiceStub.update.resolves(prodStickerSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodStickerServiceStub.update.calledWith(prodStickerSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        prodStickerServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ProdStickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prodSticker = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodStickerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        prodStickerServiceStub.find.resolves(prodStickerSample);
        prodStickerServiceStub.retrieve.resolves([prodStickerSample]);

        // WHEN
        route = {
          params: {
            prodStickerId: '' + prodStickerSample.id,
          },
        };
        const wrapper = shallowMount(ProdStickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.prodSticker).toMatchObject(prodStickerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodStickerServiceStub.find.resolves(prodStickerSample);
        const wrapper = shallowMount(ProdStickerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
