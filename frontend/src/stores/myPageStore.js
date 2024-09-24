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
            console.error(err);
        }
    }
    const putProfile = async (file) => {

        console.log(file + " store")
        try{
            const formData = new FormData();
            formData.append('file', file)
            const response = await axios.put('/user/profile', formData, {
                headers:{
                    'Content-Type' : 'multipart/form-data'
                }
            });
            alert(response.data.statusMsg);
            window.location.reload();
        }catch (err){
            console.error(err)
        }
    }
    const putNickname = async (nickName) => {
        try{
            const res =  await axios.put('/user/nickName', null, {
                params: {nickName:nickName}
            })
            alert(res.data.statusMsg)
            window.location.reload();
        }catch (err){
            console.error(err)
            nickNameErrorMsg.value  = err.response.data.errorMessage
            return nickNameErrorMsg.value
        }
    }

    const myIpoAlarmCount = async () =>{
        try{
            const res =  await axios.get('/alarm/count');
            return res.data
        }catch (err){
            console.error(err)
        }
    }

    const myListingSharesCount = async () => {
        try{
            const res =  await axios.get('/listing_share_alarm/count')
            return res.data
        }catch (err){
            console.error(err)
        }
    }

    const nickNameCheck = async (nickName) => {
        try{
            console.log(nickName + " store")

            const res = await axios.get('/user/nick_name_check', {
                params:{nickName:nickName}
            })
            console.log(res.data + "store")
            return res

        }catch (err){
            console.error(err.response.data.errorMessage + " 에러메세지 뷰 store ")
            nickNameErrorMsg.value  = err.response.data.errorMessage
            console.log( nickNameErrorMsg.value + " 잘담기나?")
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
        nickNameErrorMsg
    }


})