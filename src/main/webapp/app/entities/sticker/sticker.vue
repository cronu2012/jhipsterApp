<template>
  <div>
    <h2 id="page-heading" data-cy="StickerHeading">
      <span v-text="t$('jhipsterApp.sticker.home.title')" id="sticker-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.sticker.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'StickerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-sticker"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.sticker.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stickers && stickers.length === 0">
      <span v-text="t$('jhipsterApp.sticker.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="stickers && stickers.length > 0">
      <table class="table table-striped" aria-describedby="stickers">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stickerNo')">
              <span v-text="t$('jhipsterApp.sticker.stickerNo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stickerNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('img')">
              <span v-text="t$('jhipsterApp.sticker.img')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'img'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sticker in stickers" :key="sticker.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StickerView', params: { stickerId: sticker.id } }">{{ sticker.id }}</router-link>
            </td>
            <td>{{ sticker.stickerNo }}</td>
            <td>
              <a v-if="sticker.img" v-on:click="openFile(sticker.imgContentType, sticker.img)">
                <img v-bind:src="'data:' + sticker.imgContentType + ';base64,' + sticker.img" style="max-height: 30px" alt="sticker" />
              </a>
              <span v-if="sticker.img">{{ sticker.imgContentType }}, {{ byteSize(sticker.img) }}</span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StickerView', params: { stickerId: sticker.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StickerEdit', params: { stickerId: sticker.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(sticker)"
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
        <span id="jhipsterApp.sticker.delete.question" data-cy="stickerDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-sticker-heading" v-text="t$('jhipsterApp.sticker.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-sticker"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeSticker()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="stickers && stickers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./sticker.component.ts"></script>
