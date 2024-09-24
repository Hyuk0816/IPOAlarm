<template>
  <div class="modal" v-if="isOpen">
    <div class="modal-content">
      <div class="modal-header">
        <h5>{{ title }}</h5>
      </div>
      <div class="modal-body">
        <input v-model="newNickname" placeholder="닉네임" />
        <button type="button" id="nickNameCheckBtn" class="btn btn-secondary" @click="nickNameCheck">중복 확인</button>
        <p v-if="message" class="error-message">{{ message }}</p>
        <slot></slot>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" @click="close">취소</button>
        <button type="button" class="btn btn-primary" @click="editNickName">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useMypageStore } from "@/stores/myPageStore.js";

const newNickname = ref("");
const myPage = useMypageStore();
const isNickNameCheck = ref(false);
const message = ref(null);

const props = defineProps({
  isOpen: Boolean,
  title: String,
  confirmAction: Function,
});

const editNickName = async () => {
  try {
    if (isNickNameCheck.value) {
      const res = await myPage.putNickname(newNickname.value);
      message.value = res.data.message; // 서버에서 반환된 메시지 사용
    } else {
      message.value = "중복확인이 필요합니다.";
    }
  } catch (err) {
    console.error(err);
    message.value = myPage.nickNameErrorMsg ;
  }
};

const nickNameCheck = async () => {
  try {
    const res = await myPage.nickNameCheck(newNickname.value);
    message.value = res.data.statusMsg; // 서버에서 반환된 메시지 사용
    isNickNameCheck.value = true;
  } catch (err) {
    if(err){
      message.value = myPage.nickNameErrorMsg ; // 서버에서 반환된 에러 메시지 사용
      isNickNameCheck.value = false;
    }
    console.error(err)
  }
};

const close = () => {
  emit('update:isOpen', false);
  newNickname.value = ""; // 모달 닫을 때 닉네임 초기화
  message.value = ""; // 메시지 초기화
};

const emit = defineEmits();
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

.error-message {
  color: red; /* 에러 메시지 빨간색 */
  font-size: 12px; /* 에러 메시지 크기 */
}

#nickNameCheckBtn{
  padding-left: 20px;
}


</style>
