import axios from "axios";
import {API_BASE_URL, API_GET_ACCESS_TOKEN} from "../api/apiPoints.js";
import {computed, ref} from "vue";

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
                console.log(accessToken.value + ": axios ")
                config.headers['Authorization'] = `Bearer ${accessToken.value}`;
                console.log('Access token set in header:', accessToken.value);

            }

            console.log('Interceptor on');
        } catch (e) {
            console.error('Error fetching access token:', e);
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default instance;