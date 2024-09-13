import { createWebHistory, createRouter } from "vue-router";

import IpoData from "../components/IpoData.vue";
import MonthlyChart from "../components/MonthlyChart.vue";
import ListingShares from "../components/ListingShares.vue";
import IpoDetail from "@/components/IpoDetail.vue";

const router = createRouter({
    history: createWebHistory(),
    routes:[
        {path: '/IpoData', component:IpoData},
        {path:'/',component:MonthlyChart, name:"home"},
        {path: '/ListingShares', component:ListingShares},
        {path: `/IpoDetail`, component:IpoDetail},
    ]
})

export default router;