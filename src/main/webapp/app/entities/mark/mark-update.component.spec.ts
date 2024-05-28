/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MarkUpdate from './mark-update.vue';
import MarkService from './mark.service';
import AlertService from '@/shared/alert/alert.service';

type MarkUpdateComponentType = InstanceType<typeof MarkUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const markSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MarkUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Mark Management Update Component', () => {
    let comp: MarkUpdateComponentType;
    let markServiceStub: SinonStubbedInstance<MarkService>;

    beforeEach(() => {
      route = {};
      markServiceStub = sinon.createStubInstance<MarkService>(MarkService);
      markServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          markService: () => markServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.mark = markSample;
        markServiceStub.update.resolves(markSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(markServiceStub.update.calledWith(markSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        markServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.mark = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(markServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        markServiceStub.find.resolves(markSample);
        markServiceStub.retrieve.resolves([markSample]);

        // WHEN
        route = {
          params: {
            markId: '' + markSample.id,
          },
        };
        const wrapper = shallowMount(MarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.mark).toMatchObject(markSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        markServiceStub.find.resolves(markSample);
        const wrapper = shallowMount(MarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
