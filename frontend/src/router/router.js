import { createWebHistory, createRouter } from "vue-router";

import IpoData from "../components/IpoData.vue";
import KakaoLogin from "../components/KakaoLogin.vue";
import MonthlyChart from "../components/MonthlyChart.vue";
import ListingShares from "../components/ListingShares.vue";
import IpoDetail from "@/components/IpoDetail.vue";

const router = createRouter({
    history: createWebHistory(),
    routes:[
        {path:'/',component:MonthlyChart, name:"home"},
        {path: '/IpoData', component:IpoData},
        {path: '/ListingShares', component:ListingShares},
        // {path: `/IpoDetail/${ipoName}`, component:IpoDetail}
    ]
})

export default router;