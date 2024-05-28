/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import StickerMark from './sticker-mark.vue';
import StickerMarkService from './sticker-mark.service';
import AlertService from '@/shared/alert/alert.service';

type StickerMarkComponentType = InstanceType<typeof StickerMark>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('StickerMark Management Component', () => {
    let stickerMarkServiceStub: SinonStubbedInstance<StickerMarkService>;
    let mountOptions: MountingOptions<StickerMarkComponentType>['global'];

    beforeEach(() => {
      stickerMarkServiceStub = sinon.createStubInstance<StickerMarkService>(StickerMarkService);
      stickerMarkServiceStub.retrieve.resolves({ headers: {} });

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
          stickerMarkService: () => stickerMarkServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stickerMarkServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(StickerMark, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(stickerMarkServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.stickerMarks[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(StickerMark, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(stickerMarkServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: StickerMarkComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(StickerMark, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        stickerMarkServiceStub.retrieve.reset();
        stickerMarkServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        stickerMarkServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(stickerMarkServiceStub.retrieve.called).toBeTruthy();
        expect(comp.stickerMarks[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(stickerMarkServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        stickerMarkServiceStub.retrieve.reset();
        stickerMarkServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(stickerMarkServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.stickerMarks[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(stickerMarkServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        stickerMarkServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeStickerMark();
        await comp.$nextTick(); // clear components

        // THEN
        expect(stickerMarkServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(stickerMarkServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
