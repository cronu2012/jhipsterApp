<template>
  <div>
    <h2 id="page-heading" data-cy="CerfCompanyHeading">
      <span v-text="t$('jhipsterApp.cerfCompany.home.title')" id="cerf-company-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.cerfCompany.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CerfCompanyCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cerf-company"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.cerfCompany.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cerfCompanies && cerfCompanies.length === 0">
      <span v-text="t$('jhipsterApp.cerfCompany.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="cerfCompanies && cerfCompanies.length > 0">
      <table class="table table-striped" aria-describedby="cerfCompanies">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('relType')">
              <span v-text="t$('jhipsterApp.cerfCompany.relType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'relType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerf.id')">
              <span v-text="t$('jhipsterApp.cerfCompany.cerf')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerf.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('company.id')">
              <span v-text="t$('jhipsterApp.cerfCompany.company')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cerfCompany in cerfCompanies" :key="cerfCompany.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CerfCompanyView', params: { cerfCompanyId: cerfCompany.id } }">{{ cerfCompany.id }}</router-link>
            </td>
            <td>{{ cerfCompany.relType }}</td>
            <td>
              <div v-if="cerfCompany.cerf">
                <router-link :to="{ name: 'CerfView', params: { cerfId: cerfCompany.cerf.id } }">{{ cerfCompany.cerf.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="cerfCompany.company">
                <router-link :to="{ name: 'CompanyView', params: { companyId: cerfCompany.company.id } }">{{
                  cerfCompany.company.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CerfCompanyView', params: { cerfCompanyId: cerfCompany.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CerfCompanyEdit', params: { cerfCompanyId: cerfCompany.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cerfCompany)"
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
        <span
          id="jhipsterApp.cerfCompany.delete.question"
          data-cy="cerfCompanyDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-cerfCompany-heading" v-text="t$('jhipsterApp.cerfCompany.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-cerfCompany"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCerfCompany()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="cerfCompanies && cerfCompanies.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cerf-company.component.ts"></script>
