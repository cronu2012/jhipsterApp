/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProdCountryUpdate from './prod-country-update.vue';
import ProdCountryService from './prod-country.service';
import AlertService from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import CountryService from '@/entities/country/country.service';

type ProdCountryUpdateComponentType = InstanceType<typeof ProdCountryUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const prodCountrySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ProdCountryUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ProdCountry Management Update Component', () => {
    let comp: ProdCountryUpdateComponentType;
    let prodCountryServiceStub: SinonStubbedInstance<ProdCountryService>;

    beforeEach(() => {
      route = {};
      prodCountryServiceStub = sinon.createStubInstance<ProdCountryService>(ProdCountryService);
      prodCountryServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          prodCountryService: () => prodCountryServiceStub,
          prodService: () =>
            sinon.createStubInstance<ProdService>(ProdService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          countryService: () =>
            sinon.createStubInstance<CountryService>(CountryService, {
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
        const wrapper = shallowMount(ProdCountryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prodCountry = prodCountrySample;
        prodCountryServiceStub.update.resolves(prodCountrySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodCountryServiceStub.update.calledWith(prodCountrySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        prodCountryServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ProdCountryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.prodCountry = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prodCountryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        prodCountryServiceStub.find.resolves(prodCountrySample);
        prodCountryServiceStub.retrieve.resolves([prodCountrySample]);

        // WHEN
        route = {
          params: {
            prodCountryId: '' + prodCountrySample.id,
          },
        };
        const wrapper = shallowMount(ProdCountryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.prodCountry).toMatchObject(prodCountrySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        prodCountryServiceStub.find.resolves(prodCountrySample);
        const wrapper = shallowMount(ProdCountryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
