import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CountryService from './country.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ICountry, Country } from '@/shared/model/country.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryUpdate',
  setup() {
    const countryService = inject('countryService', () => new CountryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const country: Ref<ICountry> = ref(new Country());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCountry = async countryId => {
      try {
        const res = await countryService().find(countryId);
        country.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.countryId) {
      retrieveCountry(route.params.countryId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      countryNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 10 }).toString(), 10),
      },
      enName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      chName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
    };
    const v$ = useVuelidate(validationRules, country as any);
    v$.value.$validate();

    return {
      countryService,
      alertService,
      country,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.country.id) {
        this.countryService()
          .update(this.country)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.country.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.countryService()
          .create(this.country)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.country.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
