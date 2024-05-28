<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="wcc421View">
        <h2 class="jh-entity-heading" data-cy="wcc421ViewDetailsHeading">
          <span v-text="t$('jhipsterApp.wcc421View.detail.title')"></span> {{ wcc421View.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.cerfId')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.cerfId }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.countryChName')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.countryChName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.cerfNo')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.cerfNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.cerfVer')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.cerfVer }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.cerfStatus')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.cerfStatus }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.companyChName')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.companyChName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc421View.relType')"></span>
          </dt>
          <dd>
            <span>{{ wcc421View.relType }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./wcc-421-view-details.component.ts"></script>
