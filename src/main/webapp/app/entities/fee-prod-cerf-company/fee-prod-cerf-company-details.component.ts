import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import FeeProdCerfCompanyService from './fee-prod-cerf-company.service';
import { type IFeeProdCerfCompany } from '@/shared/model/fee-prod-cerf-company.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FeeProdCerfCompanyDetails',
  setup() {
    const feeProdCerfCompanyService = inject('feeProdCerfCompanyService', () => new FeeProdCerfCompanyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const feeProdCerfCompany: Ref<IFeeProdCerfCompany> = ref({});

    const retrieveFeeProdCerfCompany = async feeProdCerfCompanyId => {
      try {
        const res = await feeProdCerfCompanyService().find(feeProdCerfCompanyId);
        feeProdCerfCompany.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.feeProdCerfCompanyId) {
      retrieveFeeProdCerfCompany(route.params.feeProdCerfCompanyId);
    }

    return {
      alertService,
      feeProdCerfCompany,

      previousState,
      t$: useI18n().t,
    };
  },
});
