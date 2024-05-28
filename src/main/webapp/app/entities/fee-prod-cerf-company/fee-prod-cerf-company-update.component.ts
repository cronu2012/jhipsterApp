import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FeeProdCerfCompanyService from './fee-prod-cerf-company.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import { type IProd } from '@/shared/model/prod.model';
import CerfService from '@/entities/cerf/cerf.service';
import { type ICerf } from '@/shared/model/cerf.model';
import CompanyService from '@/entities/company/company.service';
import { type ICompany } from '@/shared/model/company.model';
import { type IFeeProdCerfCompany, FeeProdCerfCompany } from '@/shared/model/fee-prod-cerf-company.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FeeProdCerfCompanyUpdate',
  setup() {
    const feeProdCerfCompanyService = inject('feeProdCerfCompanyService', () => new FeeProdCerfCompanyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const feeProdCerfCompany: Ref<IFeeProdCerfCompany> = ref(new FeeProdCerfCompany());

    const prodService = inject('prodService', () => new ProdService());

    const prods: Ref<IProd[]> = ref([]);

    const cerfService = inject('cerfService', () => new CerfService());

    const cerfs: Ref<ICerf[]> = ref([]);

    const companyService = inject('companyService', () => new CompanyService());

    const companies: Ref<ICompany[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      prodService()
        .retrieve()
        .then(res => {
          prods.value = res.data;
        });
      cerfService()
        .retrieve()
        .then(res => {
          cerfs.value = res.data;
        });
      companyService()
        .retrieve()
        .then(res => {
          companies.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      fee: {},
      feeType: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 10 }).toString(), 10),
      },
      feeDt: {},
      prod: {},
      cerf: {},
      company: {},
    };
    const v$ = useVuelidate(validationRules, feeProdCerfCompany as any);
    v$.value.$validate();

    return {
      feeProdCerfCompanyService,
      alertService,
      feeProdCerfCompany,
      previousState,
      isSaving,
      currentLanguage,
      prods,
      cerfs,
      companies,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.feeProdCerfCompany.id) {
        this.feeProdCerfCompanyService()
          .update(this.feeProdCerfCompany)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.feeProdCerfCompany.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.feeProdCerfCompanyService()
          .create(this.feeProdCerfCompany)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.feeProdCerfCompany.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
