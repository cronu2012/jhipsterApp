import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ProdService from './prod.service';
import { type IProd } from '@/shared/model/prod.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProdDetails',
  setup() {
    const prodService = inject('prodService', () => new ProdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const prod: Ref<IProd> = ref({});

    const retrieveProd = async prodId => {
      try {
        const res = await prodService().find(prodId);
        prod.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.prodId) {
      retrieveProd(route.params.prodId);
    }

    return {
      alertService,
      prod,

      previousState,
      t$: useI18n().t,
    };
  },
});
