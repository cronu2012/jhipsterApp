<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.countryCert.home.createOrEditLabel"
          data-cy="CountryCertCreateUpdateHeading"
          v-text="t$('jhipsterApp.countryCert.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="countryCert.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="countryCert.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryCert.relType')" for="country-cert-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="country-cert-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryCert.country')" for="country-cert-country"></label>
            <select class="form-control" id="country-cert-country" data-cy="country" name="country" v-model="countryCert.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="countryCert.country && countryOption.id === countryCert.country.id ? countryCert.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryCert.cerf')" for="country-cert-cerf"></label>
            <select class="form-control" id="country-cert-cerf" data-cy="cerf" name="cerf" v-model="countryCert.cerf">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="countryCert.cerf && cerfOption.id === countryCert.cerf.id ? countryCert.cerf : cerfOption"
                v-for="cerfOption in cerfs"
                :key="cerfOption.id"
              >
                {{ cerfOption.id }}
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
<script lang="ts" src="./country-cert-update.component.ts"></script>
