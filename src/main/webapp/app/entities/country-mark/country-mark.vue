<template>
  <div>
    <h2 id="page-heading" data-cy="CountryMarkHeading">
      <span v-text="t$('jhipsterApp.countryMark.home.title')" id="country-mark-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.countryMark.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CountryMarkCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-country-mark"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.countryMark.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && countryMarks && countryMarks.length === 0">
      <span v-text="t$('jhipsterApp.countryMark.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="countryMarks && countryMarks.length > 0">
      <table class="table table-striped" aria-describedby="countryMarks">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('relType')">
              <span v-text="t$('jhipsterApp.countryMark.relType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'relType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('country.id')">
              <span v-text="t$('jhipsterApp.countryMark.country')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'country.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mark.id')">
              <span v-text="t$('jhipsterApp.countryMark.mark')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mark.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="countryMark in countryMarks" :key="countryMark.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CountryMarkView', params: { countryMarkId: countryMark.id } }">{{ countryMark.id }}</router-link>
            </td>
            <td>{{ countryMark.relType }}</td>
            <td>
              <div v-if="countryMark.country">
                <router-link :to="{ name: 'CountryView', params: { countryId: countryMark.country.id } }">{{
                  countryMark.country.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="countryMark.mark">
                <router-link :to="{ name: 'MarkView', params: { markId: countryMark.mark.id } }">{{ countryMark.mark.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CountryMarkView', params: { countryMarkId: countryMark.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CountryMarkEdit', params: { countryMarkId: countryMark.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(countryMark)"
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
          id="jhipsterApp.countryMark.delete.question"
          data-cy="countryMarkDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-countryMark-heading" v-text="t$('jhipsterApp.countryMark.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-countryMark"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCountryMark()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="countryMarks && countryMarks.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./country-mark.component.ts"></script>
