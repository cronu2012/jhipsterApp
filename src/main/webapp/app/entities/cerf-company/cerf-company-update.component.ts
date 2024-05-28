import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CerfCompanyService from './cerf-company.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import { type ICerf } from '@/shared/model/cerf.model';
import CompanyService from '@/entities/company/company.service';
import { type ICompany } from '@/shared/model/company.model';
import { type ICerfCompany, CerfCompany } from '@/shared/model/cerf-company.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfCompanyUpdate',
  setup() {
    const cerfCompanyService = inject('cerfCompanyService', () => new CerfCompanyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cerfCompany: Ref<ICerfCompany> = ref(new CerfCompany());

    const cerfService = inject('cerfService', () => new CerfService());

    const cerfs: Ref<ICerf[]> = ref([]);

    const companyService = inject('companyService', () => new CompanyService());

    const companies: Ref<ICompany[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCerfCompany = async cerfCompanyId => {
      try {
        const res = await cerfCompanyService().find(cerfCompanyId);
        cerfCompany.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfCompanyId) {
      retrieveCerfCompany(route.params.cerfCompanyId);
    }

    const initRelationships = () => {
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
      relType: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      cerf: {},
      company: {},
    };
    const v$ = useVuelidate(validationRules, cerfCompany as any);
    v$.value.$validate();

    return {
      cerfCompanyService,
      alertService,
      cerfCompany,
      previousState,
      isSaving,
      currentLanguage,
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
      if (this.cerfCompany.id) {
        this.cerfCompanyService()
          .update(this.cerfCompany)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.cerfCompany.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.cerfCompanyService()
          .create(this.cerfCompany)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.cerfCompany.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
