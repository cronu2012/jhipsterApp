<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.prodStd.home.createOrEditLabel"
          data-cy="ProdStdCreateUpdateHeading"
          v-text="t$('jhipsterApp.prodStd.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="prodStd.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="prodStd.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodStd.relType')" for="prod-std-relType"></label>
            <input
              type="text"
              class="form-control"
              name="relType"
              id="prod-std-relType"
              data-cy="relType"
              :class="{ valid: !v$.relType.$invalid, invalid: v$.relType.$invalid }"
              v-model="v$.relType.$model"
            />
            <div v-if="v$.relType.$anyDirty && v$.relType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.relType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodStd.prod')" for="prod-std-prod"></label>
            <select class="form-control" id="prod-std-prod" data-cy="prod" name="prod" v-model="prodStd.prod">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="prodStd.prod && prodOption.id === prodStd.prod.id ? prodStd.prod : prodOption"
                v-for="prodOption in prods"
                :key="prodOption.id"
              >
                {{ prodOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prodStd.std')" for="prod-std-std"></label>
            <select class="form-control" id="prod-std-std" data-cy="std" name="std" v-model="prodStd.std">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="prodStd.std && stdOption.id === prodStd.std.id ? prodStd.std : stdOption"
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
<script lang="ts" src="./prod-std-update.component.ts"></script>
