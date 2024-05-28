<template>
  <div>
    <h2 id="page-heading" data-cy="CerfHeading">
      <span v-text="t$('jhipsterApp.cerf.home.title')" id="cerf-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.cerf.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CerfCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-cerf">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.cerf.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cerfs && cerfs.length === 0">
      <span v-text="t$('jhipsterApp.cerf.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="cerfs && cerfs.length > 0">
      <table class="table table-striped" aria-describedby="cerfs">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfNo')">
              <span v-text="t$('jhipsterApp.cerf.cerfNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerfVer')">
              <span v-text="t$('jhipsterApp.cerf.cerfVer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerfVer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('jhipsterApp.cerf.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pdf')">
              <span v-text="t$('jhipsterApp.cerf.pdf')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pdf'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('issuDt')">
              <span v-text="t$('jhipsterApp.cerf.issuDt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'issuDt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expDt')">
              <span v-text="t$('jhipsterApp.cerf.expDt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expDt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cerf in cerfs" :key="cerf.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CerfView', params: { cerfId: cerf.id } }">{{ cerf.id }}</router-link>
            </td>
            <td>{{ cerf.cerfNo }}</td>
            <td>{{ cerf.cerfVer }}</td>
            <td>{{ cerf.status }}</td>
            <td>
              <a v-if="cerf.pdf" v-on:click="openFile(cerf.pdfContentType, cerf.pdf)" v-text="t$('entity.action.open')"></a>
              <span v-if="cerf.pdf">{{ cerf.pdfContentType }}, {{ byteSize(cerf.pdf) }}</span>
            </td>
            <td>{{ cerf.issuDt }}</td>
            <td>{{ cerf.expDt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CerfView', params: { cerfId: cerf.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CerfEdit', params: { cerfId: cerf.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cerf)"
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
        <span id="jhipsterApp.cerf.delete.question" data-cy="cerfDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-cerf-heading" v-text="t$('jhipsterApp.cerf.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-cerf"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCerf()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="cerfs && cerfs.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cerf.component.ts"></script>
