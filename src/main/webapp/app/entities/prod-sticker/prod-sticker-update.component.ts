import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProdStickerService from './prod-sticker.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import { type IProd } from '@/shared/model/prod.model';
import StickerService from '@/entities/sticker/sticker.service';
import { type ISticker } from '@/shared/model/sticker.model';
import { type IProdSticker, ProdSticker } from '@/shared/model/prod-sticker.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProdStickerUpdate',
  setup() {
    const prodStickerService = inject('prodStickerService', () => new ProdStickerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const prodSticker: Ref<IProdSticker> = ref(new ProdSticker());

    const prodService = inject('prodService', () => new ProdService());

    const prods: Ref<IProd[]> = ref([]);

    const stickerService = inject('stickerService', () => new StickerService());

    const stickers: Ref<ISticker[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveProdSticker = async prodStickerId => {
      try {
        const res = await prodStickerService().find(prodStickerId);
        prodSticker.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.prodStickerId) {
      retrieveProdSticker(route.params.prodStickerId);
    }

    const initRelationships = () => {
      prodService()
        .retrieve()
        .then(res => {
          prods.value = res.data;
        });
      stickerService()
        .retrieve()
        .then(res => {
          stickers.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      relType: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      prod: {},
      sticker: {},
    };
    const v$ = useVuelidate(validationRules, prodSticker as any);
    v$.value.$validate();

    return {
      prodStickerService,
      alertService,
      prodSticker,
      previousState,
      isSaving,
      currentLanguage,
      prods,
      stickers,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.prodSticker.id) {
        this.prodStickerService()
          .update(this.prodSticker)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.prodSticker.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.prodStickerService()
          .create(this.prodSticker)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.prodSticker.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
