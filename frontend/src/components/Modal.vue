<template>
  <div class="modal" v-if="isOpen">
    <div class="modal-content">
      <div class="modal-header">
        <h5>{{ title }}</h5>
      </div>
      <div class="modal-body">
        <slot></slot>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" @click="close">취소</button>
        <button type="button" class="btn btn-primary" @click="confirm">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  isOpen: Boolean,
  title: String,
  confirmAction: Function,
});

const emit = defineEmits();

const close = () => {
  emit('update:isOpen', false);
};

const confirm = () => {
  if (props.confirmAction) {
    props.confirmAction();
  }
  close();
};
</script>

<style scoped>
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  max-width: 400px; /* 최대 너비 설정 */
  max-height: 80%; /* 최대 높이 설정 */
  width: 100%; /* 너비를 100%로 설정 */
  overflow: auto; /* 내용이 넘칠 경우 스크롤 가능 */
}
</style>
