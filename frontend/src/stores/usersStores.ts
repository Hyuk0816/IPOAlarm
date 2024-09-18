import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import {UsersInfoData, UsersLoginRequest} from '../types/users.ts';

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

    // Actions
    const usersLogin = async (loginBody: UsersLoginRequest) => {
        try {
            const data = await login(loginBody);
            loginStatus.value = data;

            isLoggedIn.value = true;
            loginError.value = null;
        } catch (e) {
            console.error('Login failed:', e);
            loginError.value = 'Login failed. Please check your credentials and try again.';
        }
    };

    // function setName(newName: string) {
    //   name.value = newName
    // }
    //
    // function setAge(newAge: number) {
    //   age.value = newAge
    // }

    function logIn() {
        isLoggedIn.value = true;
    }

    function logOut() {
        isLoggedIn.value = false;
    }

    return {
        usersData,
        setUsersData,
        isLoggedIn,
        loginStatus,
        usersLogin,
        userInfo,
        // setName,
        // setAge,
        logIn,
        logOut
    };
});