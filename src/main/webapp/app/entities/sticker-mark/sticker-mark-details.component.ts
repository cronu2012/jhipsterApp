import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import StickerMarkService from './sticker-mark.service';
import { type IStickerMark } from '@/shared/model/sticker-mark.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StickerMarkDetails',
  setup() {
    const stickerMarkService = inject('stickerMarkService', () => new StickerMarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const stickerMark: Ref<IStickerMark> = ref({});

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

    return {
      alertService,
      stickerMark,

      previousState,
      t$: useI18n().t,
    };
  },
});
