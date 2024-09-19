import { createWebHistory, createRouter } from "vue-router";

import IpoData from "../components/IpoData.vue";
import Home from "@/components/Home.vue";
import ListingShares from "../components/ListingShares.vue";
import IpoDetail from "@/components/IpoDetail.vue";
import mypage from "../components/mypage.vue"


const router = createRouter({
    history: createWebHistory(),
    routes:[
        {path: '/IpoData', component:IpoData},
        {path:'/',component:Home, name:"home"},
        {path: '/ListingShares', component:ListingShares},
        {path: `/IpoDetail`, component:IpoDetail},
        {path: '/mypage', component:mypage}
    ]
})

export default router;