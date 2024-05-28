import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CerfCompanyService from './cerf-company.service';
import { type ICerfCompany } from '@/shared/model/cerf-company.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfCompanyDetails',
  setup() {
    const cerfCompanyService = inject('cerfCompanyService', () => new CerfCompanyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cerfCompany: Ref<ICerfCompany> = ref({});

    const retrieveCerfCompany = async cerfCompanyId => {
      try {
        const res = await cerfCompanyService().find(cerfCompanyId);
        cerfCompany.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfCompanyId) {
      retrieveCerfCompany(route.params.cerfCompanyId);
    }

    return {
      alertService,
      cerfCompany,

      previousState,
      t$: useI18n().t,
    };
  },
});
