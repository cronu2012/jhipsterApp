<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.countryStd.home.createOrEditLabel"
          data-cy="CountryStdCreateUpdateHeading"
          v-text="t$('jhipsterApp.countryStd.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="countryStd.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="countryStd.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryStd.relType')" for="country-std-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="country-std-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryStd.country')" for="country-std-country"></label>
            <select class="form-control" id="country-std-country" data-cy="country" name="country" v-model="countryStd.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="countryStd.country && countryOption.id === countryStd.country.id ? countryStd.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryStd.std')" for="country-std-std"></label>
            <select class="form-control" id="country-std-std" data-cy="std" name="std" v-model="countryStd.std">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="countryStd.std && stdOption.id === countryStd.std.id ? countryStd.std : stdOption"
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
<script lang="ts" src="./country-std-update.component.ts"></script>
