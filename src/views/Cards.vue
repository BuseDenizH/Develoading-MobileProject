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
        style="margin-top:20px">
      </ion-searchbar>

      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p class="title">Kayıtlı Kartlar</p>
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
            <img :src="card.image" :alt="card.name" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">{{ card.name }}</ion-button>
        </ion-card>
      </div>

      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p class="title">Kartlar</p>
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
            <img :src="card.image" :alt="card.name" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">{{ card.name }}</ion-button>
        </ion-card>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonSearchbar } from '@ionic/vue';

const searchTerm = ref('');

const savedCards = [
  { name: 'Axess', image: 'https://cdn.freelogovectors.net/wp-content/uploads/2020/07/axess_logo-300x300.png' },
  { name: 'Bonus', image: 'https://www.tatilvillam.com/assets/img/kart-reklamlari/400/garanti.png' }
];

const otherCards = [
  { name: 'Maximum', image: 'https://www.logotypes101.com/logos/149/350AD36F2FD3EBACABAE553D46C6FAB0/maximum.png' },
  { name: 'World', image: 'https://www.logovector.org/wp-content/uploads/logos/png/w/world_card_logo.png' },
  { name: 'Hopi', image: 'https://logowik.com/content/uploads/images/hopi-new7564.logowik.com.webp' }
];

const filteredSavedCards = computed(() => {
  const term = searchTerm.value.toLowerCase();
  if (!term) {
    return savedCards;
  }
  return savedCards.filter(card => card.name.toLowerCase().includes(term));
});

const filteredOtherCards = computed(() => {
  const term = searchTerm.value.toLowerCase();
  if (!term) {
    return otherCards;
  }
  return otherCards.filter(card => card.name.toLowerCase().includes(term));
});
</script>

<style scoped>
.transparent-card {
  background-color: transparent !important;
  box-shadow: none !important;
  border: none !important;
  --ion-background-color: transparent !important;
}

.ion-card-content {
  padding: 0 !important;
}

.title {
  padding: 20px 16px 5px 16px;
  text-align: left;
  color: red;
  font-size: 20px;
}

.line {
  background-color: rgb(255, 0, 0);
  height: 2px;
  margin: 0;
}
</style>
