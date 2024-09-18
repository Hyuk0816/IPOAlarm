<script setup>
  import KakaoLogin from "./KakaoLogin.vue";
  import {onMounted, ref} from "vue";
  import {useMypageStore} from "@/stores/myPageStore.js";

  const myPageStore = useMypageStore();
  const mypageRes = ref(null); // 초기 상태를 빈 객체로 설정
  const getMyData = async () =>{
    try{
      const response =  await myPageStore.getMyPage()
      mypageRes.value = response.data;
    }catch (err){
      console.error(err)
    }

  }
  onMounted(() => {
    getMyData()
  })
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="/">공모주 알리미</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="/IpoData">IPO 일정</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/ListingShares">상장일 일정</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/mypage">마이페이지</a>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto" >
          <li class="nav-item-kakao" v-if="!mypageRes" >
            <KakaoLogin />
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>

</style>
