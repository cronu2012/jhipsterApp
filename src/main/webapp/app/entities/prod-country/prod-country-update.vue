<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.prodCountry.home.createOrEditLabel"
          data-cy="ProdCountryCreateUpdateHeading"
          v-text="t$('jhipsterApp.prodCountry.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="prodCountry.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="prodCountry.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodCountry.relType')" for="prod-country-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="prod-country-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodCountry.prod')" for="prod-country-prod"></label>
            <select class="form-control" id="prod-country-prod" data-cy="prod" name="prod" v-model="prodCountry.prod">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="prodCountry.prod && prodOption.id === prodCountry.prod.id ? prodCountry.prod : prodOption"
                v-for="prodOption in prods"
                :key="prodOption.id"
              >
                {{ prodOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodCountry.country')" for="prod-country-country"></label>
            <select class="form-control" id="prod-country-country" data-cy="country" name="country" v-model="prodCountry.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="prodCountry.country && countryOption.id === prodCountry.country.id ? prodCountry.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
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
<script lang="ts" src="./prod-country-update.component.ts"></script>
