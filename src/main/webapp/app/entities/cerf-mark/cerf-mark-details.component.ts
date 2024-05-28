import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CerfMarkService from './cerf-mark.service';
import { type ICerfMark } from '@/shared/model/cerf-mark.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfMarkDetails',
  setup() {
    const cerfMarkService = inject('cerfMarkService', () => new CerfMarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cerfMark: Ref<ICerfMark> = ref({});

    const retrieveCerfMark = async cerfMarkId => {
      try {
        const res = await cerfMarkService().find(cerfMarkId);
        cerfMark.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfMarkId) {
      retrieveCerfMark(route.params.cerfMarkId);
    }

    return {
      alertService,
      cerfMark,

      previousState,
      t$: useI18n().t,
    };
  },
});
