<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-button @click="goBack" class="back-button">
            <ion-icon aria-hidden="true" :icon="arrowBackOutline" />
            <ion-label>Geri</ion-label>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-searchbar v-model="searchTerm" placeholder="Ara..." style="margin-top:20px"></ion-searchbar>
      <ion-grid>
        <ion-row>
          <ion-col v-for="item in filteredItems" :key="item.id" size="6">
            <div>
              <img :src="item.image" @click="handleClick(item.id)" style="width: 100%; height: 100px; margin-top:10px; cursor: pointer;"/>
              <div class="image-caption" @click="handleClick(item.id)">
                <h3>{{ item.title }}</h3>
              </div>
            </div>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-content>
  </ion-page>
</template>



<script setup lang="ts">
import { ref, computed } from 'vue';
import {  IonContent, IonCol, IonGrid, IonPage, IonRow,IonSearchbar, IonHeader, IonToolbar, IonButtons, IonButton, IonIcon} from '@ionic/vue';
import { arrowBackOutline } from 'ionicons/icons';


import resim1 from '@/assets/resim1.jpg';
import resim2 from '@/assets/resim2.jpg';
import resim3 from '@/assets/resim3.jpeg';
import resim4 from '@/assets/resim4.jpg';
import resim5 from '@/assets/resim5.jpeg';
import resim6 from '@/assets/resim6.jpeg';
import resim7 from '@/assets/resim7.jpeg';

const items = ref([
  { id: 1, title: 'Akaryakıt', image: resim1 },
  { id: 2, title: 'Giyim/Kozmetik', image: resim2 },
  { id: 3, title: 'Elektronik', image: resim3 },
  { id: 4, title: 'Yeme/İçme', image: resim4 },
  { id: 5, title: 'Turizm/Seyahat', image: resim5 },
  { id: 6, title: 'Telekomünikasyon', image: resim6 },
  { id: 7, title: 'Araç Kiralama', image: resim7 }
]);

const searchTerm = ref('');

const filteredItems = computed(() => {
  const term = searchTerm.value.toLowerCase();
  if (!term) {
    return items.value;
  }
  return items.value.filter(item => 
    item.title.toLowerCase().includes(term)
  );
});

const handleClick = (imageId: number) => {
  console.log(`Resim ${imageId} tıklandı!`);
};
</script>



<style scoped>
.back-button {
  color: blue; 
}

.image-caption {
  text-align: center; 
  margin-top: 1px; 
  border: 1px solid #ccc; 
  border-radius: 5px; 
  padding: 5px; 
  background-color: #f8f9fa; 
  cursor: pointer; 
}

.image-caption h3 {
  font-size: 1.0em; 
  margin: 0; 
}
</style>
