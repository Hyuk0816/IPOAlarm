import {defineStore} from 'pinia';
import axios from "@/plugin/axios.js";
import {API_BASE_URL, MYPAGE} from '../api/apiPoints.js'

export const useMypageStore = defineStore('mypage',() => {
    const getMyPage = async () => {
        try{
            return await axios.get(API_BASE_URL+MYPAGE);
        }catch (err){
            console.error(err);
        }
    }
    const putProfile = async (file:File) => {

        try{
            const formData = new FormData();
            formData.append('file', file)
            const response = await axios.put('/user/profile', formData, {
                headers:{
                    'Content-Type' : 'multipart/form-data'
                }
            });
            alert(response.data.statusMsg);
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
        }
    }
    return{
        getMyPage,
        putProfile,
        putNickname
    }


})