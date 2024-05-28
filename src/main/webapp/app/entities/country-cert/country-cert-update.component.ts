import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CountryCertService from './country-cert.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CountryService from '@/entities/country/country.service';
import { type ICountry } from '@/shared/model/country.model';
import CerfService from '@/entities/cerf/cerf.service';
import { type ICerf } from '@/shared/model/cerf.model';
import { type ICountryCert, CountryCert } from '@/shared/model/country-cert.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryCertUpdate',
  setup() {
    const countryCertService = inject('countryCertService', () => new CountryCertService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const countryCert: Ref<ICountryCert> = ref(new CountryCert());

    const countryService = inject('countryService', () => new CountryService());

    const countries: Ref<ICountry[]> = ref([]);

    const cerfService = inject('cerfService', () => new CerfService());

    const cerfs: Ref<ICerf[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      countryService()
        .retrieve()
        .then(res => {
          countries.value = res.data;
        });
      cerfService()
        .retrieve()
        .then(res => {
          cerfs.value = res.data;
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
      cerf: {},
    };
    const v$ = useVuelidate(validationRules, countryCert as any);
    v$.value.$validate();

    return {
      countryCertService,
      alertService,
      countryCert,
      previousState,
      isSaving,
      currentLanguage,
      countries,
      cerfs,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.countryCert.id) {
        this.countryCertService()
          .update(this.countryCert)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.countryCert.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.countryCertService()
          .create(this.countryCert)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.countryCert.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
