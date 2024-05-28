<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="t$('jhipsterApp.product.detail.title')"></span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.product.mainTitle')"></span>
          </dt>
          <dd>
            <span>{{ product.mainTitle }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.subTitle')"></span>
          </dt>
          <dd>
            <span>{{ product.subTitle }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.description')"></span>
          </dt>
          <dd>
            <span>{{ product.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.price')"></span>
          </dt>
          <dd>
            <span>{{ product.price }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.createBy')"></span>
          </dt>
          <dd>
            <span>{{ product.createBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.createdTime')"></span>
          </dt>
          <dd>
            <span v-if="product.createdTime">{{ formatDateLong(product.createdTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.updatedTime')"></span>
          </dt>
          <dd>
            <span v-if="product.updatedTime">{{ formatDateLong(product.updatedTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.product.status')"></span>
          </dt>
          <dd>
            <span>{{ product.status }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
