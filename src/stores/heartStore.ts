// src/stores/heartStore.ts
import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useHeartStore = defineStore('heart', () => {
    const hearts = ref<Set<number>>(new Set())

    function updateHearts(campaignId: number, status: boolean) {
        if (status) {
            hearts.value.add(campaignId)
        } else {
            hearts.value.delete(campaignId)
        }
    }

    function setInitialHearts(heartsList: number[]) {
        hearts.value = new Set(heartsList)
    }

    return { hearts, updateHearts, setInitialHearts }
})