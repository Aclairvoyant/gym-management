import { defineStore } from "pinia";
import {ref} from "vue";
import {userGetInfoService} from "@/apis/user";

//用户模块
export const useUserStore = defineStore('big-user',() => {

    const token = ref<string>('')

    const userType = ref<string>('')


    const setToken = (newToken: string) => {
        token.value = newToken
    }

    const removeToken = () => {
        token.value = ''
    }

    const setUserType = (newUserType: string) => {
        userType.value = newUserType
    }

    const user = ref({})
    const getUser = async () => {
        const res = await userGetInfoService() // 请求获取数据
        user.value = res.data.data
        userType.value = res.data.data.userType
    }
    const setUser = (obj: any) => {
        user.value = obj
    }


    return {
        token,
        setToken,
        removeToken,
        getUser,
        setUser,
        user,
        userType,
        setUserType
    }
}, {
    persist: true,
})


