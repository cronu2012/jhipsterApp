/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StdUpdate from './std-update.vue';
import StdService from './std.service';
import AlertService from '@/shared/alert/alert.service';

type StdUpdateComponentType = InstanceType<typeof StdUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stdSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StdUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Std Management Update Component', () => {
    let comp: StdUpdateComponentType;
    let stdServiceStub: SinonStubbedInstance<StdService>;

    beforeEach(() => {
      route = {};
      stdServiceStub = sinon.createStubInstance<StdService>(StdService);
      stdServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          stdService: () => stdServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.std = stdSample;
        stdServiceStub.update.resolves(stdSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdServiceStub.update.calledWith(stdSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        stdServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.std = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        stdServiceStub.find.resolves(stdSample);
        stdServiceStub.retrieve.resolves([stdSample]);

        // WHEN
        route = {
          params: {
            stdId: '' + stdSample.id,
          },
        };
        const wrapper = shallowMount(StdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.std).toMatchObject(stdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stdServiceStub.find.resolves(stdSample);
        const wrapper = shallowMount(StdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
