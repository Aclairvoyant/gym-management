import { createPinia } from "pinia"
import piniaPersisted from 'pinia-plugin-persistedstate'

const pinia = createPinia()
pinia.use(piniaPersisted)

export default pinia

// import { useUserStore } from "@/stores/modules/user"
// export { useUserStore }

export * from "@/stores/modules/user"
