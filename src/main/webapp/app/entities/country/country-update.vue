<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.country.home.createOrEditLabel"
          data-cy="CountryCreateUpdateHeading"
          v-text="t$('jhipsterApp.country.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="country.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="country.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.country.countryNo')" for="country-countryNo"></label>
            <input
              type="text"
              class="form-control"
              name="countryNo"
              id="country-countryNo"
              data-cy="countryNo"
              :class="{ valid: !v$.countryNo.$invalid, invalid: v$.countryNo.$invalid }"
              v-model="v$.countryNo.$model"
            />
            <div v-if="v$.countryNo.$anyDirty && v$.countryNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.countryNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.country.enName')" for="country-enName"></label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="country-enName"
              data-cy="enName"
              :class="{ valid: !v$.enName.$invalid, invalid: v$.enName.$invalid }"
              v-model="v$.enName.$model"
            />
            <div v-if="v$.enName.$anyDirty && v$.enName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.enName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.country.chName')" for="country-chName"></label>
            <input
              type="text"
              class="form-control"
              name="chName"
              id="country-chName"
              data-cy="chName"
              :class="{ valid: !v$.chName.$invalid, invalid: v$.chName.$invalid }"
              v-model="v$.chName.$model"
            />
            <div v-if="v$.chName.$anyDirty && v$.chName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.chName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
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
<script lang="ts" src="./country-update.component.ts"></script>
