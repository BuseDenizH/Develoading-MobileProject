// src/stores/userStore.ts
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
    const userName = ref('')
    const userEmail = ref('')

    const updateUserInfo = (name: string, email: string) => {
        userName.value = name
        userEmail.value = email
    }

    return {
        userName,
        userEmail,
        updateUserInfo
    }
})