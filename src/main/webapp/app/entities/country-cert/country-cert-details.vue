<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="countryCert">
        <h2 class="jh-entity-heading" data-cy="countryCertDetailsHeading">
          <span v-text="t$('jhipsterApp.countryCert.detail.title')"></span> {{ countryCert.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.countryCert.relType')"></span>
          </dt>
          <dd>
            <span>{{ countryCert.relType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.countryCert.country')"></span>
          </dt>
          <dd>
            <div v-if="countryCert.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: countryCert.country.id } }">{{
                countryCert.country.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.countryCert.cerf')"></span>
          </dt>
          <dd>
            <div v-if="countryCert.cerf">
              <router-link :to="{ name: 'CerfView', params: { cerfId: countryCert.cerf.id } }">{{ countryCert.cerf.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="countryCert.id"
          :to="{ name: 'CountryCertEdit', params: { countryCertId: countryCert.id } }"
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

<script lang="ts" src="./country-cert-details.component.ts"></script>
