/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CountryStdUpdate from './country-std-update.vue';
import CountryStdService from './country-std.service';
import AlertService from '@/shared/alert/alert.service';

import CountryService from '@/entities/country/country.service';
import StdService from '@/entities/std/std.service';

type CountryStdUpdateComponentType = InstanceType<typeof CountryStdUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const countryStdSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CountryStdUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CountryStd Management Update Component', () => {
    let comp: CountryStdUpdateComponentType;
    let countryStdServiceStub: SinonStubbedInstance<CountryStdService>;

    beforeEach(() => {
      route = {};
      countryStdServiceStub = sinon.createStubInstance<CountryStdService>(CountryStdService);
      countryStdServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          countryStdService: () => countryStdServiceStub,
          countryService: () =>
            sinon.createStubInstance<CountryService>(CountryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          stdService: () =>
            sinon.createStubInstance<StdService>(StdService, {
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
        const wrapper = shallowMount(CountryStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.countryStd = countryStdSample;
        countryStdServiceStub.update.resolves(countryStdSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countryStdServiceStub.update.calledWith(countryStdSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        countryStdServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CountryStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.countryStd = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countryStdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        countryStdServiceStub.find.resolves(countryStdSample);
        countryStdServiceStub.retrieve.resolves([countryStdSample]);

        // WHEN
        route = {
          params: {
            countryStdId: '' + countryStdSample.id,
          },
        };
        const wrapper = shallowMount(CountryStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.countryStd).toMatchObject(countryStdSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        countryStdServiceStub.find.resolves(countryStdSample);
        const wrapper = shallowMount(CountryStdUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
