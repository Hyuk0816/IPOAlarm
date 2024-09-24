import {defineStore} from 'pinia';
import axios from "@/plugin/axios.js";

export const useIpoDetailStore = defineStore('ipoDetail',()=> {

    const fetchIpoDetail = async (ipoName) => {

        if(!ipoName){
            console.error('공모주를 선택하세요!')
            alert("Ipo name missing")
        }
        try{
            return await axios.get(`/api/ipo_detail/data?ipoName=${ipoName}`)
        }catch(err){
            console.error(err);
        }
    }

    const saveComments = async (ipoName, ipoComments) =>{

        console.log(ipoName + "store")
        console.log(ipoComments + "store")
        try{

            const response =  await axios.post(`/api/ipo_comments/comment/${ipoName}`, {
                ipoComment : ipoComments
            })
            alert(response.data.statusMsg)
            location.reload();
        }catch (err){
            console.error(err);
            alert("오류 발생!")
        }
    }
    return{
        fetchIpoDetail,
        saveComments
    }





});