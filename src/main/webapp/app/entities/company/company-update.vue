<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.company.home.createOrEditLabel"
          data-cy="CompanyCreateUpdateHeading"
          v-text="t$('jhipsterApp.company.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="company.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="company.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.companyNo')" for="company-companyNo"></label>
            <input
              type="text"
              class="form-control"
              name="companyNo"
              id="company-companyNo"
              data-cy="companyNo"
              :class="{ valid: !v$.companyNo.$invalid, invalid: v$.companyNo.$invalid }"
              v-model="v$.companyNo.$model"
            />
            <div v-if="v$.companyNo.$anyDirty && v$.companyNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.companyNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.enName')" for="company-enName"></label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="company-enName"
              data-cy="enName"
              :class="{ valid: !v$.enName.$invalid, invalid: v$.enName.$invalid }"
              v-model="v$.enName.$model"
            />
            <div v-if="v$.enName.$anyDirty && v$.enName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.enName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.chName')" for="company-chName"></label>
            <input
              type="text"
              class="form-control"
              name="chName"
              id="company-chName"
              data-cy="chName"
              :class="{ valid: !v$.chName.$invalid, invalid: v$.chName.$invalid }"
              v-model="v$.chName.$model"
            />
            <div v-if="v$.chName.$anyDirty && v$.chName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.chName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.tel')" for="company-tel"></label>
            <input
              type="text"
              class="form-control"
              name="tel"
              id="company-tel"
              data-cy="tel"
              :class="{ valid: !v$.tel.$invalid, invalid: v$.tel.$invalid }"
              v-model="v$.tel.$model"
            />
            <div v-if="v$.tel.$anyDirty && v$.tel.$invalid">
              <small class="form-text text-danger" v-for="error of v$.tel.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.addr')" for="company-addr"></label>
            <input
              type="text"
              class="form-control"
              name="addr"
              id="company-addr"
              data-cy="addr"
              :class="{ valid: !v$.addr.$invalid, invalid: v$.addr.$invalid }"
              v-model="v$.addr.$model"
            />
            <div v-if="v$.addr.$anyDirty && v$.addr.$invalid">
              <small class="form-text text-danger" v-for="error of v$.addr.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.email')" for="company-email"></label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="company-email"
              data-cy="email"
              :class="{ valid: !v$.email.$invalid, invalid: v$.email.$invalid }"
              v-model="v$.email.$model"
            />
            <div v-if="v$.email.$anyDirty && v$.email.$invalid">
              <small class="form-text text-danger" v-for="error of v$.email.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.company.peopleName')" for="company-peopleName"></label>
            <input
              type="text"
              class="form-control"
              name="peopleName"
              id="company-peopleName"
              data-cy="peopleName"
              :class="{ valid: !v$.peopleName.$invalid, invalid: v$.peopleName.$invalid }"
              v-model="v$.peopleName.$model"
            />
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
<script lang="ts" src="./company-update.component.ts"></script>
