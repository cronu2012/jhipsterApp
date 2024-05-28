<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.std.home.createOrEditLabel"
          data-cy="StdCreateUpdateHeading"
          v-text="t$('jhipsterApp.std.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="std.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="std.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.stdNo')" for="std-stdNo"></label>
            <input
              type="text"
              class="form-control"
              name="stdNo"
              id="std-stdNo"
              data-cy="stdNo"
              :class="{ valid: !v$.stdNo.$invalid, invalid: v$.stdNo.$invalid }"
              v-model="v$.stdNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.stdVer')" for="std-stdVer"></label>
            <input
              type="text"
              class="form-control"
              name="stdVer"
              id="std-stdVer"
              data-cy="stdVer"
              :class="{ valid: !v$.stdVer.$invalid, invalid: v$.stdVer.$invalid }"
              v-model="v$.stdVer.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.enName')" for="std-enName"></label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="std-enName"
              data-cy="enName"
              :class="{ valid: !v$.enName.$invalid, invalid: v$.enName.$invalid }"
              v-model="v$.enName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.chName')" for="std-chName"></label>
            <input
              type="text"
              class="form-control"
              name="chName"
              id="std-chName"
              data-cy="chName"
              :class="{ valid: !v$.chName.$invalid, invalid: v$.chName.$invalid }"
              v-model="v$.chName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.status')" for="std-status"></label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="std-status"
              data-cy="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
            />
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.issuDt')" for="std-issuDt"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="std-issuDt"
                  v-model="v$.issuDt.$model"
                  name="issuDt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="std-issuDt"
                data-cy="issuDt"
                type="text"
                class="form-control"
                name="issuDt"
                :class="{ valid: !v$.issuDt.$invalid, invalid: v$.issuDt.$invalid }"
                v-model="v$.issuDt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.std.expDt')" for="std-expDt"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="std-expDt"
                  v-model="v$.expDt.$model"
                  name="expDt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="std-expDt"
                data-cy="expDt"
                type="text"
                class="form-control"
                name="expDt"
                :class="{ valid: !v$.expDt.$invalid, invalid: v$.expDt.$invalid }"
                v-model="v$.expDt.$model"
              />
            </b-input-group>
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
<script lang="ts" src="./std-update.component.ts"></script>
