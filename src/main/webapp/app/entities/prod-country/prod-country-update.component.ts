import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProdCountryService from './prod-country.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import { type IProd } from '@/shared/model/prod.model';
import CountryService from '@/entities/country/country.service';
import { type ICountry } from '@/shared/model/country.model';
import { type IProdCountry, ProdCountry } from '@/shared/model/prod-country.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProdCountryUpdate',
  setup() {
    const prodCountryService = inject('prodCountryService', () => new ProdCountryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const prodCountry: Ref<IProdCountry> = ref(new ProdCountry());

    const prodService = inject('prodService', () => new ProdService());

    const prods: Ref<IProd[]> = ref([]);

    const countryService = inject('countryService', () => new CountryService());

    const countries: Ref<ICountry[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      prodService()
        .retrieve()
        .then(res => {
          prods.value = res.data;
        });
      countryService()
        .retrieve()
        .then(res => {
          countries.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      relType: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      prod: {},
      country: {},
    };
    const v$ = useVuelidate(validationRules, prodCountry as any);
    v$.value.$validate();

    return {
      prodCountryService,
      alertService,
      prodCountry,
      previousState,
      isSaving,
      currentLanguage,
      prods,
      countries,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.prodCountry.id) {
        this.prodCountryService()
          .update(this.prodCountry)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.prodCountry.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.prodCountryService()
          .create(this.prodCountry)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.prodCountry.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
