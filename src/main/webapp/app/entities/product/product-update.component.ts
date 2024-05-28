import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProductService from './product.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IProduct, Product } from '@/shared/model/product.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProductUpdate',
  setup() {
    const productService = inject('productService', () => new ProductService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const product: Ref<IProduct> = ref(new Product());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'zh-tw'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveProduct = async productId => {
      try {
        const res = await productService().find(productId);
        res.createdTime = new Date(res.createdTime);
        res.updatedTime = new Date(res.updatedTime);
        product.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.productId) {
      retrieveProduct(route.params.productId);
    }

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      mainTitle: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      subTitle: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      description: {},
      price: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 10 }).toString(), 10),
      },
      createBy: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      createdTime: {},
      updatedTime: {},
      status: {},
    };
    const v$ = useVuelidate(validationRules, product as any);
    v$.value.$validate();

    return {
      productService,
      alertService,
      product,
      previousState,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
      ...useDateFormat({ entityRef: product }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.product.id) {
        this.productService()
          .update(this.product)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.product.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.productService()
          .create(this.product)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.product.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
