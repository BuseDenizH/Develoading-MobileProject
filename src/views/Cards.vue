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
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';

const searchTerm = ref('');

// Kartlar
const savedCards = ref([]);

const otherCards = ref<any[]>([]);  // Backend'den alınacak kartlar

const userId = ref<number | null>(null);  // UserId'yi saklayacak değişken

onMounted(async () => {
  // SecureStorage'den userId'yi al
  const storedUserId = await SecureStoragePlugin.get({ key: 'userId' });
  if (storedUserId.value) {
    userId.value = parseInt(storedUserId.value);  // UserId'yi sayısal değere çevir
  }

  if (userId.value) {
    try {
      // 1. Kullanıcıya ait kartlar ve kart bilgilerini al
      const userCardsResponse = await axios.get(`http://18.153.153.139:8082/api/user-cards/${userId.value}`);
      const userCardIds = userCardsResponse.data.map((userCard: any) => userCard.cardId);

      // 2. Kartlar tablosundan tüm kart bilgilerini al
      const allCardsResponse = await axios.get('http://18.153.153.139:8082/api/cards');
      const allCards = allCardsResponse.data;

      // 3. Kullanıcının kart bilgilerini filtrele
      savedCards.value = allCards.filter((card: any) => userCardIds.includes(card.id));

      // 4. Diğer kartları filtrele (henüz kullanıcıya ait olmayan)
      otherCards.value = allCards.filter((card: any) => !userCardIds.includes(card.id));
    } catch (error) {
      console.error('Kartlar alınamadı:', error);
    }
  } else {
    console.error('Kullanıcı kimliği bulunamadı!');
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


const addCard = async (card: any) => {
  if (!savedCards.value.some(savedCard => savedCard.name === card.name)) {
    // Frontend'de listeye ekle
    savedCards.value.push(card);
    otherCards.value = otherCards.value.filter(c => c.name !== card.name);

    // Backend'e ekleme isteği
    try {
      const userCard = {
        userId: userId.value,  // Kullanıcı ID'si (örnek olarak 1 kullanıldı)
        cardId: card.id,  // Kartın ID'si
      };

      await axios.post('http://18.153.153.139:8082/api/user-cards', userCard);
    } catch (error) {
      console.error('Kart backendde kaydedilemedi:', error);
    }
  }
};

const removeCard = async (card: any) => {
  // Frontend'de listeyi güncelle
  savedCards.value = savedCards.value.filter(c => c.name !== card.name);
  otherCards.value.push(card);

  // Backend'e silme isteği gönder
  try {
    if (userId.value) {
      const response = await axios.delete(`http://18.153.153.139:8082/api/user-cards/remove/${userId.value}/${card.id}`);
      if (response.status === 200) {
        console.log('Kart başarıyla kaldırıldı:', response.data);
      }
    }
  } catch (error) {
    console.error('Kart backendden kaldırılamadı:', error);
  }
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
