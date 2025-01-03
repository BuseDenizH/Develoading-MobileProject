// src/stores/cardStore.ts
import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useCardStore = defineStore('card', () => {
    // Cache için Map'ler
    const cards = ref<Map<number, string>>(new Map())
    const companies = ref<Map<number, string>>(new Map())
    const isLoading = ref(false)
    const error = ref<string | null>(null)

    // Tüm kart isimlerini getir ve cache'le
    const fetchCardNames = async () => {
        if (cards.value.size > 0) return // Eğer cache doluysa tekrar çekme

        try {
            isLoading.value = true
            const response = await axios.get('http://localhost:8082/api/cards')
            response.data.forEach((card: any) => {
                cards.value.set(card.id, card.name)
            })
        } catch (err) {
            error.value = 'Kart isimleri alınamadı'
            console.error('Kart isimleri alınamadı:', err)
        } finally {
            isLoading.value = false
        }
    }

    // Tüm şirket isimlerini getir ve cache'le
    const fetchCompanyNames = async () => {
        if (companies.value.size > 0) return // Eğer cache doluysa tekrar çekme

        try {
            isLoading.value = true
            const response = await axios.get('http://localhost:8082/api/companies')
            response.data.forEach((company: any) => {
                companies.value.set(company.id, company.name)
            })
        } catch (err) {
            error.value = 'Şirket isimleri alınamadı'
            console.error('Şirket isimleri alınamadı:', err)
        } finally {
            isLoading.value = false
        }
    }

    // Cache'den kart ismini al
    const getCardName = (cardId: number) => {
        return cards.value.get(cardId) || 'Bilinmeyen Kart'
    }

    // Cache'den şirket ismini al
    const getCompanyName = (companyId: number) => {
        return companies.value.get(companyId) || 'Bilinmeyen Şirket'
    }

    // Cache'i temizle (gerekirse)
    const clearCache = () => {
        cards.value.clear()
        companies.value.clear()
    }

    return {
        cards,
        companies,
        isLoading,
        error,
        fetchCardNames,
        fetchCompanyNames,
        getCardName,
        getCompanyName,
        clearCache
    }
})