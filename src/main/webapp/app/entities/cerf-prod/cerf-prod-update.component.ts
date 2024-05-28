import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CerfProdService from './cerf-prod.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import { type ICerf } from '@/shared/model/cerf.model';
import ProdService from '@/entities/prod/prod.service';
import { type IProd } from '@/shared/model/prod.model';
import { type ICerfProd, CerfProd } from '@/shared/model/cerf-prod.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfProdUpdate',
  setup() {
    const cerfProdService = inject('cerfProdService', () => new CerfProdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cerfProd: Ref<ICerfProd> = ref(new CerfProd());

    const cerfService = inject('cerfService', () => new CerfService());

    const cerfs: Ref<ICerf[]> = ref([]);

    const prodService = inject('prodService', () => new ProdService());

    const prods: Ref<IProd[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCerfProd = async cerfProdId => {
      try {
        const res = await cerfProdService().find(cerfProdId);
        cerfProd.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfProdId) {
      retrieveCerfProd(route.params.cerfProdId);
    }

    const initRelationships = () => {
      cerfService()
        .retrieve()
        .then(res => {
          cerfs.value = res.data;
        });
      prodService()
        .retrieve()
        .then(res => {
          prods.value = res.data;
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
      prod: {},
    };
    const v$ = useVuelidate(validationRules, cerfProd as any);
    v$.value.$validate();

    return {
      cerfProdService,
      alertService,
      cerfProd,
      previousState,
      isSaving,
      currentLanguage,
      cerfs,
      prods,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.cerfProd.id) {
        this.cerfProdService()
          .update(this.cerfProd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.cerfProd.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.cerfProdService()
          .create(this.cerfProd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.cerfProd.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
