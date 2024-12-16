<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Kartlar</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Tab 3</ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-searchbar
          v-model="searchTerm"
          placeholder="Ara..."
          style="margin-top:20px; margin-bottom: 10px;">
      </ion-searchbar>

      <!-- Kayıtlı Kartlar -->
      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p class="title">Kayıtlı Kartlarım</p>
        <hr class="line">
      </div>

      <div style="display: flex; flex-wrap: wrap; justify-content: flex-start; gap: 10px;">
        <ion-card
            v-for="(card, index) in filteredSavedCards"
            :key="index"
            class="transparent-card"
            style="flex: 1 0 30%; max-width: 30%;"
        >
          <ion-card-content>
            <ion-button color="orange" @click="removeCard(card)">-</ion-button>
            <img :src="card.image" :alt="card.name" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">{{ card.name }}</ion-button>
        </ion-card>
      </div>

      <!-- Kartlar -->
      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p class="title">Tüm Kartlar</p>
        <hr class="line">
      </div>

      <div style="display: flex; flex-wrap: wrap; justify-content: flex-start; gap: 10px;">
        <ion-card
            v-for="(card, index) in filteredOtherCards"
            :key="index"
            class="transparent-card"
            style="flex: 1 0 30%; max-width: 30%;"
        >
          <ion-card-content>
            <ion-button color="orange" @click="addCard(card)">+</ion-button>
            <img :src="card.image" :alt="card.name" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">{{ card.name }}</ion-button>
        </ion-card>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonSearchbar } from '@ionic/vue';
import axios from 'axios';

const searchTerm = ref('');

// Kartlar
const savedCards = ref([
  { name: 'Axess', image: 'https://cdn.freelogovectors.net/wp-content/uploads/2020/07/axess_logo-300x300.png' },
  { name: 'Bonus', image: 'https://www.tatilvillam.com/assets/img/kart-reklamlari/400/garanti.png' }
]);

const otherCards = ref<any[]>([]);  // Backend'den alınacak kartlar

// Backend'den kartları al
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8082/api/cards');  // API URL'yi backend'e göre güncelleyin
    otherCards.value = response.data;  // Backend'den gelen kartlar
  } catch (error) {
    console.error('Kartlar alınamadı:', error);
  }
});

// Kartları filtreleme
const filteredSavedCards = computed(() => {
  const term = searchTerm.value.toLowerCase();
  if (!term) {
    return savedCards.value;
  }
  return savedCards.value.filter(card => card.name.toLowerCase().includes(term));
});

const filteredOtherCards = computed(() => {
  const term = searchTerm.value.toLowerCase();
  if (!term) {
    return otherCards.value;
  }
  return otherCards.value.filter(card => card.name.toLowerCase().includes(term));
});

// Kart ekleme fonksiyonu
const addCard = (card: any) => {
  // Eğer kart kaydedilmişse, zaten kaydedilmiş olmasın
  if (!savedCards.value.some(savedCard => savedCard.name === card.name)) {
    savedCards.value.push(card);
    // Kartı "Tüm Kartlar"dan sil
    otherCards.value = otherCards.value.filter(c => c.name !== card.name);
  }
};

// Kart silme fonksiyonu
const removeCard = (card: any) => {
  // Eğer kart "Tüm Kartlar"a geri eklenirse, kaydedilen kartlardan çıkar
  savedCards.value = savedCards.value.filter(c => c.name !== card.name);
  // Kartı "Tüm Kartlar"a ekle
  otherCards.value.push(card);
};
</script>

<style scoped>
.transparent-card {
  background-color: transparent !important;
  box-shadow: none !important;
  border: none !important;
  --ion-background-color: transparent !important;
}


.title {
  padding: 20px 16px 5px 16px;
  text-align: left;
  color: rgb(248, 79, 0);
  font-size: 20px;
  display: inline;
}

.line {
  background-color: rgb(248, 79, 0);
  height: 2px;
  margin: 10px;
}

ion-card-content ion-button {
  font-weight: bold;
  font-size: 15px;
  background-color: rgb(248, 79, 0);
  float: right;
  --border-radius: 10px;
  border-radius: 10px;
}
</style>
