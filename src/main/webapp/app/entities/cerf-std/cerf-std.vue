<template>
  <div>
    <h2 id="page-heading" data-cy="CerfStdHeading">
      <span v-text="t$('jhipsterApp.cerfStd.home.title')" id="cerf-std-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.cerfStd.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CerfStdCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cerf-std"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.cerfStd.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cerfStds && cerfStds.length === 0">
      <span v-text="t$('jhipsterApp.cerfStd.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="cerfStds && cerfStds.length > 0">
      <table class="table table-striped" aria-describedby="cerfStds">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('relType')">
              <span v-text="t$('jhipsterApp.cerfStd.relType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'relType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cerf.id')">
              <span v-text="t$('jhipsterApp.cerfStd.cerf')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cerf.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('std.id')">
              <span v-text="t$('jhipsterApp.cerfStd.std')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'std.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cerfStd in cerfStds" :key="cerfStd.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CerfStdView', params: { cerfStdId: cerfStd.id } }">{{ cerfStd.id }}</router-link>
            </td>
            <td>{{ cerfStd.relType }}</td>
            <td>
              <div v-if="cerfStd.cerf">
                <router-link :to="{ name: 'CerfView', params: { cerfId: cerfStd.cerf.id } }">{{ cerfStd.cerf.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="cerfStd.std">
                <router-link :to="{ name: 'StdView', params: { stdId: cerfStd.std.id } }">{{ cerfStd.std.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CerfStdView', params: { cerfStdId: cerfStd.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CerfStdEdit', params: { cerfStdId: cerfStd.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cerfStd)"
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
        <span id="jhipsterApp.cerfStd.delete.question" data-cy="cerfStdDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-cerfStd-heading" v-text="t$('jhipsterApp.cerfStd.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-cerfStd"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCerfStd()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="cerfStds && cerfStds.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cerf-std.component.ts"></script>
