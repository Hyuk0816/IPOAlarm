<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <canvas id="Top5Chart"></canvas>
      </div>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
import axios from "axios";
import {ref} from "vue";

Chart.register(...registerables);

export default {
  data() {
    return {
      chart: null,
      chartData: ref(null),
    };
  },
  methods: {
    async updateChart() {
      const ctx = document.getElementById('Top5Chart');
      if (!ctx) {
        console.error('Canvas 요소를 찾을 수 없습니다.');
        return;
      }
      const response = await axios.get('/api/listing_shares/valuable_listing')
      this.chartData = response.data;
      console.log(response.data + " top5 chart")

      if (this.chart) {
        this.chart.destroy();
      }
      const labels = this.chartData.map(item => item.ipoName);
      const data = this.chartData.map(item => item.changeRateOpeningToOfferingPrice);

      this.chart = new Chart(ctx.getContext('2d'), {
        type: 'bar', // 막대 그래프
        data: {
          labels: labels,
          datasets: [{
            label: '공모가격 대비 상장일 가격 상승률  (%)',
            data: data,
            backgroundColor: 'rgba(75, 192, 192, 0.5)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false, // 비율 유지하지 않기
          scales: {
            y: {
              beginAtZero: true,
              title: {
                display: true,
                text: '상승률 (%)'
              }
            },
            x: {
              title: {
                display: true,
                text: '공모주 이름'
              }
            }
          },
          plugins: {
            title: {
              display: true,
              text: "상장일 대비 공모가 상승률",
              font: {
                size: 20
              }
            }
          }
        }
      });
    }
  },
  mounted() {
    this.updateChart(); // 초기 데이터 로드
  }
};
</script>

<style scoped>
#Top5Chart {
  height: 500px; /* 원하는 높이 설정 */
  width: 100%; /* 너비를 100%로 설정 */
}

@media (max-width: 576px) {
  #Top5Chart {
    height: 250px; /* 작은 화면에서는 높이 조정 */
  }
}
</style>
