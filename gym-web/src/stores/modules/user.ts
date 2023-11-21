import { defineStore } from "pinia";
import {ref} from "vue";

//用户模块
export const useUserStore = defineStore('user',() => {
    const userName = ref<string>('')

    const token = ref<string>('')

    const setUsername = (newUsername: string) => {
        userName.value = newUsername
    }

    const removeUsername = () => {
        userName.value = ''
    }

    const setToken = (newToken: string) => {
        token.value = newToken
    }

    const removeToken = () => {
        token.value = ''
    }

    return {
        userName,
        token,
        setToken,
        removeToken
    }
}, {
    persist: true
})

interface LoginInfo {
    username: string,
    password: string
}

