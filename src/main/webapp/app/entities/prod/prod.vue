<template>
  <div>
    <h2 id="page-heading" data-cy="ProdHeading">
      <span v-text="t$('jhipsterApp.prod.home.title')" id="prod-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.prod.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ProdCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-prod">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.prod.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && prods && prods.length === 0">
      <span v-text="t$('jhipsterApp.prod.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="prods && prods.length > 0">
      <table class="table table-striped" aria-describedby="prods">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prodNo')">
              <span v-text="t$('jhipsterApp.prod.prodNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prodNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enName')">
              <span v-text="t$('jhipsterApp.prod.enName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('chName')">
              <span v-text="t$('jhipsterApp.prod.chName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'chName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hsCode')">
              <span v-text="t$('jhipsterApp.prod.hsCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hsCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cccCode')">
              <span v-text="t$('jhipsterApp.prod.cccCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cccCode'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="prod in prods" :key="prod.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProdView', params: { prodId: prod.id } }">{{ prod.id }}</router-link>
            </td>
            <td>{{ prod.prodNo }}</td>
            <td>{{ prod.enName }}</td>
            <td>{{ prod.chName }}</td>
            <td>{{ prod.hsCode }}</td>
            <td>{{ prod.cccCode }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProdView', params: { prodId: prod.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProdEdit', params: { prodId: prod.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(prod)"
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
        <span id="jhipsterApp.prod.delete.question" data-cy="prodDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-prod-heading" v-text="t$('jhipsterApp.prod.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-prod"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeProd()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="prods && prods.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod.component.ts"></script>
