<template>
  <div class="base-input">
    <label :for="id">{{ label }}</label>
    <input
      :id="id"
      :type="type"
      :placeholder="placeholder"
      :value="value"
      @input="updateValue"
      :title="hint"
      :required="required"
      :maxlength="maxlength"
      :readonly="readonly"
    />
    <small v-if="hint">{{ hint }}</small>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class BaseInput extends Vue {
  @Prop({ required: true }) id!: string;
  @Prop({ required: true }) label!: string;
  @Prop({ required: true }) value!: string;
  @Prop({ default: 'text' }) type!: string;
  @Prop({ default: '' }) placeholder!: string;
  @Prop({ default: '' }) hint!: string;
  @Prop({ default: null }) maxlength!: number;
  @Prop({ default: false }) required!: boolean;
  @Prop({ default: false }) readonly!: boolean;
  updateValue(event: Event) {
    const input = event.target as HTMLInputElement;
    this.$emit('input', input.value);
  }
}
</script>

<style scoped>
.base-input {
  display: flex;
  flex-direction: column;
  margin-bottom: 16px;
}

label {
  font-size: 16px;
  margin-bottom: 8px;
  align-self: flex-start;
}

input {
  padding: 10px;
  font-size: 16px;
  background-color: #f5f8fa;
  border: 1px solid #d1d5db;
  border-radius: 4px;
}

small {
  margin-top: 4px;
  font-size: 12px;
  color: #6b7280;
  align-self: flex-start;
}
</style>
