<template>
  <div class="container">
    <header class="my-4 text-center">
      <h1 class="display-4">공모주 알리미</h1>
      <p class="lead">최신 공모주 정보를 빠르게 확인하세요!</p>
    </header>

    <section class="mb-5">
      <h2 class="text-center mb-4">공모주란?</h2>
      <div class="card">
        <div class="card-body">
          <p>
            공모주는 기업이 자금을 조달하기 위해 주식을 일반 투자자에게 판매하는 과정을 의미합니다.
            <br />
            이는 기업의 성장 가능성을 투자자에게 제공하며, 투자자는 주식 매매를 통해 수익을 얻을 수 있습니다.
          </p>
        </div>
      </div>
    </section>

    <section class="mb-5">
      <h2 class="text-center mb-4">통계 지표</h2>
      <MonthlyChart />
    </section>

    <footer class="text-center">
      <p>© 2024 공모주 알리미. All rights reserved.@Hyuck0816</p>
    </footer>
  </div>
</template>

<script>
import MonthlyChart from './MonthlyChart.vue'; // MonthlyChart 컴포넌트 가져오기

export default {
  name: 'Home',
  components: {
    MonthlyChart, // MonthlyChart 컴포넌트 등록
  },
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
}
</style>
