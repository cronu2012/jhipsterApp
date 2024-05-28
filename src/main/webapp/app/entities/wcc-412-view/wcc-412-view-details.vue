<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="wcc412View">
        <h2 class="jh-entity-heading" data-cy="wcc412ViewDetailsHeading">
          <span v-text="t$('jhipsterApp.wcc412View.detail.title')"></span> {{ wcc412View.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.cerfId')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.cerfId }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.countryChName')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.countryChName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.cerfNo')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.cerfNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.cerfVer')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.cerfVer }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.cerfStatus')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.cerfStatus }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.countryId')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.countryId }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.prodNo')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.prodNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.wcc412View.prodChName')"></span>
          </dt>
          <dd>
            <span>{{ wcc412View.prodChName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./wcc-412-view-details.component.ts"></script>
