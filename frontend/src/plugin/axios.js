import axios from "axios";
import {API_BASE_URL, API_GET_ACCESS_TOKEN, API_USER_INFO} from "../api/apiPoints.js";
import {ref} from "vue";
import {useUserStore} from "@/stores/usersStores.ts";

const instance = axios.create({
    timeout: 2000,
    baseURL: API_BASE_URL,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
});



const accessToken = ref(null)
instance.interceptors.request.use(
    async (config) => {
        try {
            // Fetch the access token from the backend
            const res = await axios.get(`${API_BASE_URL}${API_GET_ACCESS_TOKEN}`, { withCredentials: true });

            if (res.status === 200 && res.data.accessToken) {
                // Extract the access token from the response
                accessToken.value = res.data.accessToken;
                config.headers['Authorization'] = `Bearer ${accessToken.value}`;
                const userStore = useUserStore();
                const userInfo = await axios.get(`${API_BASE_URL}${API_USER_INFO}`, {
                    params:{token:accessToken.value}
                });
                userStore.setUserProfile(userInfo.data.profile);
                userStore.logIn();
            }

        } catch (e) {
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
export default instance;