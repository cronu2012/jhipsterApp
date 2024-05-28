<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.prod.home.createOrEditLabel"
          data-cy="ProdCreateUpdateHeading"
          v-text="t$('jhipsterApp.prod.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="prod.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="prod.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prod.prodNo')" for="prod-prodNo"></label>
            <input
              type="text"
              class="form-control"
              name="prodNo"
              id="prod-prodNo"
              data-cy="prodNo"
              :class="{ valid: !v$.prodNo.$invalid, invalid: v$.prodNo.$invalid }"
              v-model="v$.prodNo.$model"
            />
            <div v-if="v$.prodNo.$anyDirty && v$.prodNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.prodNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prod.enName')" for="prod-enName"></label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="prod-enName"
              data-cy="enName"
              :class="{ valid: !v$.enName.$invalid, invalid: v$.enName.$invalid }"
              v-model="v$.enName.$model"
            />
            <div v-if="v$.enName.$anyDirty && v$.enName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.enName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prod.chName')" for="prod-chName"></label>
            <input
              type="text"
              class="form-control"
              name="chName"
              id="prod-chName"
              data-cy="chName"
              :class="{ valid: !v$.chName.$invalid, invalid: v$.chName.$invalid }"
              v-model="v$.chName.$model"
            />
            <div v-if="v$.chName.$anyDirty && v$.chName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.chName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prod.hsCode')" for="prod-hsCode"></label>
            <input
              type="text"
              class="form-control"
              name="hsCode"
              id="prod-hsCode"
              data-cy="hsCode"
              :class="{ valid: !v$.hsCode.$invalid, invalid: v$.hsCode.$invalid }"
              v-model="v$.hsCode.$model"
            />
            <div v-if="v$.hsCode.$anyDirty && v$.hsCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.hsCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.prod.cccCode')" for="prod-cccCode"></label>
            <input
              type="text"
              class="form-control"
              name="cccCode"
              id="prod-cccCode"
              data-cy="cccCode"
              :class="{ valid: !v$.cccCode.$invalid, invalid: v$.cccCode.$invalid }"
              v-model="v$.cccCode.$model"
            />
            <div v-if="v$.cccCode.$anyDirty && v$.cccCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.cccCode.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./prod-update.component.ts"></script>
