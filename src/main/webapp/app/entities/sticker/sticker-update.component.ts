import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StickerService from './sticker.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ISticker, Sticker } from '@/shared/model/sticker.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StickerUpdate',
  setup() {
    const stickerService = inject('stickerService', () => new StickerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const sticker: Ref<ISticker> = ref(new Sticker());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveSticker = async stickerId => {
      try {
        const res = await stickerService().find(stickerId);
        sticker.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stickerId) {
      retrieveSticker(route.params.stickerId);
    }

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      stickerNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      img: {},
    };
    const v$ = useVuelidate(validationRules, sticker as any);
    v$.value.$validate();

    return {
      stickerService,
      alertService,
      sticker,
      previousState,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.sticker.id) {
        this.stickerService()
          .update(this.sticker)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.sticker.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.stickerService()
          .create(this.sticker)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.sticker.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    clearInputImage(field, fieldContentType, idInput): void {
      if (this.sticker && field && fieldContentType) {
        if (Object.prototype.hasOwnProperty.call(this.sticker, field)) {
          this.sticker[field] = null;
        }
        if (Object.prototype.hasOwnProperty.call(this.sticker, fieldContentType)) {
          this.sticker[fieldContentType] = null;
        }
        if (idInput) {
          (<any>this).$refs[idInput] = null;
        }
      }
    },
  },
});
