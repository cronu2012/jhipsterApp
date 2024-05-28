import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CountryCertService from './country-cert.service';
import { type ICountryCert } from '@/shared/model/country-cert.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryCertDetails',
  setup() {
    const countryCertService = inject('countryCertService', () => new CountryCertService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const countryCert: Ref<ICountryCert> = ref({});

    const retrieveCountryCert = async countryCertId => {
      try {
        const res = await countryCertService().find(countryCertId);
        countryCert.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.countryCertId) {
      retrieveCountryCert(route.params.countryCertId);
    }

    return {
      alertService,
      countryCert,

      previousState,
      t$: useI18n().t,
    };
  },
});
