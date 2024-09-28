<template>
  <div>
    <div class="logo-container">
      <img class="logo" src="../assets/img/IPO_Schedule.png" alt="logo"></img>
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
              <label for="startDate">청약 시작일:</label>
              <input type="date" v-model="startDate" class="form-control-sm" />
            </li>
            <li class="list-group-item">
              <label for="endDate">청약 마감일:</label>
              <input type="date" v-model="endDate" class="form-control-sm" />
            </li>
            <li class="list-group-item">
              <label for="pageSize">사이즈:</label>
              <select v-model="pageSize" class="form-select form-select-sm" @change="searchData">
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="30" selected>30</option>
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
          <th>공모 가격</th>
          <th>확정 가격</th>
          <th>경쟁률</th>
          <th>증권사</th>
          <th>시작일</th>
          <th>종료일</th>
          <th>알림신청</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in paginatedData" :key="index">
          <td @click="goToDetailPage(item.ipoName)" class="link-item">{{ item.ipoName }}</td>
          <td>{{ item.ipoPrice }}</td>
          <td>{{ item.confirmPrice }}</td>
          <td>{{ item.competitionRate }}</td>
          <td>{{ item.securities }}</td>
          <td>{{ formatDate(item.startDate) }}</td>
          <td>{{ formatDate(item.endDate) }}</td>
          <td class="text-center">
            <button class="btn btn-primary" @click="openModal(item)">신청</button>
          </td>
        </tr>
        </tbody>
      </table>
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
  </div>
</template>

<script setup lang="js">
import { ref, onMounted, computed } from 'vue';
import axios from '../plugin/axios.js';
import Modal from './Modal.vue'; // 모달 컴포넌트 임포트
import {API_GET_ACCESS_TOKEN, API_GET_IPO_DATA, USER_INFO} from '../api/apiPoints.js'
import {API_IPO_ALARM} from "../api/apiPoints.js";
import { useRouter } from 'vue-router'; // 라우터 가져오기
import {useUserStore} from "@/stores/usersStores.ts";
import {useKakaoCalenderStore} from "@/stores/kakaoCalenderStore.js";


const ipoData = ref([]);
const searchName = ref('');
const startDate = ref('');
const endDate = ref('');
const pageSize = ref(30);
const currentPage = ref(0);
const totalPages = ref(0);
const isFilterOpen = ref(false); // 필터가 열려있는지 여부
const isModalOpen = ref(false);
const selectedItem = ref(null); // 선택된 아이템 저장

const router = useRouter();
const userStore = useUserStore();
const kakaoCalenderStore = useKakaoCalenderStore();

const fetchData = async (page) => {
  const params = {
    page,
    size: pageSize.value,
    sort: 'string',
    ipoName: searchName.value || undefined,
    start: startDate.value || undefined,
    end: endDate.value || undefined,
  };

  try {
    const response = await axios.get(API_GET_IPO_DATA, { params });
    ipoData.value = response.data.content || [];
    totalPages.value = response.data.totalPages;

  } catch (error) {
    alert(error.response.data.errorMessage)
    console.error('Error fetching data:', error);
  }
};

const searchData = () => {
  currentPage.value = 0;
  fetchData(currentPage.value);
  isFilterOpen.value = false;
};

const resetFilters = () => {
  searchName.value = '';
  startDate.value = '';
  endDate.value = '';
  pageSize.value = 30;
  currentPage.value = 0;
  fetchData(currentPage.value);
  isFilterOpen.value = false;
};

const paginatedData = computed(() => {
  const start = 0;
  return ipoData.value.slice(start, start + pageSize.value);
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

//모달 관련
const openModal = (item) => {
  selectedItem.value = item; // 선택된 아이템 저장
  isModalOpen.value = true;
};

// Computed property to adjust for 0-based index from backend
const adjustedCurrentPage = computed(() => {
  return currentPage.value - 1; // 프론트엔드에서 1부터 시작하므로 1 빼줌
});


const submitAlarm = async () => {
  if (selectedItem.value) {
    console.log(selectedItem + " submitAlarm");
    const ipoName = selectedItem.value.ipoName;

    try {
      const response = await axios.post(API_IPO_ALARM, null, {
        params: { ipoName }
      });
      const userEamilKakaoResponse = await userStore.getUserInfo();
      const kakaoToken = userEamilKakaoResponse.data.kakaoToken;

      await kakaoCalenderStore.createIpoEvent(kakaoToken,selectedItem);
      alert(response.data.statusMsg);
    } catch (error) {
      if (error.response) {
        alert(error.response.data.errorMessage);
      } else {
        alert('알람 신청 중 오류 발생');
      }
      console.error('Error submitting alarm:', error);
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
  max-width: 400px;
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

.search-container button {
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
  width: 75%; /* 테이블 너비 설정 */
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
  background-color: navy; /* 헤더 배경색 */
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
  align-items: center;
  width: 90%;
  margin: auto;
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
