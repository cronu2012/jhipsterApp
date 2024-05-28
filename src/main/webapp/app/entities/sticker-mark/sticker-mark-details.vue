<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="stickerMark">
        <h2 class="jh-entity-heading" data-cy="stickerMarkDetailsHeading">
          <span v-text="t$('jhipsterApp.stickerMark.detail.title')"></span> {{ stickerMark.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.stickerMark.relType')"></span>
          </dt>
          <dd>
            <span>{{ stickerMark.relType }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.stickerMark.sticker')"></span>
          </dt>
          <dd>
            <div v-if="stickerMark.sticker">
              <router-link :to="{ name: 'StickerView', params: { stickerId: stickerMark.sticker.id } }">{{
                stickerMark.sticker.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.stickerMark.mark')"></span>
          </dt>
          <dd>
            <div v-if="stickerMark.mark">
              <router-link :to="{ name: 'MarkView', params: { markId: stickerMark.mark.id } }">{{ stickerMark.mark.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="stickerMark.id"
          :to="{ name: 'StickerMarkEdit', params: { stickerMarkId: stickerMark.id } }"
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

<script lang="ts" src="./sticker-mark-details.component.ts"></script>
