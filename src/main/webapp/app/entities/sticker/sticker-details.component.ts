import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import StickerService from './sticker.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type ISticker } from '@/shared/model/sticker.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StickerDetails',
  setup() {
    const stickerService = inject('stickerService', () => new StickerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const sticker: Ref<ISticker> = ref({});

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

    return {
      alertService,
      sticker,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
