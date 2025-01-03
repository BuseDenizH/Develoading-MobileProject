<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Anasayfa</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-toolbar>
        <ion-title class="title">En beğenilenler</ion-title>
        <div class="filter-bar">
          <label for="filter">filtre</label>
          <select id="filter" v-model="selectedFilter" @change="applyFilter">
            <option value="normal">Normal</option>
            <option value="cards">Kartlarıma Göre</option>

          </select>
        </div>
        <hr class="line">
      </ion-toolbar>

      <!-- Kampanyalar Listesi -->
      <div v-for="campaign in campaignsWithDetails" :key="campaign.id" class="container">
      <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
          <div class="click-icons">
            <ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp" @click="shareContent(campaign)" />
            <ion-icon
                id="heart"
                :class="{ red: heartStore.hearts.has(campaign.id) }"
                aria-hidden="true"
                :icon="heart"
                @click="toggleHeart(campaign.id)"
            />
          </div>
        </div>
        <router-link :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: campaign.id } }"
          class="card-link">{{ campaign.detail }}</router-link>
        <hr class="inline">
        <div class="under-container">
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="calendarClearOutline" /> {{ formatDate(campaign?.beginDate) }}</p>
            <p><ion-icon aria-hidden="true" :icon="hourglassOutline" /> {{ formatDate(campaign?.endDate) }}</p>
          </div>
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="cardOutline" /> {{ campaign.cardName }}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline" /> {{ campaign.companyName }}</p>
          </div>
        </div>
      </div>

      <ion-toolbar>
        <ion-title class="title">Son Kampanyalar</ion-title>
        <hr class="line">
      </ion-toolbar>

      <div v-for="campaign in campaignsWithDetails" :key="campaign.id" class="container">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
          <div class="click-icons">
            <ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp" @click="shareContent(campaign)" />
            <ion-icon
                id="heart"
                :class="{ red: heartStore.hearts.has(campaign.id) }"
                aria-hidden="true"
                :icon="heart"
                @click="toggleHeart(campaign.id)"
            />
          </div>
        </div>
        <router-link :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: campaign.id } }"
                     class="card-link">{{ campaign.detail }}</router-link>
        <hr class="inline">
        <div class="under-container">
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="calendarClearOutline" /> {{ formatDate(campaign?.beginDate) }}</p>
            <p><ion-icon aria-hidden="true" :icon="hourglassOutline" /> {{ formatDate(campaign?.endDate) }}</p>
          </div>
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="cardOutline" /> {{ campaign.cardName }}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline" /> {{ campaign.companyName }}</p>
          </div>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon } from '@ionic/vue';
import { calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline, shareSocialSharp, heart } from 'ionicons/icons';
import { ref, onMounted, computed  } from 'vue';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';
import axios from 'axios';
import { useRouter } from 'vue-router'; // useRouter'ı import et
import { Share } from '@capacitor/share';
import { useHeartStore } from '@/stores/heartStore'
import { useCardStore } from '@/stores/cardStore';

const router = useRouter(); // useRouter ile router'ı alıyoruz
const campaigns = ref<any[]>([]); // Kampanyalar listesi
const hearts = ref<Set<number>>(new Set());
const userId = ref<number>(0);  // Kullanıcı ID'si başlangıçta 0
const selectedFilter = ref('normal'); // Filtreyi 'normal' olarak başlatıyoruz
const isProcessing = ref(false); // İşlem kilidi için ref tanımlama
const heartStore = useHeartStore()
const cardStore = useCardStore();

// Kullanıcı ID'sini SecureStorage'den al ve ilgili API isteklerini yap
onMounted(async () => {
  try {
    const storedUserId = await SecureStoragePlugin.get({ key: 'userId' });
    userId.value = Number(storedUserId.value);

    if (userId.value > 0) {
      // Kart ve şirket bilgilerini önbelleğe al
      await Promise.all([
        cardStore.fetchCardNames(),
        cardStore.fetchCompanyNames()
      ]);
      await fetchCampaigns();
    }
  } catch (error) {
    console.error('Data could not be retrieved:', error);
  }
});

const shareContent = async (campaign: any) => {
  try {
    await Share.share({
      title: 'Kampanya Detayı',
      text: campaign.detail,  // Kampanya açıklaması
      url: campaign.url,      // Kampanya URL'si
    });
    console.log('Paylaşım başarılı');
  } catch (error) {
    console.error('Paylaşım hatası:', error);
  }
};

// watch kısmını kaldırıp yerine computed ekleyin
const campaignsWithDetails = computed(() => {
  return campaigns.value.map(campaign => ({
    ...campaign,
    cardName: cardStore.getCardName(campaign.cardId),
    companyName: cardStore.getCompanyName(campaign.companyId)
  }));
});

const getCardName = async (cardId: number) => {
  try {
    const response = await axios.get(`http://localhost:8082/api/card-name/${cardId}`);
    return response.data;
  } catch (error) {
    console.error('Kart adı alınamadı:', error);
    return "Bilinmeyen Kart";
  }
};

