<template>
  <div>
    <h2 id="page-heading" data-cy="CompanyHeading">
      <span v-text="t$('jhipsterApp.company.home.title')" id="company-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.company.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CompanyCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-company"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.company.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && companies && companies.length === 0">
      <span v-text="t$('jhipsterApp.company.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="companies && companies.length > 0">
      <table class="table table-striped" aria-describedby="companies">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('companyNo')">
              <span v-text="t$('jhipsterApp.company.companyNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enName')">
              <span v-text="t$('jhipsterApp.company.enName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('chName')">
              <span v-text="t$('jhipsterApp.company.chName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'chName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tel')">
              <span v-text="t$('jhipsterApp.company.tel')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('addr')">
              <span v-text="t$('jhipsterApp.company.addr')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'addr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span v-text="t$('jhipsterApp.company.email')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('peopleName')">
              <span v-text="t$('jhipsterApp.company.peopleName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'peopleName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="company in companies" :key="company.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CompanyView', params: { companyId: company.id } }">{{ company.id }}</router-link>
            </td>
            <td>{{ company.companyNo }}</td>
            <td>{{ company.enName }}</td>
            <td>{{ company.chName }}</td>
            <td>{{ company.tel }}</td>
            <td>{{ company.addr }}</td>
            <td>{{ company.email }}</td>
            <td>{{ company.peopleName }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CompanyView', params: { companyId: company.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CompanyEdit', params: { companyId: company.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(company)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="jhipsterApp.company.delete.question" data-cy="companyDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-company-heading" v-text="t$('jhipsterApp.company.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-company"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCompany()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="companies && companies.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./company.component.ts"></script>
