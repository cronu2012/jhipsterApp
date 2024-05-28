<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.mark.home.createOrEditLabel"
          data-cy="MarkCreateUpdateHeading"
          v-text="t$('jhipsterApp.mark.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="mark.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="mark.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.mark.markNo')" for="mark-markNo"></label>
            <input
              type="text"
              class="form-control"
              name="markNo"
              id="mark-markNo"
              data-cy="markNo"
              :class="{ valid: !v$.markNo.$invalid, invalid: v$.markNo.$invalid }"
              v-model="v$.markNo.$model"
            />
            <div v-if="v$.markNo.$anyDirty && v$.markNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.markNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.mark.enName')" for="mark-enName"></label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="mark-enName"
              data-cy="enName"
              :class="{ valid: !v$.enName.$invalid, invalid: v$.enName.$invalid }"
              v-model="v$.enName.$model"
            />
            <div v-if="v$.enName.$anyDirty && v$.enName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.enName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.mark.chName')" for="mark-chName"></label>
            <input
              type="text"
              class="form-control"
              name="chName"
              id="mark-chName"
              data-cy="chName"
              :class="{ valid: !v$.chName.$invalid, invalid: v$.chName.$invalid }"
              v-model="v$.chName.$model"
            />
            <div v-if="v$.chName.$anyDirty && v$.chName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.chName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.mark.img')" for="mark-img"></label>
            <div>
              <img
                v-bind:src="'data:' + mark.imgContentType + ';base64,' + mark.img"
                style="max-height: 100px"
                v-if="mark.img"
                alt="mark"
              />
              <div v-if="mark.img" class="form-text text-danger clearfix">
                <span class="pull-left">{{ mark.imgContentType }}, {{ byteSize(mark.img) }}</span>
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
                v-on:change="setFileData($event, mark, 'img', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="img"
              id="mark-img"
              data-cy="img"
              :class="{ valid: !v$.img.$invalid, invalid: v$.img.$invalid }"
              v-model="v$.img.$model"
            />
            <input type="hidden" class="form-control" name="imgContentType" id="mark-imgContentType" v-model="mark.imgContentType" />
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
<script lang="ts" src="./mark-update.component.ts"></script>
