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
        <ion-title class="header-title">Beğendiğim Kampanyalar</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div v-if="campaignsWithDetails.length === 0" class="empty-state">
        <div class="empty-state-content">
          <p>Henüz hiçbir kampanyayı beğenmediniz. Kampanyalara göz atmak için ana sayfamızı ziyaret ediniz.</p>
          <ion-button @click="goToHomePage" color="danger" class="home-button">
            Ana Sayfa
          </ion-button>
        </div>
      </div>
      <div v-else v-for="campaign in campaignsWithDetails" :key="campaign.id" class="container"
        :class="{ 'expired': campaign.isExpired }">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
          <div class="click-icons">
            <ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp"
              @click="!campaign.isExpired && shareContent(campaign)" :class="{ 'disabled': campaign.isExpired }" />
            <ion-icon id="heart" :class="{
              'red': heartStore.hearts.has(campaign.id),
              'disabled': campaign.isExpired
            }" aria-hidden="true" :icon="heart" @click="!campaign.isExpired && toggleHeart(campaign.id)" />
          </div>
        </div>
        <router-link v-if="!campaign.isExpired"
          :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: campaign.id } }" class="card-link">
          {{ campaign.detail }}
        </router-link>
        <span v-else class="card-link expired-text">{{ campaign.detail }}</span>
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
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonButton, IonIcon, IonLabel } from '@ionic/vue';
import {
  arrowBackOutline,
  calendarClearOutline,
  hourglassOutline,
  cardOutline,
  storefrontOutline,
  shareSocialSharp,
  heart
} from 'ionicons/icons';
import { ref, computed } from 'vue';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { Share } from '@capacitor/share';
import { onIonViewWillEnter } from '@ionic/vue';
import { useHeartStore } from '@/stores/heartStore'
import { useCardStore } from '@/stores/cardStore';

const router = useRouter();
const userId = ref<number>(0);
const hearts = ref<Set<number>>(new Set());
const likedCampaigns = ref<any[]>([]);
const isProcessing = ref(false);
const heartStore = useHeartStore()
const cardStore = useCardStore();


