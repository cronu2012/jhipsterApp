import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ProdStickerService from './prod-sticker.service';
import { type IProdSticker } from '@/shared/model/prod-sticker.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProdStickerDetails',
  setup() {
    const prodStickerService = inject('prodStickerService', () => new ProdStickerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const prodSticker: Ref<IProdSticker> = ref({});

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

    return {
      alertService,
      prodSticker,

      previousState,
      t$: useI18n().t,
    };
  },
});
