<template>
  <div>
    <img src="../assets/img/kakao_login_medium.png"
         @click="goToKakaoLogin"
         alt="kakaoLogin"
        >
  </div>
</template>

<script setup lang="js">
import {useUserStore} from "@/stores/usersStores.ts";
import {useKakaoLoginStore} from "@/stores/kakaoLoginStore.js";
import {onMounted, ref} from "vue";

const kakaoLoginStore = useKakaoLoginStore();
const userStore = useUserStore();
const res = ref(null);
const userProfile = ref(null);

const goToKakaoLogin = async (event) => {
  event.preventDefault();
  await kakaoLoginStore.kakaoLogin();
};


onMounted(() =>{
})
</script>

<style scoped>
/* 스타일 추가 필요 시 여기에 작성 */
</style>
