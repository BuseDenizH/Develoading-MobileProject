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
              <ion-icon :icon="heart" /> {{ item.likeCount }} kişi beğendi
            </ion-col>
          </ion-row>
          <ion-row>
            <ion-col>
              <ion-icon :icon="shareSocial" />
              <ion-button @click="shareContent">Paylaş</ion-button>
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
        <p>Yükleniyor...</p>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { IonPage, IonHeader, IonToolbar, IonContent, IonButton, IonIcon, IonRow, IonCol } from '@ionic/vue';
import { calendarClear, hourglass, heart, shareSocial, arrowBack } from 'ionicons/icons';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';

import axios from 'axios';
import { Share } from '@capacitor/share';

const route = useRoute();
const router = useRouter();
const item = ref(null);
const isUsed = ref(false); // Button status

// User ID al
const getUserId = async () => {
  try {
    const result = await SecureStoragePlugin.get({ key: 'userId' });
    return Number(result.value); // Değeri sayıya dönüştürüp döndürün
  } catch (error) {
    console.error('UserId alınırken hata oluştu:', error);
    return NaN; // Hata durumunda geçersiz bir değer döndür
  }
};

// Kampanya detayı al
const fetchCampaignDetails = async () => {
  const { id } = route.params;
  try {
    const response = await axios.get(`http://localhost:8082/api/campaigns/${id}`);
    item.value = response.data;
    console.log("API yanıtı:", response.data);  // Gelen veriyi tekrar kontrol et
    console.log("Kampanyalar:", item.value);
    console.log(item.value.likeCount);
  } catch (error) {
    console.error('Detaylar alınamadı:', error);
  }
};

const shareContent = async () => {
  try {
    await Share.share({
      title: 'Kampanya Detayı',
      text: 'Bu kampanyayı inceleyin!',
      url: item.value.url,
    });
    console.log('Paylaşım başarılı');
  } catch (error) {
    console.error('Paylaşım hatası:', error);
  }
};

// İlk mount işlemi ve her sayfa geçişinde kampanya verisini yenile
onMounted(() => {
  fetchCampaignDetails();
});

// İzleme işlemi - kampanyayı izleyip yenileyebiliriz
watch(route, async () => {
  await fetchCampaignDetails();
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
