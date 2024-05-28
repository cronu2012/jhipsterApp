<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="mark">
        <h2 class="jh-entity-heading" data-cy="markDetailsHeading">
          <span v-text="t$('jhipsterApp.mark.detail.title')"></span> {{ mark.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.mark.markNo')"></span>
          </dt>
          <dd>
            <span>{{ mark.markNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.mark.enName')"></span>
          </dt>
          <dd>
            <span>{{ mark.enName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.mark.chName')"></span>
          </dt>
          <dd>
            <span>{{ mark.chName }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.mark.img')"></span>
          </dt>
          <dd>
            <div v-if="mark.img">
              <a v-on:click="openFile(mark.imgContentType, mark.img)">
                <img v-bind:src="'data:' + mark.imgContentType + ';base64,' + mark.img" style="max-width: 100%" alt="mark" />
              </a>
              {{ mark.imgContentType }}, {{ byteSize(mark.img) }}
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="mark.id" :to="{ name: 'MarkEdit', params: { markId: mark.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./mark-details.component.ts"></script>
