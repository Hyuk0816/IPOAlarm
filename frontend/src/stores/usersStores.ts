import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import {UsersInfoData, UsersLoginRequest} from '../types/users.js';
import axios from "axios";
import {API_BASE_URL, API_GET_ACCESS_TOKEN, USER_INFO} from '../api/apiPoints.js'

export const useUserStore = defineStore('user', () => {

    // State
    const isLoggedIn = ref<boolean>(false);
    const loginStatus = ref<string | null>(null);
    const loginError = ref<string | null>(null);
    const usersData = ref<UsersInfoData>({
        email: "",
        roleName: ""
    });

    // Mutation
    const setUsersData = (newUsersData: UsersInfoData) => {
        usersData.value = newUsersData;
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
            return response;

        }catch (err){
            console.error(err)
        }
    }



    return {
        usersData,
        setUsersData,
        isLoggedIn,
        loginStatus,
        userInfo,
        getUserInfo,
        // setName,
        // setAge,
        logIn,
        logOut
    };
});