<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.countryMark.home.createOrEditLabel"
          data-cy="CountryMarkCreateUpdateHeading"
          v-text="t$('jhipsterApp.countryMark.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="countryMark.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="countryMark.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryMark.relType')" for="country-mark-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="country-mark-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryMark.country')" for="country-mark-country"></label>
            <select class="form-control" id="country-mark-country" data-cy="country" name="country" v-model="countryMark.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="countryMark.country && countryOption.id === countryMark.country.id ? countryMark.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.countryMark.mark')" for="country-mark-mark"></label>
            <select class="form-control" id="country-mark-mark" data-cy="mark" name="mark" v-model="countryMark.mark">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="countryMark.mark && markOption.id === countryMark.mark.id ? countryMark.mark : markOption"
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
<script lang="ts" src="./country-mark-update.component.ts"></script>
