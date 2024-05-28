<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cerf">
        <h2 class="jh-entity-heading" data-cy="cerfDetailsHeading">
          <span v-text="t$('jhipsterApp.cerf.detail.title')"></span> {{ cerf.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.cerf.cerfNo')"></span>
          </dt>
          <dd>
            <span>{{ cerf.cerfNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.cerf.cerfVer')"></span>
          </dt>
          <dd>
            <span>{{ cerf.cerfVer }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.cerf.status')"></span>
          </dt>
          <dd>
            <span>{{ cerf.status }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.cerf.pdf')"></span>
          </dt>
          <dd>
            <div v-if="cerf.pdf">
              <a v-on:click="openFile(cerf.pdfContentType, cerf.pdf)" v-text="t$('entity.action.open')"></a>
              {{ cerf.pdfContentType }}, {{ byteSize(cerf.pdf) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.cerf.issuDt')"></span>
          </dt>
          <dd>
            <span>{{ cerf.issuDt }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.cerf.expDt')"></span>
          </dt>
          <dd>
            <span>{{ cerf.expDt }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="cerf.id" :to="{ name: 'CerfEdit', params: { cerfId: cerf.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cerf-details.component.ts"></script>
