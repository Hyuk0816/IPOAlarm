import axios from "axios";
import {defineStore} from 'pinia';
import {ref} from "vue";
import {useMypageStore} from "@/stores/myPageStore.js";

export const useKakaoLoginStore = defineStore(('kakaoLogin'), () => {

    const myPage = useMypageStore();

    const userNickName = ref(null);
    const userProfile = ref(null);


    const redirectUri = import.meta.env.VITE_REDIRECT_URI
    const clientId = import.meta.env.VITE_CLIENT_ID
    console.log(clientId)
    const clientSecret = import.meta.env.VITE_CLIENT_SECRET



    const kakaoLogin = async () => {
        window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${clientId}&client_secret=${clientSecret}&redirect_uri=${redirectUri}`;
    }
    return{
        kakaoLogin,
        userNickName,
        userProfile
    }

})