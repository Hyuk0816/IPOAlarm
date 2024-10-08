<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <canvas id="profitChart"></canvas>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <ul class="nav nav-pills justify-content-center">
          <li class="nav-item" v-for="year in years" :key="year">
            <a
                class="nav-link"
                :class="{ active: selectedYear === year }"
                @click.prevent="fetchData(year)">
            {{ year }}
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
import axios from "@/plugin/axios.js";

Chart.register(...registerables);

export default {
  data() {
    return {
      years: [2021, 2022, 2023, 2024],
      chart: null,
      chartData: [],
      currentRequest: null,
      selectedYear: 2024, // 기본 선택 연도
    };
  },
  methods: {
    async fetchData(year) {
      // 이전 요청이 진행 중이면 취소
      if (this.currentRequest) {
        this.currentRequest.abort();
      }

      this.currentRequest = new AbortController();
      const { signal } = this.currentRequest;

      try {
        const response = await axios.get(`/api/listing_shares/monthly_profit`, {
          params:{year:year},
          signal:signal
        });

        this.chartData = response.data
        this.selectedYear = year; // 선택된 연도 업데이트
        this.updateChart();
      } catch (error) {
        if (error.name === 'AbortError') {
        } else {
        }
      }
    },
    updateChart() {
      const ctx = document.getElementById('profitChart');
      if (!ctx) {
        return;
      }

      if (this.chart) {
        this.chart.destroy();
      }

      this.chart = new Chart(ctx.getContext('2d'), {
        type: 'line',
        data: {
          labels: [
            '1월', '2월', '3월', '4월', '5월',
            '6월', '7월', '8월', '9월', '10월',
            '11월', '12월'
          ],
          datasets: [{
            label: '월별 이익 (단위: %)',
            data: this.chartData,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
            fill: true,
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false, // 비율 유지하지 않기
          scales: {
            y: {
              beginAtZero: true
            }
          },
          plugins: {
            title:{
              display: true,
              text: "월간 공모가 대비 상장일 수익률",
              font: {
                size : 20
              }
            }
          }
        }
      });
    }
  },
  mounted() {
    this.fetchData(this.selectedYear); // 초기 데이터 로드
  }
};
</script>

<style scoped>
.nav-pills .nav-link {
  margin-bottom: 10px; /* 탭 간의 간격 조정 */
}

#profitChart {
  height: 300px; /* 원하는 높이 설정 */
  width: 100%; /* 너비를 100%로 설정 */
}

@media (max-width: 576px) {
  #profitChart {
    height: 250px; /* 작은 화면에서는 높이 조정 */
  }
}
</style>