const getCompanyName = async (companyId: number) => {
  try {
    const response = await axios.get(`http://localhost:8082/api/company-name/${companyId}`);
    return response.data;
  } catch (error) {
    console.error('Şirket adı alınamadı:', error);
    return "Bilinmeyen Şirket";
  }
};

// Kampanyaları getiren fonksiyon
const fetchCampaigns = async () => {
  try {
    let url = '';

    if (selectedFilter.value === 'normal') {
      url = 'http://localhost:8082/api/campaigns';
    } else if (selectedFilter.value === 'cards' && userId.value > 0) {
      url = `http://localhost:8082/api/user-cards/${userId.value}/campaigns`;
    }

    const response = await axios.get(url);
    campaigns.value = response.data;

    const likedResponse = await axios.get(`http://localhost:8082/api/likedCampaigns/${userId.value}`);
    const likedCampaigns = likedResponse.data;

    // Store'u güncelle
    const likedIds = likedCampaigns.map((campaign: any) => campaign.campaignId);
    heartStore.setInitialHearts(likedIds);

  } catch (error) {
    console.error('Kampanyalar yüklenemedi:', error);
  }
};

// Tarihi formatla
const formatDate = (date: string) => date.split('T')[0];

// Beğenme durumunun kontrolü
const isHearted = (campaignId: number) => hearts.value.has(campaignId);

// Beğenme ikonunu toggle et ve beğenilen kampanyayı kaydet/sil
// Script kısmının en üstünde, diğer ref tanımlamalarının yanına ekleyin:

// toggleHeart fonksiyonunu bu şekilde güncelleyin:
const toggleHeart = async (campaignId: number) => {
  if (userId.value === 0) {
    console.error("Geçerli bir kullanıcı bulunamadı.");
    return;
  }

  if (isProcessing.value) {
    console.log('İşlem devam ediyor, lütfen bekleyin...');
    return;
  }

  isProcessing.value = true;

  try {
    if (heartStore.hearts.has(campaignId)) {
      const unlikeResponse = await axios.delete(`http://localhost:8082/api/likedCampaigns/${userId.value}/${campaignId}`);

      if (unlikeResponse.status === 200) {
        heartStore.updateHearts(campaignId, false);
        await axios.post(`http://localhost:8082/api/campaigns/${campaignId}/unlike`);
      }
    } else {
      const likeResponse = await axios.post('http://localhost:8082/api/likedCampaigns', {
        userId: userId.value,
        campaignId: campaignId
      });

      if (likeResponse.status === 200) {
        heartStore.updateHearts(campaignId, true);
        await axios.post(`http://localhost:8082/api/campaigns/${campaignId}/like`);
      }
    }
  } catch (error) {
    console.error('Error with liking/unliking campaign:', error);
  } finally {
    isProcessing.value = false;
  }
};

// Home.vue içinde
const updateLikeCount = (campaignId, newLikeCount) => {
  // Kampanya beğenildi veya beğenmeden çıkarıldıktan sonra
  router.push({
    path: `/campaign/${campaignId}`,
    query: { likeCount: newLikeCount } // Yeni likeCount değerini query olarak gönderiyoruz
  });
};




// Filtreyi uygula
const applyFilter = () => {
  fetchCampaigns();
};
</script>


<style scoped>
.title {
  padding: 20px 16px 5px 16px;
  text-align: left;
  color: rgb(248, 79, 0);
}

.line {
  background-color: rgb(248, 79, 0);
  margin-left: 16px;
  margin-right: 16px;
  height: 2px;
}

.inline {
  background-color: gray;
  margin-left: 16px;
  margin-right: 16px;
  height: 2px;
}

.container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  text-align: center;
  padding-top: 0px;
  margin-top: 16px;
  margin-left: 16px;
  margin-right: 16px;
  border-radius: 10px;
  border-style: groove;
  border-color: black;
  border-width: 2px;
}

.container img {
  width: 100%;
  height: auto;
}

.images-container {
  position: relative;
  display: inline;
  width: 100%;
}

.click-icons {
  position: absolute;
  top: 0;
  right: 0;
  margin-right: 10px;
  font-size: 24px;
}

.click-icons ion-icon {
  cursor: pointer;
}

.white {
  color: white;
}

.red {
  color: red;
}

.gray {
  color: gray;
}

.container p {
  font-size: 16px;
  line-height: 22px;
  color: black;
  margin: 0 10px;
  width: 100%;
  padding: 10px 16px;
  font-weight: bold;
}

.card-link {
  font-size: 16px;
  line-height: 22px;
  color: black;
  margin: 0 10px;
  width: 100%;
  padding: 10px 16px;
  font-weight: bold;
  text-decoration: none;
}

.filter-bar {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-right: 16px;
  margin-top: 10px;
}

.filter-bar label {
  margin-right: 8px;
  font-size: 14px;
  color: black;
}

.filter-bar select {
  padding: 5px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
}



.under-container {
  display: flex;
  width: 100%;
  margin-bottom: 16px;
}

.inner-info {
  width: 100%;
}

.inner-info p {
  width: max-content;
  color: black;
  font-weight: normal;
  padding: 0%;
  margin-top: 5px;
}

ion-icon {
  --ionicon-stroke-width: 40px;
}

.red {
  color: red;
}
</style>
