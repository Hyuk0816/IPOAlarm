import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import {UsersInfoData, UsersLoginRequest} from '../types/users.js';
import axios from "axios";
import {API_BASE_URL, API_GET_ACCESS_TOKEN,API_LOGOUT} from '../api/apiPoints.js'
import router from '../router/router'

export const useUserStore = defineStore('user', () => {

    // State
    const isLoggedIn = ref<boolean>(false);
    const loginStatus = ref<string | null>(null);
    const loginError = ref<string | null>(null);
    const usersData = ref<UsersInfoData>({
        email: "",
        profile: ""
    });

    // Mutation
    const setUsersData = (newUsersData: UsersInfoData) => {
        usersData.value = newUsersData;
    }

    const setUserProfile = (profile:string) => {
        usersData.value.profile = profile;
    }

    // Getters
    const userInfo = computed(() => {
        return {
            isLoggedIn: isLoggedIn.value,
            loginError: loginError.value
        };
    });

    function logIn() {
        isLoggedIn.value = true;
    }

    function logOut() {
        isLoggedIn.value = false;
    }

    const getUserInfo = async () => {
        try{
            const token = await axios.get(`${API_BASE_URL}${API_GET_ACCESS_TOKEN}`, { withCredentials: true });
            console.log( "userStore : " + token.data.accessToken)

            const response =  await axios.get('/api/auth/userInfo', {
                params: { token: token.data.accessToken }
            })
            console.log(response.data.email + " userStore")
            usersData.value.profile = response.data.profile;
            return response.data;

        }catch (err){
            console.error(err)
        }
    }

    const logout = async () =>{
        try{
            const res = axios.post(API_LOGOUT)
        }catch (err){
            console.log(err)
        }
    }

    return {
        usersData,
        setUsersData,
        isLoggedIn,
        loginStatus,
        userInfo,
        getUserInfo,
        setUserProfile,
        logout,
        // setName,
        // setAge,
        logIn,
        logOut
    };
});