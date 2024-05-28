<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="countryStd">
        <h2 class="jh-entity-heading" data-cy="countryStdDetailsHeading">
          <span v-text="t$('jhipsterApp.countryStd.detail.title')"></span> {{ countryStd.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.countryStd.relType')"></span>
          </dt>
          <dd>
            <span>{{ countryStd.relType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.countryStd.country')"></span>
          </dt>
          <dd>
            <div v-if="countryStd.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: countryStd.country.id } }">{{
                countryStd.country.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.countryStd.std')"></span>
          </dt>
          <dd>
            <div v-if="countryStd.std">
              <router-link :to="{ name: 'StdView', params: { stdId: countryStd.std.id } }">{{ countryStd.std.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="countryStd.id"
          :to="{ name: 'CountryStdEdit', params: { countryStdId: countryStd.id } }"
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

<script lang="ts" src="./country-std-details.component.ts"></script>
