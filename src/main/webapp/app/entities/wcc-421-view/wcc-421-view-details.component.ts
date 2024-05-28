import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import Wcc421ViewService from './wcc-421-view.service';
import { type IWcc421View } from '@/shared/model/wcc-421-view.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Wcc421ViewDetails',
  setup() {
    const wcc421ViewService = inject('wcc421ViewService', () => new Wcc421ViewService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const wcc421View: Ref<IWcc421View> = ref({});

    const retrieveWcc421View = async wcc421ViewId => {
      try {
        const res = await wcc421ViewService().find(wcc421ViewId);
        wcc421View.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.wcc421ViewId) {
      retrieveWcc421View(route.params.wcc421ViewId);
    }

    return {
      alertService,
      wcc421View,

      previousState,
      t$: useI18n().t,
    };
  },
});
