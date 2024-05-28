import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CerfStdService from './cerf-std.service';
import { type ICerfStd } from '@/shared/model/cerf-std.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfStdDetails',
  setup() {
    const cerfStdService = inject('cerfStdService', () => new CerfStdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cerfStd: Ref<ICerfStd> = ref({});

    const retrieveCerfStd = async cerfStdId => {
      try {
        const res = await cerfStdService().find(cerfStdId);
        cerfStd.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfStdId) {
      retrieveCerfStd(route.params.cerfStdId);
    }

    return {
      alertService,
      cerfStd,

      previousState,
      t$: useI18n().t,
    };
  },
});
