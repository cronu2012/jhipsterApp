<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.cerfStd.home.createOrEditLabel"
          data-cy="CerfStdCreateUpdateHeading"
          v-text="t$('jhipsterApp.cerfStd.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="cerfStd.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="cerfStd.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerfStd.relType')" for="cerf-std-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="cerf-std-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerfStd.cerf')" for="cerf-std-cerf"></label>
            <select class="form-control" id="cerf-std-cerf" data-cy="cerf" name="cerf" v-model="cerfStd.cerf">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cerfStd.cerf && cerfOption.id === cerfStd.cerf.id ? cerfStd.cerf : cerfOption"
                v-for="cerfOption in cerfs"
                :key="cerfOption.id"
              >
                {{ cerfOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerfStd.std')" for="cerf-std-std"></label>
            <select class="form-control" id="cerf-std-std" data-cy="std" name="std" v-model="cerfStd.std">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cerfStd.std && stdOption.id === cerfStd.std.id ? cerfStd.std : stdOption"
                v-for="stdOption in stds"
                :key="stdOption.id"
              >
                {{ stdOption.id }}
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
<script lang="ts" src="./cerf-std-update.component.ts"></script>
