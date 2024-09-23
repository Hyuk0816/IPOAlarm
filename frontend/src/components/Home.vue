
<script setup>
import MonthlyChart from './MonthlyChart.vue'; // MonthlyChart 컴포넌트 가져오기
import {useHomeStore} from "@/stores/homeStore.js";
import {onMounted, ref} from "vue";
import TopFiveChart from "@/components/TopFiveChart.vue";
const homeStore = useHomeStore();
const biggestRateIPO = ref(null)

const mostBiggestRateIpo = async () => {
  biggestRateIPO.value = await homeStore.mostBiggestRateIPO()
}
onMounted(() =>{
  mostBiggestRateIpo()
})
</script>

<template>
  <div class="container">
    <header class="my-4 text-center">
      <h1 class="display-4">공모주 알리미</h1>
      <p class="lead">공모주 알리미는 카카오톡 캘린더와 연동하여 공모주 청약 일정과 상장일 일정을 알람 등록해주는 서비스입니다.</p>
    </header>

    <section class="mb-5">
      <h2 class="text-center mb-4">공모주란?</h2>
      <div class="card">
        <div class="card-body">
          <p>
            주식 시장에서 "공모"란 새롭게 발행한 주식·공사채 등 유가 증권의 인수를 널리 일반에게 공개로 모집하는 것을 뜻합니다.
            <br />
            이를 통해 기업은 필요한 자금을 확보하고, 투자자들은 해당 기업의 주식을 구매함으로써 지분을 가질 수 있습니다. 공모주는 주로 IPO(Initial Public Offering) 형태로 이루어집니다.
            <br />
            공모가격 대비 현재가격을 비교했을 때 가장 큰 상승률을 보인 공모주는 <strong>{{ biggestRateIPO.data.ipoName }}</strong>입니다.
            <br />
            공모가는 <strong>{{ biggestRateIPO.data.offeringPrice }}</strong>원이었고, 현재가는 <strong>{{ biggestRateIPO.data.currentPrice }}</strong>원입니다. 상승률은 <strong>{{ biggestRateIPO.data.changeRateOfferingPrice }}%</strong>입니다.
          </p>
        </div>
      </div>
    </section>

    <section class="mb-5">
      <h2 class="text-center mb-4">통계 지표</h2>
      <div>
        <MonthlyChart />
      </div>
      <div>
        <TopFiveChart />
      </div>
    </section>

    <footer class="text-center">
      <p>© 2024 공모주 알리미. All rights reserved.@Hyuck0816</p>
    </footer>
  </div>
</template>


<style scoped>
.container {
  max-width: 800px;
  margin: auto;
}
.lead{
  margin-top: 10px;
}
</style>
