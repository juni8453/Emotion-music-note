import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import router from './json/router.js'
import store from './json/store.js'

createApp(App).use(router).use(store).mount('#app')
