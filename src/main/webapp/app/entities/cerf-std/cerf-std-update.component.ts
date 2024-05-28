import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CerfStdService from './cerf-std.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import { type ICerf } from '@/shared/model/cerf.model';
import StdService from '@/entities/std/std.service';
import { type IStd } from '@/shared/model/std.model';
import { type ICerfStd, CerfStd } from '@/shared/model/cerf-std.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfStdUpdate',
  setup() {
    const cerfStdService = inject('cerfStdService', () => new CerfStdService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cerfStd: Ref<ICerfStd> = ref(new CerfStd());

    const cerfService = inject('cerfService', () => new CerfService());

    const cerfs: Ref<ICerf[]> = ref([]);

    const stdService = inject('stdService', () => new StdService());

    const stds: Ref<IStd[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCerfStd = async cerfStdId => {
      try {
        const res = await cerfStdService().find(cerfStdId);
        cerfStd.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfStdId) {
      retrieveCerfStd(route.params.cerfStdId);
    }

    const initRelationships = () => {
      cerfService()
        .retrieve()
        .then(res => {
          cerfs.value = res.data;
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
      cerf: {},
      std: {},
    };
    const v$ = useVuelidate(validationRules, cerfStd as any);
    v$.value.$validate();

    return {
      cerfStdService,
      alertService,
      cerfStd,
      previousState,
      isSaving,
      currentLanguage,
      cerfs,
      stds,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.cerfStd.id) {
        this.cerfStdService()
          .update(this.cerfStd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.cerfStd.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.cerfStdService()
          .create(this.cerfStd)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.cerfStd.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
