<template>
  <div>
    <img src="../assets/img/kakao_login_medium.png" @click="goToKakaoLogin" alt="kakoLogin"></img>
  </div>
</template>

<script setup>
import {almostWhole} from "chart.js/helpers";

import router from "../router/router.js";

const goToKakaoLogin = async () => {
  // 카카오 로그인 URL
  // 카카오 로그인 페이지로 이동
  const kakaoLoginUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=292b2857a22606f949ea630aea4ef0e0&client_secret=LyLPICzrjENT92TXZv937kz5ez4nKYLe&redirect_uri=http://localhost:8080/dev/login/oauth/kakao`;
  window.location.href = kakaoLoginUrl; // 해당 URL로 이동
  await router.push({name:"home"})
};
</script>

<style scoped>
/* 스타일 추가 필요 시 여기에 작성 */
</style>
