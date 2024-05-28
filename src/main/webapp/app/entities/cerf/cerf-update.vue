<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.cerf.home.createOrEditLabel"
          data-cy="CerfCreateUpdateHeading"
          v-text="t$('jhipsterApp.cerf.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="cerf.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="cerf.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerf.cerfNo')" for="cerf-cerfNo"></label>
            <input
              type="text"
              class="form-control"
              name="cerfNo"
              id="cerf-cerfNo"
              data-cy="cerfNo"
              :class="{ valid: !v$.cerfNo.$invalid, invalid: v$.cerfNo.$invalid }"
              v-model="v$.cerfNo.$model"
            />
            <div v-if="v$.cerfNo.$anyDirty && v$.cerfNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.cerfNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerf.cerfVer')" for="cerf-cerfVer"></label>
            <input
              type="text"
              class="form-control"
              name="cerfVer"
              id="cerf-cerfVer"
              data-cy="cerfVer"
              :class="{ valid: !v$.cerfVer.$invalid, invalid: v$.cerfVer.$invalid }"
              v-model="v$.cerfVer.$model"
            />
            <div v-if="v$.cerfVer.$anyDirty && v$.cerfVer.$invalid">
              <small class="form-text text-danger" v-for="error of v$.cerfVer.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerf.status')" for="cerf-status"></label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="cerf-status"
              data-cy="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
            />
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerf.pdf')" for="cerf-pdf"></label>
            <div>
              <div v-if="cerf.pdf" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(cerf.pdfContentType, cerf.pdf)" v-text="t$('entity.action.open')"></a><br />
                <span class="pull-left">{{ cerf.pdfContentType }}, {{ byteSize(cerf.pdf) }}</span>
                <button
                  type="button"
                  v-on:click="
                    cerf.pdf = null;
                    cerf.pdfContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_pdf" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_pdf"
                id="file_pdf"
                style="display: none"
                data-cy="pdf"
                v-on:change="setFileData($event, cerf, 'pdf', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="pdf"
              id="cerf-pdf"
              data-cy="pdf"
              :class="{ valid: !v$.pdf.$invalid, invalid: v$.pdf.$invalid }"
              v-model="v$.pdf.$model"
            />
            <input type="hidden" class="form-control" name="pdfContentType" id="cerf-pdfContentType" v-model="cerf.pdfContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerf.issuDt')" for="cerf-issuDt"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="cerf-issuDt"
                  v-model="v$.issuDt.$model"
                  name="issuDt"
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
                id="cerf-issuDt"
                data-cy="issuDt"
                type="text"
                class="form-control"
                name="issuDt"
                :class="{ valid: !v$.issuDt.$invalid, invalid: v$.issuDt.$invalid }"
                v-model="v$.issuDt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.cerf.expDt')" for="cerf-expDt"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="cerf-expDt"
                  v-model="v$.expDt.$model"
                  name="expDt"
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
                id="cerf-expDt"
                data-cy="expDt"
                type="text"
                class="form-control"
                name="expDt"
                :class="{ valid: !v$.expDt.$invalid, invalid: v$.expDt.$invalid }"
                v-model="v$.expDt.$model"
              />
            </b-input-group>
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
<script lang="ts" src="./cerf-update.component.ts"></script>
