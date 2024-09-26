<template>
  <div>
    <img src="../assets/img/kakao_login_medium.png"
         @click="goToKakaoLogin"
         alt="kakaoLogin">
  </div>
</template>

<script setup lang="js">
import {useMypageStore} from "@/stores/myPageStore.js";

const redirectUri = import.meta.env.VITE_REDIRECT_URI
const clientId = import.meta.env.VITE_CLIENT_ID
console.log(clientId)
const clientSecret = import.meta.env.VITE_CLIENT_SECRET
const myPage = useMypageStore();


const goToKakaoLogin = async () => {
  window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${clientId}&client_secret=${clientSecret}&redirect_uri=${redirectUri}`;
  const res =  await myPage.getMyPage()
  console.log(res.data.nickName + " 카카오 로그인 시")
};

// 로그인 후 홈으로 이동 (특정 로직 필요)
</script>

<style scoped>
/* 스타일 추가 필요 시 여기에 작성 */
</style>
