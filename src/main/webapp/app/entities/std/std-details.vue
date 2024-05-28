<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="std">
        <h2 class="jh-entity-heading" data-cy="stdDetailsHeading">
          <span v-text="t$('jhipsterApp.std.detail.title')"></span> {{ std.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.std.stdNo')"></span>
          </dt>
          <dd>
            <span>{{ std.stdNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.std.stdVer')"></span>
          </dt>
          <dd>
            <span>{{ std.stdVer }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.std.enName')"></span>
          </dt>
          <dd>
            <span>{{ std.enName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.std.chName')"></span>
          </dt>
          <dd>
            <span>{{ std.chName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.std.status')"></span>
          </dt>
          <dd>
            <span>{{ std.status }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.std.issuDt')"></span>
          </dt>
          <dd>
            <span>{{ std.issuDt }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.std.expDt')"></span>
          </dt>
          <dd>
            <span>{{ std.expDt }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="std.id" :to="{ name: 'StdEdit', params: { stdId: std.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./std-details.component.ts"></script>
