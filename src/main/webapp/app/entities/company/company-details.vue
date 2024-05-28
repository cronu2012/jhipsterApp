<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="company">
        <h2 class="jh-entity-heading" data-cy="companyDetailsHeading">
          <span v-text="t$('jhipsterApp.company.detail.title')"></span> {{ company.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.company.companyNo')"></span>
          </dt>
          <dd>
            <span>{{ company.companyNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.company.enName')"></span>
          </dt>
          <dd>
            <span>{{ company.enName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.company.chName')"></span>
          </dt>
          <dd>
            <span>{{ company.chName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.company.tel')"></span>
          </dt>
          <dd>
            <span>{{ company.tel }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.company.addr')"></span>
          </dt>
          <dd>
            <span>{{ company.addr }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.company.email')"></span>
          </dt>
          <dd>
            <span>{{ company.email }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.company.peopleName')"></span>
          </dt>
          <dd>
            <span>{{ company.peopleName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="company.id" :to="{ name: 'CompanyEdit', params: { companyId: company.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./company-details.component.ts"></script>
