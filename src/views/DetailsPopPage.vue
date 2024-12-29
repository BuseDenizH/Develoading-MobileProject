<template>
  <ion-page>
    <!-- Header -->
    <ion-header>
      <ion-toolbar color="light">
        <ion-button fill="clear" @click="goBack">
          <ion-icon :icon="arrowBack" slot="start" /> Geri
        </ion-button>
      </ion-toolbar>
    </ion-header>

    <!-- Main Content -->
    <ion-content :fullscreen="true">
      <div v-if="item" class="campaign-container">
        <!-- Image Section -->
        <div class="image-section">
          <img :src="item.image" :alt="item.alt" />
        </div>

        <!-- Title -->
        <h2 class="campaign-title">{{ item.detail }}</h2>

        <!-- Campaign Info -->
        <div class="campaign-info">
          <ion-row>
            <ion-col>
              <ion-icon :icon="calendarClear" /> {{ formatDate(item.beginDate) }}
            </ion-col>
            <ion-col>
              <ion-icon :icon="hourglass" /> {{ formatDate(item.endDate) }}
            </ion-col>
          </ion-row>
          <ion-row>
            <ion-col>
              <ion-icon :icon="bookmark" /> {{ item.bookmarks }}18 kişi kullandı
            </ion-col>
            <ion-col>
              <ion-icon :icon="heart" /> {{ item.hearts }}27 kişi beğendi
            </ion-col>
          </ion-row>
          <ion-row>
            <ion-col>
              <ion-icon :icon="shareSocial" /> Paylaş
            </ion-col>
          </ion-row>
        </div>

        <!-- Details -->
        <div class="campaign-details">
          <h3>KATILMAK İÇİN</h3>
          <p>İlk harcamadan önce HEMEN KATIL butonunun tıklanması gerekmektedir.</p>
          <h3>NASIL KAZANIRIM?</h3>
          <p>
            Alışverişlerinizde QR veya Mobil Temassız Ödeme ile Garanti BBVA POS'larından yapacağınız
            750 TL ve üzeri her işlemde 25 TL, toplamda 300 TL bonus verilecektir.
          </p>
        </div>

        <!-- Action Buttons -->
        <div class="action-buttons">
          <ion-button :disabled="isUsed" @click="markAsUsed" expand="block" color="danger">
            Kullandım
          </ion-button>
          <ion-button expand="block" color="danger" @click="goToLink(item.url)">
            Detayı Görüntüle
          </ion-button>
        </div>
      </div>
      <div v-else>
        <p>Detay bulunamadı.</p>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { IonPage, IonHeader, IonToolbar, IonContent, IonButton, IonIcon, IonRow, IonCol } from '@ionic/vue';
import { calendarClear, hourglass, bookmark, heart, shareSocial, arrowBack } from 'ionicons/icons';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';

import axios from 'axios';

const route = useRoute();
const router = useRouter();
const item = ref(null);
const isUsed = ref(false); // Button status

const getUserId = async () => {
  try {
    const result = await SecureStoragePlugin.get({ key: 'userId' });
    return Number(result.value); // Değeri sayıya dönüştürüp döndürün
  } catch (error) {
    console.error('UserId alınırken hata oluştu:', error);
    return NaN; // Hata durumunda geçersiz bir değer döndür
  }
};


//kampanya detayı al 
onMounted(async () => {
  const { id } = route.params;
  try {
    const response = await axios.get(`http://18.153.153.139:8082/api/campaigns/${id}`);
    item.value = response.data;
  } catch (error) {
    console.error('Detaylar alınamadı:', error);
  }
});

const goBack = () => {
  router.push('/tabs/tab1');
};

const goToLink = (url: string) => {
  window.open(url, '_blank');
};

const formatDate = (dateString: string) => {
  return dateString?.split('T')[0] || '';
};

// Kampanyayı kullanıldık olarak işaretleme
const markAsUsed = async () => {
  if (isUsed.value || !item.value) return;

  try {
    const userId = await getUserId();
    if (isNaN(userId) || userId <= 0) {
      console.error("Geçersiz UserId:", userId);
      return;
    }

    const campaignId = item.value.id;
    const response = await axios.post('http://localhost:8082/api/used-campaigns/use', null, {
      params: { userId, campaignId },
    });

    console.log(response.data);
    isUsed.value = true;
  } catch (error) {
    console.error('Kampanya kullanılamadı:', error);
  }
};


</script>

<style>
.campaign-container {
  padding: 16px;
  text-align: center;
}

.image-section img {
  width: 100%;
  border-radius: 10px;
}

.campaign-title {
  font-size: 22px;
  font-weight: bold;
  color: #000;
  margin-top: 16px;
}

.campaign-info ion-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 8px;
  font-size: 14px;
  color: #666;
}

.campaign-details h3 {
  font-size: 18px;
  color: #000;
  margin-top: 16px;
}

.campaign-details p {
  font-size: 16px;
  color: #444;
}

.action-buttons ion-button {
  margin-top: 10px;
}
</style>