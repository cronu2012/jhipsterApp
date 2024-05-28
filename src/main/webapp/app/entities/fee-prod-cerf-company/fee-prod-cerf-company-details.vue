<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="feeProdCerfCompany">
        <h2 class="jh-entity-heading" data-cy="feeProdCerfCompanyDetailsHeading">
          <span v-text="t$('jhipsterApp.feeProdCerfCompany.detail.title')"></span> {{ feeProdCerfCompany.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.feeProdCerfCompany.fee')"></span>
          </dt>
          <dd>
            <span>{{ feeProdCerfCompany.fee }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.feeProdCerfCompany.feeType')"></span>
          </dt>
          <dd>
            <span>{{ feeProdCerfCompany.feeType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.feeProdCerfCompany.feeDt')"></span>
          </dt>
          <dd>
            <span>{{ feeProdCerfCompany.feeDt }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.feeProdCerfCompany.prod')"></span>
          </dt>
          <dd>
            <div v-if="feeProdCerfCompany.prod">
              <router-link :to="{ name: 'ProdView', params: { prodId: feeProdCerfCompany.prod.id } }">{{
                feeProdCerfCompany.prod.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.feeProdCerfCompany.cerf')"></span>
          </dt>
          <dd>
            <div v-if="feeProdCerfCompany.cerf">
              <router-link :to="{ name: 'CerfView', params: { cerfId: feeProdCerfCompany.cerf.id } }">{{
                feeProdCerfCompany.cerf.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.feeProdCerfCompany.company')"></span>
          </dt>
          <dd>
            <div v-if="feeProdCerfCompany.company">
              <router-link :to="{ name: 'CompanyView', params: { companyId: feeProdCerfCompany.company.id } }">{{
                feeProdCerfCompany.company.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="feeProdCerfCompany.id"
          :to="{ name: 'FeeProdCerfCompanyEdit', params: { feeProdCerfCompanyId: feeProdCerfCompany.id } }"
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

<script lang="ts" src="./fee-prod-cerf-company-details.component.ts"></script>
