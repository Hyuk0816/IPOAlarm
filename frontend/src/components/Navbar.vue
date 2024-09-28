<template>
  <nav class="navbar  navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <router-link to="/" class="navbar-brand">공모주 알리미</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item" >
            <router-link to="/IpoData" class="nav-link">공모주 청약 일정</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/ListingShares" class="nav-link">상장일 일정</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/mypage" v-if="userStore.isLoggedIn" class="nav-link">마이페이지</router-link>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto">
          <li class="nav-item-kakao" v-if="!userStore.isLoggedIn">
            <img class="kakaoLogin" src="../assets/img/kakao_login_medium.png" @click="goToKakaoLogin"  alt="kakoLogin">
          </li>
          <li class="nav-item-profile" v-else>
            <img
                :src="userStore.usersData.profile"
                alt="Profile Image"
                class="profile-image"
            />
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import{useKakaoLoginStore} from "@/stores/kakaoLoginStore.js";
import {onBeforeMount, onMounted, ref} from "vue";
import {useUserStore} from "@/stores/usersStores.ts";

const userStore = useUserStore();
const kakaoLoginStroe = useKakaoLoginStore();

const goToKakaoLogin = async (event) => {
  event.preventDefault();
  await kakaoLoginStroe.kakaoLogin();
};
onMounted( async () => {
  // await myPageStore.getMyPage(); // API 호출
  // userImage.value = myPageStore.userImage; // 스토어에서 사용자 이미지 가져오기
  // console.log(userImage.value + " 템플릿 2 @");
});
</script>

<style scoped>
body {
  font-family: 'Roboto', sans-serif; /* 전체 폰트 설정 */
}

.profile-image {
  width: 30px; /* 원하는 크기로 조절 */
  height: 30px; /* 원하는 크기로 조절 */
  border-radius: 50%; /* 원형으로 만들기 */
  margin-right: 5px; /* 이미지와 텍스트 간격 조정 */
  cursor: pointer; /* 클릭 가능한 커서 */
}

.dropdown-menu {
  position: absolute; /* 위치 조정 */
  background-color: white;
  border: 1px solid #ddd;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000; /* 드롭다운이 다른 요소 위에 표시되도록 설정 */
  display: block; /* 드롭다운이 보일 때 */
  min-width: 150px; /* 최소 너비 설정 */
}

.dropdown-item {
  padding: 10px 15px; /* 여백 추가 */
  color: #333; /* 텍스트 색상 */
  text-align: center; /* 중앙 정렬 */
  text-decoration: none; /* 기본 링크 스타일 제거 */
  transition: background-color 0.3s; /* 호버 효과를 부드럽게 전환 */
}

.dropdown-item:hover {
  background-color: #f1f1f1; /* 호버 시 배경 색상 변경 */
}

.nav-item-profile {
  cursor: none;
}

@media (max-width: 768px) {
  .profile-image {
    display: none;
  }

}

</style>
