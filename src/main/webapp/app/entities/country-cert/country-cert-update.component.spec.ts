/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CountryCertUpdate from './country-cert-update.vue';
import CountryCertService from './country-cert.service';
import AlertService from '@/shared/alert/alert.service';

import CountryService from '@/entities/country/country.service';
import CerfService from '@/entities/cerf/cerf.service';

type CountryCertUpdateComponentType = InstanceType<typeof CountryCertUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const countryCertSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CountryCertUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CountryCert Management Update Component', () => {
    let comp: CountryCertUpdateComponentType;
    let countryCertServiceStub: SinonStubbedInstance<CountryCertService>;

    beforeEach(() => {
      route = {};
      countryCertServiceStub = sinon.createStubInstance<CountryCertService>(CountryCertService);
      countryCertServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          countryCertService: () => countryCertServiceStub,
          countryService: () =>
            sinon.createStubInstance<CountryService>(CountryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          cerfService: () =>
            sinon.createStubInstance<CerfService>(CerfService, {
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
        const wrapper = shallowMount(CountryCertUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.countryCert = countryCertSample;
        countryCertServiceStub.update.resolves(countryCertSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countryCertServiceStub.update.calledWith(countryCertSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        countryCertServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CountryCertUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.countryCert = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countryCertServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        countryCertServiceStub.find.resolves(countryCertSample);
        countryCertServiceStub.retrieve.resolves([countryCertSample]);

        // WHEN
        route = {
          params: {
            countryCertId: '' + countryCertSample.id,
          },
        };
        const wrapper = shallowMount(CountryCertUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.countryCert).toMatchObject(countryCertSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        countryCertServiceStub.find.resolves(countryCertSample);
        const wrapper = shallowMount(CountryCertUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
