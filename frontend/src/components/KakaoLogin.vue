<template>
  <div>
    <img src="../assets/img/kakao_login_medium.png"
         @click="goToKakaoLogin"
         alt="kakaoLogin"
         v-if="!isLoginValue">
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import {ref} from "vue";

const router = useRouter();
const isLoginValue = ref(false);

const goToKakaoLogin = async () => {
  window.location.href = 'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=292b2857a22606f949ea630aea4ef0e0&client_secret=LyLPICzrjENT92TXZv937kz5ez4nKYLe&redirect_uri=http://localhost:8080/dev/login/oauth/kakao';
  await router.push('/')
  isLoginValue.value = true;
};

// 로그인 후 홈으로 이동 (특정 로직 필요)
</script>

<style scoped>
/* 스타일 추가 필요 시 여기에 작성 */
</style>
