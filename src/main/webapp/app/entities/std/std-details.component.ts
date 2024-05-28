import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import StdService from './std.service';
import { type IStd } from '@/shared/model/std.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StdDetails',
  setup() {
    const stdService = inject('stdService', () => new StdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const std: Ref<IStd> = ref({});

    const retrieveStd = async stdId => {
      try {
        const res = await stdService().find(stdId);
        std.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stdId) {
      retrieveStd(route.params.stdId);
    }

    return {
      alertService,
      std,

      previousState,
      t$: useI18n().t,
    };
  },
});
