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
        <ion-card style="width: 30%; background-color: transparent;">
          <ion-card-content>
            <img src="/Users/eren/Desktop/a.png" alt="Açıklayıcı fotoğraf" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">Axess</ion-button>
        </ion-card>

        <ion-card style="width: 30%; background-color: transparent;">
          <ion-card-content>
            <img src="/Users/eren/Desktop/bonus.png" alt="Açıklayıcı fotoğraf" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">Bonus</ion-button>
        </ion-card>
      </div>

      <div style="text-align: left; margin-bottom: 10px; padding-left: 20px;">
        <p style="text-decoration: underline;">Kartlar</p>
      </div>

      <div style="display: flex; flex-wrap: wrap; justify-content: flex-start; gap: 10px;">
        <ion-card style="width: 30%; background-color: transparent;">
          <ion-card-content>
            <img src="/Users/eren/Desktop/maximum_card_logo.png" alt="Açıklayıcı fotoğraf" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">Maximum</ion-button>
        </ion-card>

        <ion-card style="width: 30%; background-color: transparent;">
          <ion-card-content>
            <img src="/Users/eren/Desktop/world_card_logo.png" alt="Açıklayıcı fotoğraf" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">World</ion-button>
        </ion-card>

        <ion-card style="width: 30%; background-color: transparent;">
          <ion-card-content>
            <img src="/Users/eren/Desktop/icon.webp" alt="Açıklayıcı fotoğraf" style="width: 100%; height: auto;" />
          </ion-card-content>
          <ion-button fill="clear">Hopi</ion-button>
        </ion-card>
      </div>
      
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
</script>
