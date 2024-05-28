/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import CerfCompany from './cerf-company.vue';
import CerfCompanyService from './cerf-company.service';
import AlertService from '@/shared/alert/alert.service';

type CerfCompanyComponentType = InstanceType<typeof CerfCompany>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('CerfCompany Management Component', () => {
    let cerfCompanyServiceStub: SinonStubbedInstance<CerfCompanyService>;
    let mountOptions: MountingOptions<CerfCompanyComponentType>['global'];

    beforeEach(() => {
      cerfCompanyServiceStub = sinon.createStubInstance<CerfCompanyService>(CerfCompanyService);
      cerfCompanyServiceStub.retrieve.resolves({ headers: {} });

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
          cerfCompanyService: () => cerfCompanyServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(CerfCompany, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(cerfCompanyServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.cerfCompanies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(CerfCompany, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(cerfCompanyServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: CerfCompanyComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(CerfCompany, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        cerfCompanyServiceStub.retrieve.reset();
        cerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        cerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(cerfCompanyServiceStub.retrieve.called).toBeTruthy();
        expect(comp.cerfCompanies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(cerfCompanyServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        cerfCompanyServiceStub.retrieve.reset();
        cerfCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(cerfCompanyServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.cerfCompanies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(cerfCompanyServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        cerfCompanyServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeCerfCompany();
        await comp.$nextTick(); // clear components

        // THEN
        expect(cerfCompanyServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(cerfCompanyServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
