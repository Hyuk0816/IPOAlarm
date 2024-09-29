import {defineStore} from 'pinia';
import axios from "@/plugin/axios.js";

export const useIpoDetailStore = defineStore('ipoDetail',()=> {

    const fetchIpoDetail = async (ipoName) => {

        if(!ipoName){
            alert("Ipo name missing")
        }
        try{
            return await axios.get(`/api/ipo_detail/data?ipoName=${ipoName}`)
        }catch(err){
        }
    }

    const saveComments = async (ipoName, ipoComments) =>{
        try{

            const response =  await axios.post(`/api/ipo_comments/comment/${ipoName}`, {
                ipoComment : ipoComments
            })
            alert(response.data.statusMsg)
            location.reload();
        }catch (err){
            alert("오류 발생!")
        }
    }
    return{
        fetchIpoDetail,
        saveComments
    }





});