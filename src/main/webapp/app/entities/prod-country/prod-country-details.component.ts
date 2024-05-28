import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ProdCountryService from './prod-country.service';
import { type IProdCountry } from '@/shared/model/prod-country.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProdCountryDetails',
  setup() {
    const prodCountryService = inject('prodCountryService', () => new ProdCountryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const prodCountry: Ref<IProdCountry> = ref({});

    const retrieveProdCountry = async prodCountryId => {
      try {
        const res = await prodCountryService().find(prodCountryId);
        prodCountry.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.prodCountryId) {
      retrieveProdCountry(route.params.prodCountryId);
    }

    return {
      alertService,
      prodCountry,

      previousState,
      t$: useI18n().t,
    };
  },
});
