<script setup>
import IpoData from './components/IpoData.vue';
import MonthlyChart from './components/MonthlyChart.vue';
import Navbar from "./components/Navbar.vue";
// export default {
//   name: 'App',
//   components: {
//     Navbar,
//     KakaoLogin,
//     IpoData,
//     MonthlyChart,
//   }
// };

</script>

<template>
  <Navbar/>

  <router-view />

</template>

<style scoped>

</style>
