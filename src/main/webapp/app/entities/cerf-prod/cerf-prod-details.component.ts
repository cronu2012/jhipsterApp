import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CerfProdService from './cerf-prod.service';
import { type ICerfProd } from '@/shared/model/cerf-prod.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfProdDetails',
  setup() {
    const cerfProdService = inject('cerfProdService', () => new CerfProdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cerfProd: Ref<ICerfProd> = ref({});

    const retrieveCerfProd = async cerfProdId => {
      try {
        const res = await cerfProdService().find(cerfProdId);
        cerfProd.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfProdId) {
      retrieveCerfProd(route.params.cerfProdId);
    }

    return {
      alertService,
      cerfProd,

      previousState,
      t$: useI18n().t,
    };
  },
});
