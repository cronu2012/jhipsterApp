import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StdService from './std.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IStd, Std } from '@/shared/model/std.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StdUpdate',
  setup() {
    const stdService = inject('stdService', () => new StdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const std: Ref<IStd> = ref(new Std());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveStd = async stdId => {
      try {
        const res = await stdService().find(stdId);
        std.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stdId) {
      retrieveStd(route.params.stdId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      stdNo: {},
      stdVer: {},
      enName: {},
      chName: {},
      status: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 10 }).toString(), 10),
      },
      issuDt: {},
      expDt: {},
    };
    const v$ = useVuelidate(validationRules, std as any);
    v$.value.$validate();

    return {
      stdService,
      alertService,
      std,
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
      if (this.std.id) {
        this.stdService()
          .update(this.std)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.std.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.stdService()
          .create(this.std)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.std.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
