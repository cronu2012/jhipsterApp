import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MarkService from './mark.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IMark } from '@/shared/model/mark.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MarkDetails',
  setup() {
    const markService = inject('markService', () => new MarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const mark: Ref<IMark> = ref({});

    const retrieveMark = async markId => {
      try {
        const res = await markService().find(markId);
        mark.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.markId) {
      retrieveMark(route.params.markId);
    }

    return {
      alertService,
      mark,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
