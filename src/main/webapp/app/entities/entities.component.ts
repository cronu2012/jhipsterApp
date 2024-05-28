import { defineComponent, provide } from 'vue';

import ProdService from './prod/prod.service';
import CountryService from './country/country.service';
import CerfService from './cerf/cerf.service';
import StdService from './std/std.service';
import MarkService from './mark/mark.service';
import CompanyService from './company/company.service';
import FeeProdCerfCompanyService from './fee-prod-cerf-company/fee-prod-cerf-company.service';
import CerfCompanyService from './cerf-company/cerf-company.service';
import StickerService from './sticker/sticker.service';
import ProdCountryService from './prod-country/prod-country.service';
import ProdStdService from './prod-std/prod-std.service';
import CerfProdService from './cerf-prod/cerf-prod.service';
import CerfStdService from './cerf-std/cerf-std.service';
import CountryStdService from './country-std/country-std.service';
import CerfMarkService from './cerf-mark/cerf-mark.service';
import StickerMarkService from './sticker-mark/sticker-mark.service';
import CountryCertService from './country-cert/country-cert.service';
import ProdStickerService from './prod-sticker/prod-sticker.service';
import CountryMarkService from './country-mark/country-mark.service';
import Wcc412ViewService from './wcc-412-view/wcc-412-view.service';
import Wcc421ViewService from './wcc-421-view/wcc-421-view.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('prodService', () => new ProdService());
    provide('countryService', () => new CountryService());
    provide('cerfService', () => new CerfService());
    provide('stdService', () => new StdService());
    provide('markService', () => new MarkService());
    provide('companyService', () => new CompanyService());
    provide('feeProdCerfCompanyService', () => new FeeProdCerfCompanyService());
    provide('cerfCompanyService', () => new CerfCompanyService());
    provide('stickerService', () => new StickerService());
    provide('prodCountryService', () => new ProdCountryService());
    provide('prodStdService', () => new ProdStdService());
    provide('cerfProdService', () => new CerfProdService());
    provide('cerfStdService', () => new CerfStdService());
    provide('countryStdService', () => new CountryStdService());
    provide('cerfMarkService', () => new CerfMarkService());
    provide('stickerMarkService', () => new StickerMarkService());
    provide('countryCertService', () => new CountryCertService());
    provide('prodStickerService', () => new ProdStickerService());
    provide('countryMarkService', () => new CountryMarkService());
    provide('wcc412ViewService', () => new Wcc412ViewService());
    provide('wcc421ViewService', () => new Wcc421ViewService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
