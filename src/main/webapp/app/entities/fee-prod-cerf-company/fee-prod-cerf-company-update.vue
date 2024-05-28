<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.feeProdCerfCompany.home.createOrEditLabel"
          data-cy="FeeProdCerfCompanyCreateUpdateHeading"
          v-text="t$('jhipsterApp.feeProdCerfCompany.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="feeProdCerfCompany.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="feeProdCerfCompany.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.feeProdCerfCompany.fee')" for="fee-prod-cerf-company-fee"></label>
            <input
              type="number"
              class="form-control"
              name="fee"
              id="fee-prod-cerf-company-fee"
              data-cy="fee"
              :class="{ valid: !v$.fee.$invalid, invalid: v$.fee.$invalid }"
              v-model.number="v$.fee.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApp.feeProdCerfCompany.feeType')"
              for="fee-prod-cerf-company-feeType"
            ></label>
            <input
              type="text"
              class="form-control"
              name="feeType"
              id="fee-prod-cerf-company-feeType"
              data-cy="feeType"
              :class="{ valid: !v$.feeType.$invalid, invalid: v$.feeType.$invalid }"
              v-model="v$.feeType.$model"
            />
            <div v-if="v$.feeType.$anyDirty && v$.feeType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.feeType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.feeProdCerfCompany.feeDt')" for="fee-prod-cerf-company-feeDt"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="fee-prod-cerf-company-feeDt"
                  v-model="v$.feeDt.$model"
                  name="feeDt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="fee-prod-cerf-company-feeDt"
                data-cy="feeDt"
                type="text"
                class="form-control"
                name="feeDt"
                :class="{ valid: !v$.feeDt.$invalid, invalid: v$.feeDt.$invalid }"
                v-model="v$.feeDt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.feeProdCerfCompany.prod')" for="fee-prod-cerf-company-prod"></label>
            <select class="form-control" id="fee-prod-cerf-company-prod" data-cy="prod" name="prod" v-model="feeProdCerfCompany.prod">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  feeProdCerfCompany.prod && prodOption.id === feeProdCerfCompany.prod.id ? feeProdCerfCompany.prod : prodOption
                "
                v-for="prodOption in prods"
                :key="prodOption.id"
              >
                {{ prodOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.feeProdCerfCompany.cerf')" for="fee-prod-cerf-company-cerf"></label>
            <select class="form-control" id="fee-prod-cerf-company-cerf" data-cy="cerf" name="cerf" v-model="feeProdCerfCompany.cerf">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  feeProdCerfCompany.cerf && cerfOption.id === feeProdCerfCompany.cerf.id ? feeProdCerfCompany.cerf : cerfOption
                "
                v-for="cerfOption in cerfs"
                :key="cerfOption.id"
              >
                {{ cerfOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApp.feeProdCerfCompany.company')"
              for="fee-prod-cerf-company-company"
            ></label>
            <select
              class="form-control"
              id="fee-prod-cerf-company-company"
              data-cy="company"
              name="company"
              v-model="feeProdCerfCompany.company"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  feeProdCerfCompany.company && companyOption.id === feeProdCerfCompany.company.id
                    ? feeProdCerfCompany.company
                    : companyOption
                "
                v-for="companyOption in companies"
                :key="companyOption.id"
              >
                {{ companyOption.id }}
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
<script lang="ts" src="./fee-prod-cerf-company-update.component.ts"></script>
