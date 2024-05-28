import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StickerMarkService from './sticker-mark.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import StickerService from '@/entities/sticker/sticker.service';
import { type ISticker } from '@/shared/model/sticker.model';
import MarkService from '@/entities/mark/mark.service';
import { type IMark } from '@/shared/model/mark.model';
import { type IStickerMark, StickerMark } from '@/shared/model/sticker-mark.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StickerMarkUpdate',
  setup() {
    const stickerMarkService = inject('stickerMarkService', () => new StickerMarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const stickerMark: Ref<IStickerMark> = ref(new StickerMark());

    const stickerService = inject('stickerService', () => new StickerService());

    const stickers: Ref<ISticker[]> = ref([]);

    const markService = inject('markService', () => new MarkService());

    const marks: Ref<IMark[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveStickerMark = async stickerMarkId => {
      try {
        const res = await stickerMarkService().find(stickerMarkId);
        stickerMark.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stickerMarkId) {
      retrieveStickerMark(route.params.stickerMarkId);
    }

    const initRelationships = () => {
      stickerService()
        .retrieve()
        .then(res => {
          stickers.value = res.data;
        });
      markService()
        .retrieve()
        .then(res => {
          marks.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      relType: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      sticker: {},
      mark: {},
    };
    const v$ = useVuelidate(validationRules, stickerMark as any);
    v$.value.$validate();

    return {
      stickerMarkService,
      alertService,
      stickerMark,
      previousState,
      isSaving,
      currentLanguage,
      stickers,
      marks,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.stickerMark.id) {
        this.stickerMarkService()
          .update(this.stickerMark)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.stickerMark.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.stickerMarkService()
          .create(this.stickerMark)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.stickerMark.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
