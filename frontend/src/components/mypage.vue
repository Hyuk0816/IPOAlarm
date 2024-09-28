<script setup lang="js">
 import {useMypageStore} from "@/stores/myPageStore.js";
 import {onMounted, ref} from "vue";
 import ProfileModal from "@/components/ProfileModal.vue";
 import NicknameModal from "@/components/NicknameModal.vue";
 import {useUserStore} from "@/stores/usersStores.ts";
 import router from "@/router/router.js";

 const mypage = useMypageStore();
 const response = ref(null); // response를 ref로 초기화
 const userStore = useUserStore();

 const ipoAlarmCount = ref('');
 const listingAlarmCount = ref('');
 const isModalOpen = ref(false);
 const isProfileModalOpen = ref(false);
 const isNickNameModalOpen = ref(false);

 const getData = async ()=>{
   response.value = await mypage.getMyPage()
 }
 const formatDate = (dateString) => {
   const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
   return new Date(dateString).toLocaleDateString(undefined, options);
 };

 const myIpoAlarmCount = async () =>{
   ipoAlarmCount.value = await mypage.myIpoAlarmCount();
 }

 const myListingAlarmCount = async () => {
   listingAlarmCount.value = await mypage.myListingSharesCount();
 }

 const openModal = () => {
   isModalOpen.value = true;
 };

 const openProfileModal = () => {
   isProfileModalOpen.value=true
 }

 const openNickNameModal = () => {
   isNickNameModalOpen.value=true
 }

 const logout = async () => {
   await userStore.logout();
   window.location.href = '/'
 }

 onMounted(() => {
   getData();
   myIpoAlarmCount();
   myListingAlarmCount();

 })
</script>

<template>
  <div class="container mt-5">
    <div class="row text-center">
      <div class="col-md-12">
        <img :src="response.data.image" alt="User Image" class="rounded-circle img-fluid mb-3" style="width: 250px; height: 250px;">
        <p class="edit-profile" @click="openProfileModal">프로필 변경</p> <!-- 추가된 부분 -->
        <ProfileModal
          :isOpen="isProfileModalOpen"
          title="프로필 사진 변경"
        @update:isOpen="isProfileModalOpen=$event"
        />
        <table class="table">
          <tr>
            <td>닉네임:</td>
            <td class="text-container">{{ response.data.nickName }}</td>
            <td class="edit-nickname fw-light" @click="openNickNameModal">닉네임 변경</td>
          </tr>
          <NicknameModal
            :isOpen="isNickNameModalOpen"
            title="닉네임 변경"
            @update:isOpen="isNickNameModalOpen=$event"/>
          <tr>
            <td>공모주 알람:</td>
            <td class="text-container">{{ipoAlarmCount}}회</td>
          </tr>
          <tr>
            <td>상장일 알람:</td>
            <td class="text-container">{{listingAlarmCount}}회</td>
          </tr>
        </table>
        <div class="logout-container">
          <button type="button" class="btn btn-warning" id="logout-text" @click="logout">로그아웃</button>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
          <li class="nav-item" role="presentation">
            <a class="nav-link active" id="alarms-tab" data-bs-toggle="tab" href="#alarms" role="tab" aria-controls="alarms" aria-selected="true">공모주 알람</a>
          </li>
          <li class="nav-item" role="presentation">
            <a class="nav-link" id="listing-tab" data-bs-toggle="tab" href="#listing" role="tab" aria-controls="listing" aria-selected="false">상장일 알람</a>
          </li>
        </ul>
        <div class="tab-content" id="myTabContent">
          <div class="tab-pane fade show active" id="alarms" role="tabpanel" aria-labelledby="alarms-tab">
            <table class="table table-striped mt-3">
              <thead>
              <tr>
                <th>공모주 이름</th>
                <th>공모 가격</th>
                <th>확정 가격</th>
                <th>주간사</th>
                <th>공모 시작일</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="alarm in response.data.myAlarm" :key="alarm.ipoName">
                <td>{{ alarm.ipoName }}</td>
                <td>{{ alarm.ipoPrice }}</td>
                <td>{{ alarm.confirmPrice }}</td>
                <td>{{ alarm.securities }}</td>
                <td>{{ formatDate(alarm.startDate) }}</td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="tab-pane fade" id="listing" role="tabpanel" aria-labelledby="listing-tab">
            <table class="table table-striped mt-3">
              <thead>
              <tr>
                <th>상장주 이름</th>
                <th>상장 날짜</th>
                <th>공모 확정 가격</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="listing in response.data.myListingShares" :key="listing.listingShares">
                <td>{{ listing.listingShares }}</td>
                <td>{{ formatDate(listing.listingDate) }}</td>
                <td>{{ listing.offeringPrice }}</td>
              </tr>
              </tbody>
            </table>
            <div v-if="response.data.myListingShares.length === 0" class="mt-3">
              <p>No listing shares available.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.text-center {
  text-align: center;
}
.edit-profile {
  cursor: pointer;
  font-size: 0.8rem; /* 글자 크기 조정 */
  color: gray; /* 글자 색상 조정 */
  margin-top: -10px; /* 사진과의 간격 조정 */
}

.edit-nickname {
  cursor: pointer;
  font-size: 0.8rem; /* 글자 크기 조정 */
  color: gray; /* 글자 색상 조정 */
  padding-left: 20px;
}
.logout-container{
  margin-left: 100px;
  padding-top: 10px;
  padding-bottom: 10px;
  display: flex;
}
#logout-text{
  cursor: pointer;
  font-size: 0.8rem;
  color: brown;
}

</style>