<template>
  <div>
    <img src="../assets/img/kakao_login_medium.png"
         @click="goToKakaoLogin"
         alt="kakaoLogin">
  </div>
</template>

<script setup>

const goToKakaoLogin = async () => {
  window.location.href = 'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=4273ed90c97fada8f1dedfc1222c3466&client_secret=lX1BBKnPhoN1hoalZ7XLdfml8UF74rpd&redirect_uri=http://localhost:8080/dev/login/oauth/kakao';
};

// 로그인 후 홈으로 이동 (특정 로직 필요)
</script>

<style scoped>
/* 스타일 추가 필요 시 여기에 작성 */
</style>
