import {defineStore} from 'pinia';
import axios from "@/plugin/axios.js";
import {MYPAGE} from '../api/apiPoints.js'

export const useMypageStore = defineStore('mypage',() => {

    const getMyPage = async () => {
        try{
            return await axios.get(MYPAGE);
        }catch (err){
            console.error(err);
        }
    }
    return{
        getMyPage,
    }
})