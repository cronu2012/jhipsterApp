import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CountryStdService from './country-std.service';
import { type ICountryStd } from '@/shared/model/country-std.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryStdDetails',
  setup() {
    const countryStdService = inject('countryStdService', () => new CountryStdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const countryStd: Ref<ICountryStd> = ref({});

    const retrieveCountryStd = async countryStdId => {
      try {
        const res = await countryStdService().find(countryStdId);
        countryStd.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.countryStdId) {
      retrieveCountryStd(route.params.countryStdId);
    }

    return {
      alertService,
      countryStd,

      previousState,
      t$: useI18n().t,
    };
  },
});
