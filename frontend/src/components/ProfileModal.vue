<template>
  <div class="modal" v-if="isOpen">
    <div class="modal-content">
      <div class="modal-header">
        <h5>{{ title }}</h5>
      </div>
      <div class="modal-body">
        <p class="info">250 * 250에 최적화 되어있습니다.</p>
        <input type="file" multiple @change="handleFileChange" accept="image/*" />
        <slot></slot>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" @click="close">취소</button>
        <button type="button" class="btn btn-primary" @click="putProfilePicture">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {useMypageStore} from "@/stores/myPageStore.js";

const myPageStore = useMypageStore();
const profileImage = ref(null);

const props = defineProps({
  isOpen: Boolean,
  title: String,
  confirmAction: Function,
});

const emit = defineEmits();

const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    profileImage.value = file; // 올바르게 파일 할당
  } else {
    console.log("파일이 선택되지 않았습니다.");
  }
};

const putProfilePicture = async () => {
  if (profileImage.value) {
    const res = await myPageStore.putProfile(profileImage.value); // 파일 전송
    console.log(res); // 결과 확인
  } else {
    console.error("전송할 파일이 없습니다.");
  }
};

const close = () => {
  emit('update:isOpen', false);
  profileImage.value = null; // 모달 닫을 때 파일 초기화
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
.info{
  font-size: 0.8rem; /* 글자 크기 조정 */
  color: gray; /* 글자 색상 조정 */
}
</style>
