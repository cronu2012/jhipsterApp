import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CerfMarkService from './cerf-mark.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CerfService from '@/entities/cerf/cerf.service';
import { type ICerf } from '@/shared/model/cerf.model';
import MarkService from '@/entities/mark/mark.service';
import { type IMark } from '@/shared/model/mark.model';
import { type ICerfMark, CerfMark } from '@/shared/model/cerf-mark.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CerfMarkUpdate',
  setup() {
    const cerfMarkService = inject('cerfMarkService', () => new CerfMarkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cerfMark: Ref<ICerfMark> = ref(new CerfMark());

    const cerfService = inject('cerfService', () => new CerfService());

    const cerfs: Ref<ICerf[]> = ref([]);

    const markService = inject('markService', () => new MarkService());

    const marks: Ref<IMark[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCerfMark = async cerfMarkId => {
      try {
        const res = await cerfMarkService().find(cerfMarkId);
        cerfMark.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cerfMarkId) {
      retrieveCerfMark(route.params.cerfMarkId);
    }

    const initRelationships = () => {
      cerfService()
        .retrieve()
        .then(res => {
          cerfs.value = res.data;
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
      cerf: {},
      mark: {},
    };
    const v$ = useVuelidate(validationRules, cerfMark as any);
    v$.value.$validate();

    return {
      cerfMarkService,
      alertService,
      cerfMark,
      previousState,
      isSaving,
      currentLanguage,
      cerfs,
      marks,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.cerfMark.id) {
        this.cerfMarkService()
          .update(this.cerfMark)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.cerfMark.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.cerfMarkService()
          .create(this.cerfMark)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.cerfMark.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
