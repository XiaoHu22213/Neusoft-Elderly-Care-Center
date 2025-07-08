// import './axios/index.js'
import './assets/main.css'
import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
// import {userMenuStore} from '@/stores'

import App from './App.vue'
import router from './router'


const app = createApp(App)

app.use(createPinia())
app.use(router)

// const menuStore = userMenuStore();

// window.addEventListener('load', () => {
//   menuStore.fetchMenuAndSetSession(); // 当页面加载时调用
// });

app.mount('#app')
