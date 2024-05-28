<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.stickerMark.home.createOrEditLabel"
          data-cy="StickerMarkCreateUpdateHeading"
          v-text="t$('jhipsterApp.stickerMark.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="stickerMark.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="stickerMark.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.stickerMark.relType')" for="sticker-mark-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="sticker-mark-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.stickerMark.sticker')" for="sticker-mark-sticker"></label>
            <select class="form-control" id="sticker-mark-sticker" data-cy="sticker" name="sticker" v-model="stickerMark.sticker">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="stickerMark.sticker && stickerOption.id === stickerMark.sticker.id ? stickerMark.sticker : stickerOption"
                v-for="stickerOption in stickers"
                :key="stickerOption.id"
              >
                {{ stickerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.stickerMark.mark')" for="sticker-mark-mark"></label>
            <select class="form-control" id="sticker-mark-mark" data-cy="mark" name="mark" v-model="stickerMark.mark">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="stickerMark.mark && markOption.id === stickerMark.mark.id ? stickerMark.mark : markOption"
                v-for="markOption in marks"
                :key="markOption.id"
              >
                {{ markOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./sticker-mark-update.component.ts"></script>
