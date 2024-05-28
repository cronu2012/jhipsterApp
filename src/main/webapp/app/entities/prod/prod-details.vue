<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="prod">
        <h2 class="jh-entity-heading" data-cy="prodDetailsHeading">
          <span v-text="t$('jhipsterApp.prod.detail.title')"></span> {{ prod.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.prod.prodNo')"></span>
          </dt>
          <dd>
            <span>{{ prod.prodNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prod.enName')"></span>
          </dt>
          <dd>
            <span>{{ prod.enName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prod.chName')"></span>
          </dt>
          <dd>
            <span>{{ prod.chName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prod.hsCode')"></span>
          </dt>
          <dd>
            <span>{{ prod.hsCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prod.cccCode')"></span>
          </dt>
          <dd>
            <span>{{ prod.cccCode }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="prod.id" :to="{ name: 'ProdEdit', params: { prodId: prod.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod-details.component.ts"></script>
