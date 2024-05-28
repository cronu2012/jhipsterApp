<template>
  <div>
    <b-dropdown-item to="/prod">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.prod')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.country')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cerf">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cerf')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/std">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.std')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/mark">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.mark')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/company">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.company')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/fee-prod-cerf-company">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.feeProdCerfCompany')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cerf-company">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cerfCompany')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/sticker">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.sticker')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/prod-country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.prodCountry')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/prod-std">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.prodStd')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cerf-prod">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cerfProd')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cerf-std">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cerfStd')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country-std">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.countryStd')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cerf-mark">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cerfMark')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/sticker-mark">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.stickerMark')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country-cert">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.countryCert')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/prod-sticker">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.prodSticker')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country-mark">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.countryMark')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/wcc-412-view">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.wcc412View')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/wcc-421-view">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.wcc421View')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
