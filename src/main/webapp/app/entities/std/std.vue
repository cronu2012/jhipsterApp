<template>
  <div>
    <h2 id="page-heading" data-cy="StdHeading">
      <span v-text="t$('jhipsterApp.std.home.title')" id="std-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.std.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'StdCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-std">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.std.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stds && stds.length === 0">
      <span v-text="t$('jhipsterApp.std.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="stds && stds.length > 0">
      <table class="table table-striped" aria-describedby="stds">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stdNo')">
              <span v-text="t$('jhipsterApp.std.stdNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stdNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stdVer')">
              <span v-text="t$('jhipsterApp.std.stdVer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stdVer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enName')">
              <span v-text="t$('jhipsterApp.std.enName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('chName')">
              <span v-text="t$('jhipsterApp.std.chName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'chName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('jhipsterApp.std.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('issuDt')">
              <span v-text="t$('jhipsterApp.std.issuDt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'issuDt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expDt')">
              <span v-text="t$('jhipsterApp.std.expDt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expDt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="std in stds" :key="std.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StdView', params: { stdId: std.id } }">{{ std.id }}</router-link>
            </td>
            <td>{{ std.stdNo }}</td>
            <td>{{ std.stdVer }}</td>
            <td>{{ std.enName }}</td>
            <td>{{ std.chName }}</td>
            <td>{{ std.status }}</td>
            <td>{{ std.issuDt }}</td>
            <td>{{ std.expDt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StdView', params: { stdId: std.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StdEdit', params: { stdId: std.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(std)"
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
        <span id="jhipsterApp.std.delete.question" data-cy="stdDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-std-heading" v-text="t$('jhipsterApp.std.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-std"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeStd()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="stds && stds.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./std.component.ts"></script>
