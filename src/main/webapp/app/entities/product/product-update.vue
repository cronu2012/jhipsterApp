<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.product.home.createOrEditLabel"
          data-cy="ProductCreateUpdateHeading"
          v-text="t$('jhipsterApp.product.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="product.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="product.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.mainTitle')" for="product-mainTitle"></label>
            <input
              type="text"
              class="form-control"
              name="mainTitle"
              id="product-mainTitle"
              data-cy="mainTitle"
              :class="{ valid: !v$.mainTitle.$invalid, invalid: v$.mainTitle.$invalid }"
              v-model="v$.mainTitle.$model"
            />
            <div v-if="v$.mainTitle.$anyDirty && v$.mainTitle.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainTitle.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.subTitle')" for="product-subTitle"></label>
            <input
              type="text"
              class="form-control"
              name="subTitle"
              id="product-subTitle"
              data-cy="subTitle"
              :class="{ valid: !v$.subTitle.$invalid, invalid: v$.subTitle.$invalid }"
              v-model="v$.subTitle.$model"
            />
            <div v-if="v$.subTitle.$anyDirty && v$.subTitle.$invalid">
              <small class="form-text text-danger" v-for="error of v$.subTitle.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.description')" for="product-description"></label>
            <textarea
              class="form-control"
              name="description"
              id="product-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.price')" for="product-price"></label>
            <input
              type="text"
              class="form-control"
              name="price"
              id="product-price"
              data-cy="price"
              :class="{ valid: !v$.price.$invalid, invalid: v$.price.$invalid }"
              v-model="v$.price.$model"
            />
            <div v-if="v$.price.$anyDirty && v$.price.$invalid">
              <small class="form-text text-danger" v-for="error of v$.price.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.createBy')" for="product-createBy"></label>
            <input
              type="text"
              class="form-control"
              name="createBy"
              id="product-createBy"
              data-cy="createBy"
              :class="{ valid: !v$.createBy.$invalid, invalid: v$.createBy.$invalid }"
              v-model="v$.createBy.$model"
            />
            <div v-if="v$.createBy.$anyDirty && v$.createBy.$invalid">
              <small class="form-text text-danger" v-for="error of v$.createBy.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.createdTime')" for="product-createdTime"></label>
            <div class="d-flex">
              <input
                id="product-createdTime"
                data-cy="createdTime"
                type="datetime-local"
                class="form-control"
                name="createdTime"
                :class="{ valid: !v$.createdTime.$invalid, invalid: v$.createdTime.$invalid }"
                :value="convertDateTimeFromServer(v$.createdTime.$model)"
                @change="updateInstantField('createdTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.updatedTime')" for="product-updatedTime"></label>
            <div class="d-flex">
              <input
                id="product-updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                class="form-control"
                name="updatedTime"
                :class="{ valid: !v$.updatedTime.$invalid, invalid: v$.updatedTime.$invalid }"
                :value="convertDateTimeFromServer(v$.updatedTime.$model)"
                @change="updateInstantField('updatedTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.product.status')" for="product-status"></label>
            <input
              type="number"
              class="form-control"
              name="status"
              id="product-status"
              data-cy="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model.number="v$.status.$model"
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
<script lang="ts" src="./product-update.component.ts"></script>
