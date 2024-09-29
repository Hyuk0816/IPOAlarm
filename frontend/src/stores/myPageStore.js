import {defineStore} from 'pinia';
import axios from "@/plugin/axios.js";
import {API_BASE_URL, MYPAGE} from '../api/apiPoints.js'
import {ref} from "vue";

export const useMypageStore = defineStore('mypage',() => {

    const myPageRes = ref(null);
    const userImage = ref(null);

    const profileImage = ref(null);
    const nickNameErrorMsg = ref(null);
    const getMyPage = async () => {
        try{
            const myPage =  await axios.get(API_BASE_URL+MYPAGE);
            myPageRes.value = myPage.data;
            userImage.value = myPageRes.value.image;
            return myPage;
        }catch (err){
        }
    }
    const putProfile = async (file) => {

        try{
            const formData = new FormData();
            formData.append('file', file)
            const response = await axios.put('/api/user/profile', formData, {
                headers:{
                    'Content-Type' : 'multipart/form-data'
                }
            });
            alert(response.data.statusMsg);
            window.location.reload();
        }catch (err){
        }
    }
    const putNickname = async (nickName) => {
        try{
            const res =  await axios.put('/api/user/nickName', null, {
                params: {nickName:nickName}
            })
            alert(res.data.statusMsg)
            window.location.reload();
        }catch (err){
            nickNameErrorMsg.value  = err.response.data.errorMessage
            return nickNameErrorMsg.value
        }
    }

    const myIpoAlarmCount = async () =>{
        try{
            const res =  await axios.get('/api/alarm/count');
            return res.data
        }catch (err){
        }
    }

    const myListingSharesCount = async () => {
        try{
            const res =  await axios.get('/api/listing_share_alarm/count')
            return res.data
        }catch (err){
        }
    }

    const nickNameCheck = async (nickName) => {
        try{
            const res = await axios.get('/api/user/nick_name_check', {
                params:{nickName:nickName}
            })
            return res

        }catch (err){
            nickNameErrorMsg.value  = err.response.data.errorMessage
            return nickNameErrorMsg.value
        }
    }

    return{
        getMyPage,
        putProfile,
        putNickname,
        myIpoAlarmCount,
        myListingSharesCount,
        userImage,
        profileImage,
        nickNameCheck,
        nickNameErrorMsg,
        myPageRes
    }


})