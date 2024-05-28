<template>
  <div>
    <h2 id="page-heading" data-cy="Wcc412ViewHeading">
      <span v-text="t$('jhipsterApp.wcc412View.home.title')" id="wcc-412-view-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.wcc412View.home.refreshListLabel')"></span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && wcc412Views && wcc412Views.length === 0">
      <span v-text="t$('jhipsterApp.wcc412View.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="wcc412Views && wcc412Views.length > 0">
      <table class="table table-striped" aria-describedby="wcc412Views">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfId')">
              <span v-text="t$('jhipsterApp.wcc412View.cerfId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('countryChName')">
              <span v-text="t$('jhipsterApp.wcc412View.countryChName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryChName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfNo')">
              <span v-text="t$('jhipsterApp.wcc412View.cerfNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfVer')">
              <span v-text="t$('jhipsterApp.wcc412View.cerfVer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfVer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfStatus')">
              <span v-text="t$('jhipsterApp.wcc412View.cerfStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('countryId')">
              <span v-text="t$('jhipsterApp.wcc412View.countryId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prodNo')">
              <span v-text="t$('jhipsterApp.wcc412View.prodNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prodNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prodChName')">
              <span v-text="t$('jhipsterApp.wcc412View.prodChName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prodChName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="wcc412View in wcc412Views" :key="wcc412View.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'Wcc412ViewView', params: { wcc412ViewId: wcc412View.id } }">{{ wcc412View.id }}</router-link>
            </td>
            <td>{{ wcc412View.cerfId }}</td>
            <td>{{ wcc412View.countryChName }}</td>
            <td>{{ wcc412View.cerfNo }}</td>
            <td>{{ wcc412View.cerfVer }}</td>
            <td>{{ wcc412View.cerfStatus }}</td>
            <td>{{ wcc412View.countryId }}</td>
            <td>{{ wcc412View.prodNo }}</td>
            <td>{{ wcc412View.prodChName }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'Wcc412ViewView', params: { wcc412ViewId: wcc412View.id } }" custom v-slot="{ navigate }">
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
    <div v-show="wcc412Views && wcc412Views.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./wcc-412-view.component.ts"></script>
