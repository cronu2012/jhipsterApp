<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="prodCountry">
        <h2 class="jh-entity-heading" data-cy="prodCountryDetailsHeading">
          <span v-text="t$('jhipsterApp.prodCountry.detail.title')"></span> {{ prodCountry.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.prodCountry.relType')"></span>
          </dt>
          <dd>
            <span>{{ prodCountry.relType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prodCountry.prod')"></span>
          </dt>
          <dd>
            <div v-if="prodCountry.prod">
              <router-link :to="{ name: 'ProdView', params: { prodId: prodCountry.prod.id } }">{{ prodCountry.prod.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prodCountry.country')"></span>
          </dt>
          <dd>
            <div v-if="prodCountry.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: prodCountry.country.id } }">{{
                prodCountry.country.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="prodCountry.id"
          :to="{ name: 'ProdCountryEdit', params: { prodCountryId: prodCountry.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod-country-details.component.ts"></script>
