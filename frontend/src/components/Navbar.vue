<script setup>
  import KakaoLogin from "./KakaoLogin.vue";
  import {onMounted, ref} from "vue";
  import {useMypageStore} from "@/stores/myPageStore.js";

  const myPageStore = useMypageStore();
  const mypageRes = ref(null); // 초기 상태를 빈 객체로 설정
  let userEmail = ref(null);
  let userImage = ref(null);

  const getMyData = async () =>{
    try{
      const response =  await myPageStore.getMyPage()
      mypageRes.value = response.data;
      userEmail = mypageRes.value.email;
      userImage = mypageRes.value.image;
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
            <a class="nav-link active" aria-current="page" href="/IpoData">공모주 청약 일정</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/ListingShares">상장일 일정</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/mypage" v-if="mypageRes">마이페이지</a>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto" >
          <li class="nav-item-kakao" v-if="!mypageRes" >
            <KakaoLogin />
          </li>
          <li class="nav-item-profile" v-if="mypageRes">
            <img :src="userImage" alt="Profile Image" class="profile-image" />
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>

.profile-image {
  width: 30px; /* 원하는 크기로 조절 */
  height: 30px; /* 원하는 크기로 조절 */
  border-radius: 50%; /* 원형으로 만들기 */
  margin-right: 5px; /* 이미지와 텍스트 간격 조정 */
}

.profile-email {
  display: inline-block; /* 텍스트가 한 줄에 표시되도록 함 */
  vertical-align: middle; /* 이미지와 수직 정렬 */
}

.nav-item-profile {
  display: flex; /* 이미지와 텍스트를 가로로 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
}

</style>
