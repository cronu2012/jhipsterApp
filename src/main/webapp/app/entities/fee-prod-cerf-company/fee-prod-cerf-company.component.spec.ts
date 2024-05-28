/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import FeeProdCerfCompany from './fee-prod-cerf-company.vue';
import FeeProdCerfCompanyService from './fee-prod-cerf-company.service';
import AlertService from '@/shared/alert/alert.service';

type FeeProdCerfCompanyComponentType = InstanceType<typeof FeeProdCerfCompany>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('FeeProdCerfCompany Management Component', () => {
    let feeProdCerfCompanyServiceStub: SinonStubbedInstance<FeeProdCerfCompanyService>;
    let mountOptions: MountingOptions<FeeProdCerfCompanyComponentType>['global'];

    beforeEach(() => {
      feeProdCerfCompanyServiceStub = sinon.createStubInstance<FeeProdCerfCompanyService>(FeeProdCerfCompanyService);
      feeProdCerfCompanyServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          feeProdCerfCompanyService: () => feeProdCerfCompanyServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        feeProdCerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(FeeProdCerfCompany, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(feeProdCerfCompanyServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.feeProdCerfCompanies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(FeeProdCerfCompany, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(feeProdCerfCompanyServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: FeeProdCerfCompanyComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(FeeProdCerfCompany, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        feeProdCerfCompanyServiceStub.retrieve.reset();
        feeProdCerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        feeProdCerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(feeProdCerfCompanyServiceStub.retrieve.called).toBeTruthy();
        expect(comp.feeProdCerfCompanies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(feeProdCerfCompanyServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        feeProdCerfCompanyServiceStub.retrieve.reset();
        feeProdCerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(feeProdCerfCompanyServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.feeProdCerfCompanies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(feeProdCerfCompanyServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        feeProdCerfCompanyServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeFeeProdCerfCompany();
        await comp.$nextTick(); // clear components

        // THEN
        expect(feeProdCerfCompanyServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(feeProdCerfCompanyServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
