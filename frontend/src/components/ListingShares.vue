<template>
  <div>
    <div class="logo-container">
      <img class="logo" src="../assets/img/listind_logo.png" alt="listingLogo"></img>
    </div>
    <div class="search-container">
      <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" @click="toggleFilter" aria-expanded="false">
          검색 필터 {{ isFilterOpen ? '닫기' : '열기' }}
        </button>
        <div v-if="isFilterOpen" class="filter-menu">
          <ul class="list-group">
            <li class="list-group-item">
              <label for="searchName">이름:</label>
              <input v-model="searchName" placeholder="이름 검색" class="form-control-sm" type="search" aria-label="Search"/>
            </li>
            <li class="list-group-item">
              <label for="listingStartDate">상장 시작일:</label>
              <input type="date" v-model="listingStartDate" class="form-control-sm" />
            </li>
            <li class="list-group-item">
              <label for="listingEndDate">상장 종료일:</label>
              <input type="date" v-model="listingEndDate" class="form-control-sm" />
            </li>
            <li class="list-group-item">
              <label for="pageSize">사이즈:</label>
              <select v-model="pageSize" class="form-select form-select-sm" @change="searchData">
                <option value="10">10</option>
                <option value="20" selected>20</option>
                <option value="30" >30</option>
                <option value="50">50</option>
              </select>
            </li>
            <li class="list-group-item">
              <button @click="searchData" class="btn btn-primary">검색</button>
              <button @click="resetFilters" class="btn btn-secondary">초기화</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="table-responsive">
      <table class="table table-striped table-bordered table-hover">
        <thead class="thead-light">
        <tr>
          <th>이름</th>
          <th>상장일</th>
          <th>현재가</th>
          <th>변동률 (전일)</th>
          <th>공모가</th>
          <th>공모가 대비 변동률</th>
          <th>장게시일 가격</th>
          <th>변동률 (장 게시 가격)</th>
          <th>장 게시 마감가격</th>
          <th>알림신청</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in paginatedData" :key="index">
          <td @click="goToDetailPage(item.ipoName)" class="link-item">{{ item.ipoName }}</td>
          <td>{{ formatDate(item.listingDate) }}</td>
          <td>{{ item.currentPrice }}</td>
          <td>{{ item.changeRatePrevious }}</td>
          <td>{{ item.offeringPrice }}</td>
          <td>{{ item.changeRateOfferingPrice }}</td>
          <td>{{ item.openingPrice }}</td>
          <td>{{ item.changeRateOpeningToOfferingPrice }}</td>
          <td>{{ item.closingPriceFirstDay }}</td>
          <td class="text-center">
            <button class="btn btn-danger btn-sm" @click="openModal(item)">신청</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination">
      <a href="#" class="first-page" @click.prevent="goToPage(0)" :class="{ disabled: currentPage === 0 }">First</a>
      <a href="#" class="prev-page" @click.prevent="prevPage" :class="{ disabled: currentPage === 0 }">Prev</a>

      <template v-if="totalPages > 5">
        <span v-if="currentPage > 3">...</span>
        <a v-if="currentPage > 2" @click.prevent="goToPage(currentPage - 2)">{{ currentPage - 1 }}</a>
        <a v-if="currentPage > 1" @click.prevent="goToPage(currentPage - 1)">{{ currentPage }}</a>
        <a class="active">{{ currentPage + 1 }}</a>
        <a v-if="currentPage < totalPages - 1" @click.prevent="goToPage(currentPage + 1)">{{ currentPage + 2 }}</a>
        <a v-if="currentPage < totalPages - 2" @click.prevent="goToPage(currentPage + 2)">{{ currentPage + 3 }}</a>
        <span v-if="currentPage < totalPages - 2">...</span>
      </template>

      <template v-else>
        <a v-for="page in totalPages" :key="page" href="#"
           @click.prevent="goToPage(page - 1)"
           :class="{ active: currentPage === page - 1 }">{{ page }}</a>
      </template>

      <a href="#" class="next-page" @click.prevent="nextPage" :class="{ disabled: currentPage === totalPages }">Next</a>
      <a href="#" class="last-page" @click.prevent="goToPage(totalPages - 1)" :class="{ disabled: currentPage === totalPages }">Last</a>
    </div>
    <!-- 모달 -->
    <modal
        :isOpen="isModalOpen"
        title="알람 신청"
        @update:isOpen="isModalOpen = $event"
        :confirmAction="submitAlarm"
    >
      <p>{{ selectedItem.value ? selectedItem.value : '' }} 알람 신청하시겠습니까?</p>
    </modal>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from '../plugin/axios.js'
import Modal from './Modal.vue'; // 모달 컴포넌트 임포트
import {LISTING_SHARES_ALARM} from "../api/apiPoints.js"
import router from "@/router/router.js";
import {useKakaoCalenderStore} from "@/stores/kakaoCalenderStore.js";
import {useUserStore} from "@/stores/usersStores.ts";
import {API_BASE_URL} from "../api/apiPoints.js";

const listingData = ref([]);
const searchName = ref('');
const listingStartDate = ref('');
const listingEndDate = ref('');
const pageSize = ref(30);
const currentPage = ref(0);
const totalPages = ref(0);
const isFilterOpen = ref(false);
const isModalOpen = ref(false);
const selectedItem = ref(null); // 선택된 아이템 저장

