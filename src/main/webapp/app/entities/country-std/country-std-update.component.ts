import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CountryStdService from './country-std.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CountryService from '@/entities/country/country.service';
import { type ICountry } from '@/shared/model/country.model';
import StdService from '@/entities/std/std.service';
import { type IStd } from '@/shared/model/std.model';
import { type ICountryStd, CountryStd } from '@/shared/model/country-std.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryStdUpdate',
  setup() {
    const countryStdService = inject('countryStdService', () => new CountryStdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const countryStd: Ref<ICountryStd> = ref(new CountryStd());

    const countryService = inject('countryService', () => new CountryService());

    const countries: Ref<ICountry[]> = ref([]);

    const stdService = inject('stdService', () => new StdService());

    const stds: Ref<IStd[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      countryService()
        .retrieve()
        .then(res => {
          countries.value = res.data;
        });
      stdService()
        .retrieve()
        .then(res => {
          stds.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      relType: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      country: {},
      std: {},
    };
    const v$ = useVuelidate(validationRules, countryStd as any);
    v$.value.$validate();

    return {
      countryStdService,
      alertService,
      countryStd,
      previousState,
      isSaving,
      currentLanguage,
      countries,
      stds,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.countryStd.id) {
        this.countryStdService()
          .update(this.countryStd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.countryStd.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.countryStdService()
          .create(this.countryStd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.countryStd.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
