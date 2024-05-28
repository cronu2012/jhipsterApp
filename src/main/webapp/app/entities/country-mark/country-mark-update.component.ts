import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CountryMarkService from './country-mark.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CountryService from '@/entities/country/country.service';
import { type ICountry } from '@/shared/model/country.model';
import MarkService from '@/entities/mark/mark.service';
import { type IMark } from '@/shared/model/mark.model';
import { type ICountryMark, CountryMark } from '@/shared/model/country-mark.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CountryMarkUpdate',
  setup() {
    const countryMarkService = inject('countryMarkService', () => new CountryMarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const countryMark: Ref<ICountryMark> = ref(new CountryMark());

    const countryService = inject('countryService', () => new CountryService());

    const countries: Ref<ICountry[]> = ref([]);

    const markService = inject('markService', () => new MarkService());

    const marks: Ref<IMark[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      countryService()
        .retrieve()
        .then(res => {
          countries.value = res.data;
        });
      markService()
        .retrieve()
        .then(res => {
          marks.value = res.data;
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
      mark: {},
    };
    const v$ = useVuelidate(validationRules, countryMark as any);
    v$.value.$validate();

    return {
      countryMarkService,
      alertService,
      countryMark,
      previousState,
      isSaving,
      currentLanguage,
      countries,
      marks,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.countryMark.id) {
        this.countryMarkService()
          .update(this.countryMark)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.countryMark.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.countryMarkService()
          .create(this.countryMark)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.countryMark.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
