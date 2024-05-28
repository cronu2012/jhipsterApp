<template>
  <div>
    <h2 id="page-heading" data-cy="ProdStdHeading">
      <span v-text="t$('jhipsterApp.prodStd.home.title')" id="prod-std-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.prodStd.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ProdStdCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-prod-std"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.prodStd.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && prodStds && prodStds.length === 0">
      <span v-text="t$('jhipsterApp.prodStd.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="prodStds && prodStds.length > 0">
      <table class="table table-striped" aria-describedby="prodStds">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('relType')">
              <span v-text="t$('jhipsterApp.prodStd.relType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'relType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prod.id')">
              <span v-text="t$('jhipsterApp.prodStd.prod')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prod.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('std.id')">
              <span v-text="t$('jhipsterApp.prodStd.std')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'std.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="prodStd in prodStds" :key="prodStd.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProdStdView', params: { prodStdId: prodStd.id } }">{{ prodStd.id }}</router-link>
            </td>
            <td>{{ prodStd.relType }}</td>
            <td>
              <div v-if="prodStd.prod">
                <router-link :to="{ name: 'ProdView', params: { prodId: prodStd.prod.id } }">{{ prodStd.prod.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="prodStd.std">
                <router-link :to="{ name: 'StdView', params: { stdId: prodStd.std.id } }">{{ prodStd.std.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProdStdView', params: { prodStdId: prodStd.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProdStdEdit', params: { prodStdId: prodStd.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(prodStd)"
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
        <span id="jhipsterApp.prodStd.delete.question" data-cy="prodStdDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-prodStd-heading" v-text="t$('jhipsterApp.prodStd.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-prodStd"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeProdStd()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="prodStds && prodStds.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod-std.component.ts"></script>
