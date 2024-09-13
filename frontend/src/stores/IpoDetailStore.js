import {defineStore} from 'pinia';
import {ref} from 'vue';
import axios from "@/plugin/axios.js";
import {API_GET_IPO_DETAIL} from "@/api/apiPoints.js";
export const useIpoDetailStore = defineStore('ipoDetail',()=> {

    let currentIpoName = '';

    const fetchIpoDetail = async (ipoName) => {

        if(!ipoName){
            console.error('공모주를 선택하세요!')
            alert("Ipo name missing")
        }
        try{
            console.log(ipoName + "store")
            const response = await axios.get(`http://localhost:8080/api/ipo_detail/data?ipoName=${ipoName}`);
            console.log(response.data)
            currentIpoName = ipoName;
        }catch(err){
            console.error(err);
        }

    }
    return{
        fetchIpoDetail,
    }
});