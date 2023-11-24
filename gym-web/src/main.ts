import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import pinia from "@/stores"


const app = createApp(App)

app.use(router)
app.use(ElementPlus)
// 屏蔽警告信息
app.config.warnHandler = () => null;

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(pinia).mount('#app')
