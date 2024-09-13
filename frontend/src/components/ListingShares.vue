<template xmlns="">
  <div>
    <h1 class="text-left display-4 text-primary mb-4">상장일 일정</h1>
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
              <button @click="searchData" class="btn btn-primary">검색</button>
              <button @click="resetFilters" class="btn btn-secondary">초기화</button>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <table>
      <thead>
      <tr>
        <th>Num</th>
        <th>이름</th>
        <th>상장일</th>
        <th>현재가</th>
        <th>변동률 (이전)</th>
        <th>공모가</th>
        <th>변동률 (공모가)</th>
        <th>시가</th>
        <th>변동률 (시가)</th>
        <th>첫 날 종가</th>
        <th>알림신청</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, index) in paginatedData" :key="index">
        <td>{{ index + 1 }}</td>
        <td>{{ item.ipoName }}</td>
        <td>{{ formatDate(item.listingDate) }}</td>
        <td>{{ item.currentPrice }}</td>
        <td>{{ item.changeRatePrevious }}</td>
        <td>{{ item.offeringPrice }}</td>
        <td>{{ item.changeRateOfferingPrice }}</td>
        <td>{{ item.openingPrice }}</td>
        <td>{{ item.changeRateOpeningToOfferingPrice }}</td>
        <td>{{ item.closingPriceFirstDay }}</td>
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
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from '../plugin/axios.js'
import Modal from './Modal.vue'; // 모달 컴포넌트 임포트

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
    const response = await axios.get(`http://localhost:8080/api/listing_shares/data?${queryString}`);
    listingData.value = response.data.content || [];
    totalPages.value = response.data.totalPages;
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const searchData = () => {
  currentPage.value = 0;
  fetchData(currentPage.value);
};

const resetFilters = () => {
  searchName.value = '';
  listingStartDate.value = '';
  listingEndDate.value = '';
  pageSize.value = 30;
  currentPage.value = 0;
  fetchData(currentPage.value);
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
  selectedItem.value = item.ipoName; // 선택된 아이템 저장
  isModalOpen.value = true;
};

const submitAlarm = async () => {
  if (selectedItem.value) {
    console.log(selectedItem.value);
    const listingShares = selectedItem.value;

    try {
      const response = await axios.post(`http://localhost:8080/api/listing_share_alarm/alarm`, null, {
        params: { listingShares }
      });

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


onMounted(() => fetchData(0));
</script>
<style scoped>
body {
  font-family: Arial, sans-serif;
  background-color: #f4f4f4;
  padding: 20px;
}

h1 {
  text-align: center;
}

.search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
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

table {
  width: 80%;
  border-collapse: collapse;
  margin: 0 auto;
}

td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #4CAF50;
  color: white;
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

.pagination a {
  color: #6e7a8e;
  padding: 8px 12px;
  margin: 0 4px;
  text-decoration: none;
  border-radius: 4px;
  font-size: 14px;
}

.pagination a:hover {
  background-color: #e4e9f1;
  cursor: pointer;
}

.pagination a.active {
  color: blue;
  font-weight: bold;
  pointer-events: none;
}

.pagination a.disabled {
  color: #d1d5db;
  pointer-events: none;
}

.pagination a:first-child,
.pagination a:last-child {
  margin-right: 10px;
  position: relative;
}

.pagination a.first-page,
.pagination a.last-page {
  font-weight: bold;
  position: relative;

}

</style>
