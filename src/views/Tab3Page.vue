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
        placeholder="Kart ara..." 
        showCancelButton="true" 
        debounce="300"
      ></ion-searchbar>

      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p style="text-decoration: underline;">Kayıtlı Kartlar</p>
      </div>

      <div style="display: flex; flex-wrap: wrap; justify-content: flex-start; gap: 10px;">
        <ion-card 
          v-for="(card, index) in savedCards" 
          :key="index" 
          class="transparent-card"
        >
          <ion-card-content>
            <img :src="card.image" :alt="card.name" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">{{ card.name }}</ion-button>
        </ion-card>
      </div>

      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p style="text-decoration: underline;">Kartlar</p>
      </div>

      <div style="display: flex; flex-wrap: wrap; justify-content: flex-start; gap: 10px;">
        <ion-card 
          v-for="(card, index) in otherCards" 
          :key="index" 
          class="transparent-card"
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
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';

// Kayıtlı ve diğer kartlar için verileri tanımlıyoruz
const savedCards = [
  { name: 'Axess', image: '/Users/eren/Desktop/a.png' },
  { name: 'Bonus', image: '/Users/eren/Desktop/bonus.png' }
];

const otherCards = [
  { name: 'Maximum', image: '/Users/eren/Desktop/maximum_card_logo.png' },
  { name: 'World', image: '/Users/eren/Desktop/world_card_logo.png' },
  { name: 'Hopi', image: '/Users/eren/Desktop/icon.webp' }
];
</script>

<style scoped>
.transparent-card {
  background-color: transparent !important;
  box-shadow: none !important;
  border: none !important;
  --ion-background-color: transparent !important; /* Ionic'in varsayılan arka plan rengini de iptal ediyoruz */
}

.ion-card-content {
  padding: 0 !important; /* Varsayılan boşlukları kaldırıyoruz */
}
</style>
