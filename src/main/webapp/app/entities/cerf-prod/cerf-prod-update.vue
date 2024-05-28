<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.cerfProd.home.createOrEditLabel"
          data-cy="CerfProdCreateUpdateHeading"
          v-text="t$('jhipsterApp.cerfProd.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="cerfProd.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="cerfProd.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerfProd.relType')" for="cerf-prod-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="cerf-prod-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerfProd.cerf')" for="cerf-prod-cerf"></label>
            <select class="form-control" id="cerf-prod-cerf" data-cy="cerf" name="cerf" v-model="cerfProd.cerf">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cerfProd.cerf && cerfOption.id === cerfProd.cerf.id ? cerfProd.cerf : cerfOption"
                v-for="cerfOption in cerfs"
                :key="cerfOption.id"
              >
                {{ cerfOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerfProd.prod')" for="cerf-prod-prod"></label>
            <select class="form-control" id="cerf-prod-prod" data-cy="prod" name="prod" v-model="cerfProd.prod">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cerfProd.prod && prodOption.id === cerfProd.prod.id ? cerfProd.prod : prodOption"
                v-for="prodOption in prods"
                :key="prodOption.id"
              >
                {{ prodOption.id }}
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
<script lang="ts" src="./cerf-prod-update.component.ts"></script>
