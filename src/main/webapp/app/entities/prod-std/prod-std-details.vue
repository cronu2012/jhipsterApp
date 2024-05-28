<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="prodStd">
        <h2 class="jh-entity-heading" data-cy="prodStdDetailsHeading">
          <span v-text="t$('jhipsterApp.prodStd.detail.title')"></span> {{ prodStd.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.prodStd.relType')"></span>
          </dt>
          <dd>
            <span>{{ prodStd.relType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prodStd.prod')"></span>
          </dt>
          <dd>
            <div v-if="prodStd.prod">
              <router-link :to="{ name: 'ProdView', params: { prodId: prodStd.prod.id } }">{{ prodStd.prod.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prodStd.std')"></span>
          </dt>
          <dd>
            <div v-if="prodStd.std">
              <router-link :to="{ name: 'StdView', params: { stdId: prodStd.std.id } }">{{ prodStd.std.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="prodStd.id" :to="{ name: 'ProdStdEdit', params: { prodStdId: prodStd.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod-std-details.component.ts"></script>
