<template>
  <div>
    <h2 id="page-heading" data-cy="Wcc421ViewHeading">
      <span v-text="t$('jhipsterApp.wcc421View.home.title')" id="wcc-421-view-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.wcc421View.home.refreshListLabel')"></span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && wcc421Views && wcc421Views.length === 0">
      <span v-text="t$('jhipsterApp.wcc421View.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="wcc421Views && wcc421Views.length > 0">
      <table class="table table-striped" aria-describedby="wcc421Views">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfId')">
              <span v-text="t$('jhipsterApp.wcc421View.cerfId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('countryChName')">
              <span v-text="t$('jhipsterApp.wcc421View.countryChName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryChName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfNo')">
              <span v-text="t$('jhipsterApp.wcc421View.cerfNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfVer')">
              <span v-text="t$('jhipsterApp.wcc421View.cerfVer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfVer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfStatus')">
              <span v-text="t$('jhipsterApp.wcc421View.cerfStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('companyChName')">
              <span v-text="t$('jhipsterApp.wcc421View.companyChName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyChName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('relType')">
              <span v-text="t$('jhipsterApp.wcc421View.relType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'relType'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="wcc421View in wcc421Views" :key="wcc421View.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'Wcc421ViewView', params: { wcc421ViewId: wcc421View.id } }">{{ wcc421View.id }}</router-link>
            </td>
            <td>{{ wcc421View.cerfId }}</td>
            <td>{{ wcc421View.countryChName }}</td>
            <td>{{ wcc421View.cerfNo }}</td>
            <td>{{ wcc421View.cerfVer }}</td>
            <td>{{ wcc421View.cerfStatus }}</td>
            <td>{{ wcc421View.companyChName }}</td>
            <td>{{ wcc421View.relType }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'Wcc421ViewView', params: { wcc421ViewId: wcc421View.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-show="wcc421Views && wcc421Views.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./wcc-421-view.component.ts"></script>
