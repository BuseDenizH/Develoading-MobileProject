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
        <ion-title class="header-title">Kullandığım Kampanyalar</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div v-for="campaign in campaignsWithDetails" :key="campaign.id" class="container">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt" />
          <div class="click-icons">
            <ion-icon
              id="heart"
              :class="{ red: heartStore.hearts.has(campaign.id) }"
              aria-hidden="true"
              :icon="heart"
              @click="toggleHeart(campaign.id)"
            />
          </div>
        </div>
        <router-link
          :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: campaign.id } }"
          class="card-link"
        >
          {{ campaign.detail }}
        </router-link>
        <hr class="inline" />
        <div class="under-container">
          <div class="inner-info">
            <p>
              <ion-icon aria-hidden="true" :icon="calendarClearOutline" />
              {{ formatDate(campaign?.beginDate) }}
            </p>
            <p>
              <ion-icon aria-hidden="true" :icon="hourglassOutline" />
              {{ formatDate(campaign?.endDate) }}
            </p>
          </div>
          <div class="inner-info">
            <p>
              <ion-icon aria-hidden="true" :icon="cardOutline" />
              {{ campaign.cardName }}
            </p>
            <p>
              <ion-icon aria-hidden="true" :icon="storefrontOutline" />
              {{ campaign.companyName }}
            </p>
          </div>
        </div>
        <!-- Buton yerine "Kullanıldı" kutusu gösterilecek -->
        <div v-if="campaign.isUsed" class="used-box">Kullanıldı</div>
       <ion-button
  :disabled="campaign.isUsed"
  class="used-button"
  @click="markAsUsed(campaign.id)"
>
  {{ campaign.isUsed ? "Kullanıldı" : "Kullanıldı" }}
</ion-button>

      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonButton, IonIcon, IonLabel } from '@ionic/vue';
import { arrowBackOutline, calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline, heart } from 'ionicons/icons';
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { onIonViewWillEnter } from '@ionic/vue';
import { useHeartStore } from '@/stores/heartStore';
import { useCardStore } from '@/stores/cardStore';
import axios from 'axios';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';

const router = useRouter();
const userId = ref<number>(0);
const heartStore = useHeartStore();
const cardStore = useCardStore();
const usedCampaigns = ref<any[]>([]);


// Kampanyaları computed property ile birleştir
const campaignsWithDetails = computed(() => {
  return usedCampaigns.value.map(campaign => ({
    ...campaign,
    cardName: cardStore.getCardName(campaign.cardId),
    companyName: cardStore.getCompanyName(campaign.companyId)
  }));
}); 

// Kullanılan kampanyaları getir
onIonViewWillEnter(async () => {
  try {
    const storedUserId = await SecureStoragePlugin.get({ key: 'userId' });
    userId.value = Number(storedUserId.value);

    if (userId.value > 0) {
      await cardStore.fetchCardNames();
      await cardStore.fetchCompanyNames();
      await fetchUsedCampaigns();
    }
  } catch (error) {
    console.error('Kullanıcı bilgileri alınamadı:', error);
  }
});

// Kullanılan kampanyaları API'den çek
const fetchUsedCampaigns = async () => {
  try {
    const response = await axios.get(`http://localhost:8082/api/used-campaigns/campaign-details/${userId.value}`);
    usedCampaigns.value = response.data.map((campaign: any) => ({
      ...campaign,
      cardName: cardStore.getCardName(campaign.cardId),
      companyName: cardStore.getCompanyName(campaign.companyId),
    }));
  } catch (error) {
    console.error('Kullanılan kampanyalar getirilemedi:', error);
  }
};

// Kampanyayı "Kullandım" olarak işaretle
const markAsUsed = async (campaignId: number) => {
  try {
    const response = await axios.post(`http://localhost:8082/api/used-campaigns/mark-used`, {
      userId: userId.value,
      campaignId: campaignId,
    });

    if (response.status === 200) {
      const campaign = usedCampaigns.value.find((camp) => camp.id === campaignId);
      if (campaign) campaign.isUsed = true;
    }
  } catch (error) {
    console.error('Kampanya kullandım olarak işaretlenemedi:', error);
  }
};

const formatDate = (date: string) => date?.split('T')[0];

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

.used-button {
  --background: gray;  /* Gri arka plan rengi */
  --color: white;  /* Yazı rengini beyaz yapalım */
  --opacity: 0.6;  /* Butonun opaklığını düşürerek gri tonları ekleyelim */
  cursor: not-allowed;  /* Tıklanamaz hale getirelim */
}

.used-button[disabled] {
  background-color: gray;  /* Gri renkteki arka plan */
  color: #fff;  /* Beyaz metin */
  pointer-events: none;  /* Butona tıklanmasını engelle */
}


/* Hover efekti */
.use-btn:hover {
  background-color: #45a049; /* Daha koyu yeşil */
}
</style>
