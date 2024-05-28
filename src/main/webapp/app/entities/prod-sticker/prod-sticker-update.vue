<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.prodSticker.home.createOrEditLabel"
          data-cy="ProdStickerCreateUpdateHeading"
          v-text="t$('jhipsterApp.prodSticker.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="prodSticker.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="prodSticker.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodSticker.relType')" for="prod-sticker-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="prod-sticker-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodSticker.prod')" for="prod-sticker-prod"></label>
            <select class="form-control" id="prod-sticker-prod" data-cy="prod" name="prod" v-model="prodSticker.prod">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="prodSticker.prod && prodOption.id === prodSticker.prod.id ? prodSticker.prod : prodOption"
                v-for="prodOption in prods"
                :key="prodOption.id"
              >
                {{ prodOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodSticker.sticker')" for="prod-sticker-sticker"></label>
            <select class="form-control" id="prod-sticker-sticker" data-cy="sticker" name="sticker" v-model="prodSticker.sticker">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="prodSticker.sticker && stickerOption.id === prodSticker.sticker.id ? prodSticker.sticker : stickerOption"
                v-for="stickerOption in stickers"
                :key="stickerOption.id"
              >
                {{ stickerOption.id }}
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
<script lang="ts" src="./prod-sticker-update.component.ts"></script>
