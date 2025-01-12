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
              <img :src="item.image" @click="handleClick(item.id)"
                style="width: 100%; height: 100px; margin-top:10px; cursor: pointer;" />
              <div class="image-caption" @click="handleClick(item.id)">
                <h3>{{ item.name }}</h3>
              </div>
            </div>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { IonContent, IonCol, IonGrid, IonPage, IonRow, IonSearchbar, IonHeader, IonToolbar, IonButtons, IonButton, IonIcon } from '@ionic/vue';
import { arrowBackOutline } from 'ionicons/icons';

const items = ref([]);

const searchTerm = ref('');

const filteredItems = computed(() => {
  const term = searchTerm.value.toLowerCase();
  if (!term) {
    return items.value;
  }
  return items.value.filter(item =>
    item.name.toLowerCase().includes(term)
  );
});

const handleClick = (category: number) => {
  console.log(category);
  router.push({ name: 'FirmsPopPage', params: { category: category, companyName: items.value.find(item => item.id === category)?.name || '' } });
};

const router = useRouter();
const goBack = () => {
  router.back();
};

onMounted(async () => {
  try {
    const response = await axios.get('http://18.153.153.139:8082/api/companies');
    items.value = response.data;
  } catch (error) {
    console.error('Veriler çekilemedi:', error);
  }
});
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
