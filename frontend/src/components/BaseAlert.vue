<template>
    <transition name="fade">
        <div v-if="visible" :class="['base-alert', type]">
        <span>{{ message }}</span>
        <button class="close" @click="visible = false">&times;</button>
        </div>
    </transition>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class BaseAlert extends Vue {
    @Prop({ required: true }) message!: string;
    @Prop({ default: 'info' }) type!: 'success' | 'error' | 'warning' | 'info';

    visible: boolean = true;

    mounted() {
        setTimeout(() => {
        this.visible = false;
        }, 4000);
    }
}
</script>

<style>
.base-alert {
    position: fixed;
    top: 24px;
    right: 24px;
    z-index: 1000;
    padding: 12px 20px;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    color: white;
    font-weight: 500;
    display: flex;
    align-items: center;
    justify-content: space-between;
    min-width: 280px;
    max-width: 90vw;
}

.success {
    background-color: #16a34a;
}

.error {
    background-color: #dc2626;
}

.warning {
    background-color: #f59e0b;
}

.info {
    background-color: #2563eb;
}

.close {
    background: none;
    border: none;
    color: white;
    font-size: 18px;
    cursor: pointer;
    margin-left: 16px;
}

.fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s ease;
}
.fade-enter, .fade-leave-to {
    opacity: 0;
}
</style>
