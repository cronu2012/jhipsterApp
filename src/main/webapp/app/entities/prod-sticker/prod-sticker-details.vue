<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="prodSticker">
        <h2 class="jh-entity-heading" data-cy="prodStickerDetailsHeading">
          <span v-text="t$('jhipsterApp.prodSticker.detail.title')"></span> {{ prodSticker.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.prodSticker.relType')"></span>
          </dt>
          <dd>
            <span>{{ prodSticker.relType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prodSticker.prod')"></span>
          </dt>
          <dd>
            <div v-if="prodSticker.prod">
              <router-link :to="{ name: 'ProdView', params: { prodId: prodSticker.prod.id } }">{{ prodSticker.prod.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.prodSticker.sticker')"></span>
          </dt>
          <dd>
            <div v-if="prodSticker.sticker">
              <router-link :to="{ name: 'StickerView', params: { stickerId: prodSticker.sticker.id } }">{{
                prodSticker.sticker.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="prodSticker.id"
          :to="{ name: 'ProdStickerEdit', params: { prodStickerId: prodSticker.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod-sticker-details.component.ts"></script>
