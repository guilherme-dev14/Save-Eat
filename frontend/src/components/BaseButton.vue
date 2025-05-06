<template>
  <button class="base-button" :type="type">
    <slot />
  </button>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class BaseButton extends Vue {
  @Prop({ default: 'button' }) type!: string;
}
</script>

<style scoped>
.base-button {
  padding: 12px;
  background-color: #2db83d;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 30px;
}

.base-button:hover {
  background-color: #2ba839;
}
</style>
