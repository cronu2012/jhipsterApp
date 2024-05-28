import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import Wcc412ViewService from './wcc-412-view.service';
import { type IWcc412View } from '@/shared/model/wcc-412-view.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Wcc412ViewDetails',
  setup() {
    const wcc412ViewService = inject('wcc412ViewService', () => new Wcc412ViewService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const wcc412View: Ref<IWcc412View> = ref({});

    const retrieveWcc412View = async wcc412ViewId => {
      try {
        const res = await wcc412ViewService().find(wcc412ViewId);
        wcc412View.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.wcc412ViewId) {
      retrieveWcc412View(route.params.wcc412ViewId);
    }

    return {
      alertService,
      wcc412View,

      previousState,
      t$: useI18n().t,
    };
  },
});