// Kampanyaları sıralama ve detayları ekleme
const campaignsWithDetails = computed(() => {
  // Önce tüm kampanyaları işle ve isExpired bilgisini ekle
  const processedCampaigns = likedCampaigns.value.map(campaign => {
    const today = new Date();
    const expiryDate = new Date(campaign.endDate);
    const isExpired = today > expiryDate;

    return {
      ...campaign,
      cardName: cardStore.getCardName(campaign.cardId),
      companyName: cardStore.getCompanyName(campaign.companyId),
      isExpired
    };
  });

  // Aktif ve süresi geçmiş kampanyaları ayır
  const activeCampaigns = processedCampaigns.filter(campaign => !campaign.isExpired);
  const expiredCampaigns = processedCampaigns.filter(campaign => campaign.isExpired);

  // Aktif kampanyaları bitiş tarihine göre sırala (yakın tarihli önce)
  activeCampaigns.sort((a, b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());

  // Süresi geçmiş kampanyaları bitiş tarihine göre sırala (son bitenler önce)
  expiredCampaigns.sort((a, b) => new Date(b.endDate).getTime() - new Date(a.endDate).getTime());

  // Önce aktif kampanyalar, sonra süresi geçmişler
  return [...activeCampaigns, ...expiredCampaigns];
});

// Sayfa yüklendiğinde
onIonViewWillEnter(async () => {
  try {
    const storedUserId = await SecureStoragePlugin.get({ key: 'userId' });
    userId.value = Number(storedUserId.value);

    if (userId.value > 0) {
      // Kart ve şirket bilgilerini önbelleğe al
      await Promise.all([
        cardStore.fetchCardNames(),
        cardStore.fetchCompanyNames()
      ]);
      await fetchLikedCampaigns();
    }
  } catch (error) {
    console.error('Kullanıcı bilgileri alınamadı:', error);
  }
});

// Beğenilen kampanyaları getir
const fetchLikedCampaigns = async () => {
  try {
    const likedResponse = await axios.get(`http://18.153.153.139:8082/api/likedCampaigns/${userId.value}`);
    const likedCampaignIds = likedResponse.data.map((item: any) => item.campaignId);

    // Store'u güncelle
    heartStore.setInitialHearts(likedCampaignIds);

    const campaignsResponse = await axios.get('http://18.153.153.139:8082/api/campaigns');
    const allCampaigns = campaignsResponse.data;

    likedCampaigns.value = allCampaigns.filter((campaign: any) =>
      likedCampaignIds.includes(campaign.id)
    );

  } catch (error) {
    console.error('Beğenilen kampanyalar getirilemedi:', error);
  }
};

const shareContent = async (campaign: any) => {
  if (campaign.isExpired) return;

  try {
    await Share.share({
      title: 'Kampanya Detayı',
      text: campaign.detail,
      url: campaign.url,
    });
  } catch (error) {
    console.error('Paylaşım hatası:', error);
  }
};

// Beğeni kaldırma fonksiyonu
const toggleHeart = async (campaignId: number) => {
  const campaign = campaignsWithDetails.value.find(c => c.id === campaignId);
  if (campaign?.isExpired) return;

  if (userId.value === 0) {
    console.error("Geçerli bir kullanıcı bulunamadı.");
    return;
  }

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
      const unlikeResponse = await axios.delete(`http://18.153.153.139:8082/api/likedCampaigns/${userId.value}/${campaignId}`);

      if (unlikeResponse.status === 200) {
        heartStore.updateHearts(campaignId, false);
        await axios.post(`http://18.153.153.139:8082/api/campaigns/${campaignId}/unlike`);
        likedCampaigns.value = likedCampaigns.value.filter(camp => camp.id !== campaignId);
      }
    } else {
      const likeResponse = await axios.post('http://18.153.153.139:8082/api/likedCampaigns', {
        userId: userId.value,
        campaignId: campaignId
      });

      if (likeResponse.status === 200) {
        heartStore.updateHearts(campaignId, true);
        await axios.post(`http://18.153.153.139:8082/api/campaigns/${campaignId}/like`);
      }
    }
  } catch (error) {
    console.error('Error with liking/unliking campaign:', error);
  } finally {
    isProcessing.value = false;
  }
};


const formatDate = (date: string) => {
  if (!date) return '';
  const [year, month, day] = date.split('T')[0].split('-');
  return `${day}/${month}/${year}`;
};

const goToHomePage = () => {
  router.push('/tabs/tab1');
};

const goBack = () => {
  router.push('/tabs/tab4');
};
</script>

<style scoped>
.header-title {
  font-size: 16px !important;
  white-space: nowrap !important;
  overflow: visible !important;
  text-overflow: clip !important;
  padding: 0 !important;
  max-width: none !important;
}

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



.expired {
  opacity: 0.6;
  position: relative;
  pointer-events: none;
  border-color: #ccc !important;
}

.expired::after {
  content: "Kampanya Süresi Doldu";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  z-index: 1;
  pointer-events: none;
}

.expired img {
  filter: grayscale(100%);
}

.disabled {
  opacity: 0.5;
  cursor: not-allowed !important;
}

.expired-text {
  color: #999;
  cursor: default;
}

.expired .inner-info p {
  color: #999 !important;
}

.expired .click-icons ion-icon {
  color: #ccc !important;
}



.empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  margin: 20px;
  text-align: center;
  color: #666;
  border: 1px dashed #ccc;
  border-radius: 8px;
}

.empty-state-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.empty-state-content p {
  margin-bottom: 20px;
  font-size: 16px;
  line-height: 1.5;
  color: #666;
  max-width: 300px;
}

.home-button {
  width: 200px;
  --padding-top: 15px;
  --padding-bottom: 15px;
  font-weight: bold;
  text-transform: none;
  --background: var(--ion-color-danger);
}

.home-button:hover {
  --background: var(--ion-color-danger-shade);
}
</style>