const kakaoCalenderStore = useKakaoCalenderStore();
const userStore = useUserStore();
const fetchData = async (page) => {
  const params = {
    page,
    size: pageSize.value,
  };

  if (searchName.value) {
    params.ipoName = searchName.value;
  }
  if (listingStartDate.value) {
    params.listingStartDate = listingStartDate.value;
  }
  if (listingEndDate.value) {
    params.listingEndDate = listingEndDate.value;
  }

  const queryString = new URLSearchParams(params).toString();

  try {
    const response = await axios.get(`/api/listing_shares/data?${queryString}`);
    listingData.value = response.data.content || [];
    totalPages.value = response.data.totalPages;
  } catch (error) {
    if(error){
      alert(error.response.data.errorMessage)
    }

  }
};

const searchData = () => {
  currentPage.value = 0;
  fetchData(currentPage.value);
  isFilterOpen.value = false;
};

const resetFilters = () => {
  searchName.value = '';
  listingStartDate.value = '';
  listingEndDate.value = '';
  pageSize.value = 30;
  currentPage.value = 0;
  fetchData(currentPage.value);
  isFilterOpen.value = false;
};

const paginatedData = computed(() => {
  const start = 0;
  return listingData.value.slice(start, start + pageSize.value);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++;
    fetchData(currentPage.value);
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    currentPage.value--;
    fetchData(currentPage.value);
  }
};

const goToPage = (page) => {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page;
    fetchData(currentPage.value);
  }
};

const formatDate = (dateString) => {
  if (!dateString) return '정보 없음';
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(dateString).toLocaleDateString('ko-KR', options);
};

const toggleFilter = () => {
  isFilterOpen.value = !isFilterOpen.value;
};

const openModal = (item) => {
  selectedItem.value = item; // 선택된 아이템 저장
  isModalOpen.value = true;
};

const submitAlarm = async () => {
  if (selectedItem.value.ipoName) {

    try{
      const listingShares = selectedItem.value.ipoName;
      const userEamilKakaoResponse = await userStore.getUserInfo();
      const kakaoToken = userEamilKakaoResponse.kakaoToken;

      try{
        await kakaoCalenderStore.createListingEvent(kakaoToken,selectedItem)
      }catch (err){
      }
      try {
        const response = await axios.post(LISTING_SHARES_ALARM, null, {
          params: { listingShares }
        });

        alert(response.data.statusMsg);
      } catch (error) {
        if (error.response) {
          alert(error.response.data.errorMessage);
        } else {
          alert('알람 신청 중 오류 발생');
        }
      }
    }catch (err){
      alert("로그인이 필요한 서비스 입니다.")
    }


  }
  isModalOpen.value = false; // 모달 닫기
};
const goToDetailPage = async (ipoName) => {
  await router.push({path: '/ipoDetail', query: {ipoName}})
}


onMounted(() => fetchData(0));
</script>
<style scoped>
body {
  font-family: Arial, sans-serif;
  background-color: #f4f4f4;
  padding: 20px;
}

.logo{
  width: 100%;
  max-width: 350px;
  height: auto;
}

.logo-container {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
  margin-bottom: 20px; /* 아래쪽 여백 추가 */
}
.search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.search-container button{
  margin: 5px;
}

.filter-menu {
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  position: absolute;
  z-index: 1000;
}

input, select {
  margin: 5px;
  padding: 5px;
}

.table-responsive {
  overflow-x: auto; /* 반응형 테이블 */
}

.table {
  width: 90%;
  border-collapse: collapse;
  margin: 0 auto;
  font-size: 0.9rem; /* 테이블 폰트 크기 조정 */
}

td, th {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: brown; /* 헤더 배경색 */
  color: white;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: #f2f2f2; /* 홀수 줄 배경색 */
}

.table-hover tbody tr:hover {
  background-color: #e2e6ea; /* 마우스 오버 시 배경색 */
}

.link-item {
  cursor: pointer; /* 링크 스타일 */
  color: #007bff; /* 링크 색상 */
}

.link-item:hover {
  text-decoration: underline; /* 링크 오버 시 밑줄 */
}


.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 0;
  list-style: none;
  width: 90%;
  margin: auto;
}

.pagination a {
  color: #007bff; /* 링크 색상 */
  padding: 8px 12px;
  margin: 0 4px;
  text-decoration: none;
  border-radius: 4px;
  font-size: 14px;
}

.pagination a:hover {
  background-color: #e4e9f1; /* 마우스 오버 시 배경색 */
  cursor: pointer;
}

.pagination a.active {
  color: white;
  background-color: #007bff; /* 활성 페이지 색상 */
  font-weight: bold;
  pointer-events: none;
}

.pagination a.disabled {
  color: #d1d5db; /* 비활성 링크 색상 */
  pointer-events: none;
}

@media (max-width: 768px) {
  h1 {
    font-size: 1.5rem; /* 모바일에서 제목 크기 조정 */
  }

  .table {
    width: 300%;
    font-size: 0.8rem; /* 모바일에서 테이블 폰트 크기 조정 */
    overflow-x: auto; /* 테이블이 가로 스크롤 가능하도록 설정 */
    display: block; /* 블록으로 설정하여 스크롤 가능하도록 함 */
  }

  .pagination {
    flex-direction: row; /* 모바일에서 페이지네이션을 가로로 배치 */
    justify-content: center; /* 가운데 정렬 */
  }

  .pagination a {
    margin: 0 2px; /* 페이지네이션 링크 여백 조정 */
  }
}

</style>
