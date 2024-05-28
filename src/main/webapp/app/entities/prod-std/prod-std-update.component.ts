import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProdStdService from './prod-std.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProdService from '@/entities/prod/prod.service';
import { type IProd } from '@/shared/model/prod.model';
import StdService from '@/entities/std/std.service';
import { type IStd } from '@/shared/model/std.model';
import { type IProdStd, ProdStd } from '@/shared/model/prod-std.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProdStdUpdate',
  setup() {
    const prodStdService = inject('prodStdService', () => new ProdStdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const prodStd: Ref<IProdStd> = ref(new ProdStd());

    const prodService = inject('prodService', () => new ProdService());

    const prods: Ref<IProd[]> = ref([]);

    const stdService = inject('stdService', () => new StdService());

    const stds: Ref<IStd[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveProdStd = async prodStdId => {
      try {
        const res = await prodStdService().find(prodStdId);
        prodStd.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.prodStdId) {
      retrieveProdStd(route.params.prodStdId);
    }

    const initRelationships = () => {
      prodService()
        .retrieve()
        .then(res => {
          prods.value = res.data;
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
      prod: {},
      std: {},
    };
    const v$ = useVuelidate(validationRules, prodStd as any);
    v$.value.$validate();

    return {
      prodStdService,
      alertService,
      prodStd,
      previousState,
      isSaving,
      currentLanguage,
      prods,
      stds,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.prodStd.id) {
        this.prodStdService()
          .update(this.prodStd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.prodStd.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.prodStdService()
          .create(this.prodStd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.prodStd.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
