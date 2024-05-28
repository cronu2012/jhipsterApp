<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.sticker.home.createOrEditLabel"
          data-cy="StickerCreateUpdateHeading"
          v-text="t$('jhipsterApp.sticker.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="sticker.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="sticker.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.sticker.stickerNo')" for="sticker-stickerNo"></label>
            <input
              type="text"
              class="form-control"
              name="stickerNo"
              id="sticker-stickerNo"
              data-cy="stickerNo"
              :class="{ valid: !v$.stickerNo.$invalid, invalid: v$.stickerNo.$invalid }"
              v-model="v$.stickerNo.$model"
            />
            <div v-if="v$.stickerNo.$anyDirty && v$.stickerNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.stickerNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.sticker.img')" for="sticker-img"></label>
            <div>
              <img
                v-bind:src="'data:' + sticker.imgContentType + ';base64,' + sticker.img"
                style="max-height: 100px"
                v-if="sticker.img"
                alt="sticker"
              />
              <div v-if="sticker.img" class="form-text text-danger clearfix">
                <span class="pull-left">{{ sticker.imgContentType }}, {{ byteSize(sticker.img) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('img', 'imgContentType', 'file_img')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_img" v-text="t$('entity.action.addimage')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_img"
                id="file_img"
                style="display: none"
                data-cy="img"
                v-on:change="setFileData($event, sticker, 'img', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="img"
              id="sticker-img"
              data-cy="img"
              :class="{ valid: !v$.img.$invalid, invalid: v$.img.$invalid }"
              v-model="v$.img.$model"
            />
            <input type="hidden" class="form-control" name="imgContentType" id="sticker-imgContentType" v-model="sticker.imgContentType" />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./sticker-update.component.ts"></script>
