/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CountryMarkUpdate from './country-mark-update.vue';
import CountryMarkService from './country-mark.service';
import AlertService from '@/shared/alert/alert.service';

import CountryService from '@/entities/country/country.service';
import MarkService from '@/entities/mark/mark.service';

type CountryMarkUpdateComponentType = InstanceType<typeof CountryMarkUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const countryMarkSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CountryMarkUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CountryMark Management Update Component', () => {
    let comp: CountryMarkUpdateComponentType;
    let countryMarkServiceStub: SinonStubbedInstance<CountryMarkService>;

    beforeEach(() => {
      route = {};
      countryMarkServiceStub = sinon.createStubInstance<CountryMarkService>(CountryMarkService);
      countryMarkServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          countryMarkService: () => countryMarkServiceStub,
          countryService: () =>
            sinon.createStubInstance<CountryService>(CountryService, {
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
        const wrapper = shallowMount(CountryMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.countryMark = countryMarkSample;
        countryMarkServiceStub.update.resolves(countryMarkSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countryMarkServiceStub.update.calledWith(countryMarkSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        countryMarkServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CountryMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.countryMark = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countryMarkServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        countryMarkServiceStub.find.resolves(countryMarkSample);
        countryMarkServiceStub.retrieve.resolves([countryMarkSample]);

        // WHEN
        route = {
          params: {
            countryMarkId: '' + countryMarkSample.id,
          },
        };
        const wrapper = shallowMount(CountryMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.countryMark).toMatchObject(countryMarkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        countryMarkServiceStub.find.resolves(countryMarkSample);
        const wrapper = shallowMount(CountryMarkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
