import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CountryMarkService from './country-mark.service';
import { type ICountryMark } from '@/shared/model/country-mark.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryMarkDetails',
  setup() {
    const countryMarkService = inject('countryMarkService', () => new CountryMarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const countryMark: Ref<ICountryMark> = ref({});

    const retrieveCountryMark = async countryMarkId => {
      try {
        const res = await countryMarkService().find(countryMarkId);
        countryMark.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.countryMarkId) {
      retrieveCountryMark(route.params.countryMarkId);
    }

    return {
      alertService,
      countryMark,

      previousState,
      t$: useI18n().t,
    };
  },
});
