import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import router from "./router/router.js";
import pinia from "@/stores/index.js";

createApp(App).use(router).use(pinia).mount('#app')
