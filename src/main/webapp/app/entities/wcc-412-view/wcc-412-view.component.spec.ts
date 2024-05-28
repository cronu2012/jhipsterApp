/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Wcc412View from './wcc-412-view.vue';
import Wcc412ViewService from './wcc-412-view.service';
import AlertService from '@/shared/alert/alert.service';

type Wcc412ViewComponentType = InstanceType<typeof Wcc412View>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Wcc412View Management Component', () => {
    let wcc412ViewServiceStub: SinonStubbedInstance<Wcc412ViewService>;
    let mountOptions: MountingOptions<Wcc412ViewComponentType>['global'];

    beforeEach(() => {
      wcc412ViewServiceStub = sinon.createStubInstance<Wcc412ViewService>(Wcc412ViewService);
      wcc412ViewServiceStub.retrieve.resolves({ headers: {} });

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
          wcc412ViewService: () => wcc412ViewServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        wcc412ViewServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Wcc412View, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(wcc412ViewServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.wcc412Views[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(Wcc412View, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(wcc412ViewServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: Wcc412ViewComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Wcc412View, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        wcc412ViewServiceStub.retrieve.reset();
        wcc412ViewServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        wcc412ViewServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(wcc412ViewServiceStub.retrieve.called).toBeTruthy();
        expect(comp.wcc412Views[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(wcc412ViewServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        wcc412ViewServiceStub.retrieve.reset();
        wcc412ViewServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(wcc412ViewServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.wcc412Views[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(wcc412ViewServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });
    });
  });
});
