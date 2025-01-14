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
          <ion-button @click="toggleUsed" expand="block" :color="isUsed ? 'medium' : 'danger'" class="use-button"
            :disabled="isProcessing">
            {{ isUsed ? 'Kullanıldı' : 'Kullandım' }}
          </ion-button>
          <ion-button color="danger" @click="goToLink(item.url)">
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
import { Browser } from '@capacitor/browser';

const route = useRoute();
const router = useRouter();
const item = ref(null);
const isUsed = ref(false); // Button status
// Diğer ref tanımlamalarının yanına ekleyin
const isProcessing = ref(false);

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
    const response = await axios.get(`http://18.153.153.139:8082/api/campaigns/${id}`);
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
onMounted(async () => {
  await fetchCampaignDetails();
  await checkIfCampaignIsUsed();
});
// İzleme işlemi - kampanyayı izleyip yenileyebiliriz
watch(route, async () => {
  await fetchCampaignDetails();
  await checkIfCampaignIsUsed();
});

const goBack = () => {
  router.go(-1); // Bir önceki sayfaya dön
};

const goToLink = async (url: string) => {
  await Browser.open({ url: url });
};

const formatDate = (dateString: string) => {
  if (!dateString) return '';

  const [year, month, day] = dateString.split('T')[0].split('-');
  return `${day}/${month}/${year}`;
};




// Script kısmında değişiklikler
const toggleUsed = async () => {
  try {
    const userId = await getUserId();
    if (isNaN(userId) || userId <= 0 || !item.value) return;

    // İşlem devam ediyorsa yeni işlemi engelle
    if (isProcessing.value) {
      console.log('İşlem devam ediyor, lütfen bekleyin...');
      return;
    }

    // İşlem başladı
    isProcessing.value = true;

    const response = await axios.post(`http://18.153.153.139:8082/api/used-campaigns/toggle`, null, {
      params: {
        userId: userId,
        campaignId: item.value.id
      }
    });

    if (response.status === 200) {
      isUsed.value = !isUsed.value; // Durumu tersine çevir
      console.log(response.data); // Sunucudan gelen mesajı göster
    }
  } catch (error) {
    console.error('Kampanya kullanım durumu değiştirilemedi:', error);
  } finally {
    // İşlem bitti, kilidi kaldır
    isProcessing.value = false;
  }
};



// Script kısmında yapılacak değişiklikler
const checkIfCampaignIsUsed = async () => {
  try {
    const userId = await getUserId();
    if (isNaN(userId) || userId <= 0 || !item.value) return;

    // Kullanıcının kullandığı kampanyaları al
    const response = await axios.get(`http://18.153.153.139:8082/api/used-campaigns/campaigns/${userId}`);

    // Eğer response.data boş değilse ve current kampanya ID'si kullanılan kampanyalar arasındaysa
    if (response.data && response.data.includes(item.value.id)) {
      isUsed.value = true;
    }
  } catch (error) {
    console.error('Kampanya kullanım durumu kontrol edilemedi:', error);
  }
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

    const response = await axios.post('http://18.153.153.139:8082/api/used-campaigns/use', null, {
      params: {
        userId: userId,
        campaignId: item.value.id
      }
    });

    if (response.status === 200) {
      isUsed.value = true;
      // İsteğe bağlı: Başarılı işlem mesajı göster
      console.log('Kampanya başarıyla kullanıldı olarak işaretlendi');
    }
  } catch (error) {
    if (error.response?.status === 400) {
      console.error('Bu kampanya zaten kullanılmış');
    } else {
      console.error('Kampanya kullanılamadı:', error);
    }
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

.action-buttons {
  display: flex;
  flex-direction: column;
  padding: 0 16px;
}

.action-buttons ion-button {
  width: 100%;
  height: 40px;
  margin: 5px 0;
  --padding-top: 15px;
  --padding-bottom: 15px;
}

.use-button {
  transition: all 0.3s ease;
}

.use-button.danger {
  --background: var(--ion-color-danger);
}

.use-button.medium {
  --background: var(--ion-color-medium);
}
</style>