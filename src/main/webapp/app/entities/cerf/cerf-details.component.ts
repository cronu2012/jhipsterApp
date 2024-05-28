import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CerfService from './cerf.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type ICerf } from '@/shared/model/cerf.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfDetails',
  setup() {
    const cerfService = inject('cerfService', () => new CerfService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cerf: Ref<ICerf> = ref({});

    const retrieveCerf = async cerfId => {
      try {
        const res = await cerfService().find(cerfId);
        cerf.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfId) {
      retrieveCerf(route.params.cerfId);
    }

    return {
      alertService,
      cerf,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
