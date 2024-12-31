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
        <ion-title class="header-title">Kullanılan Kampanyalar</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <!-- Kullanılan Kampanyalar Listesi -->
      <div v-for="campaign in usedCampaigns" :key="campaign.id" class="container">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
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
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonButton, IonIcon, IonLabel } from '@ionic/vue';
import { arrowBackOutline, calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline } from 'ionicons/icons';
import { ref } from 'vue';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { onIonViewWillEnter } from '@ionic/vue';

const router = useRouter();
const userId = ref<number>(0);
const usedCampaigns = ref<any[]>([]);

// Sayfa yüklendiğinde
onIonViewWillEnter(async () => {
  try {
    const storedUserId = await SecureStoragePlugin.get({ key: 'userId' });
    userId.value = Number(storedUserId.value);

    if (userId.value > 0) {
      await fetchUsedCampaigns();
    }
  } catch (error) {
    console.error('Kullanıcı bilgileri alınamadı:', error);
  }
});

// Kullanılan kampanyaları getir
const fetchUsedCampaigns = async () => {
  try {
    // Kullanıcıya ait kullanılan kampanyaları al
    const response = await axios.get(`http://localhost:8082/api/used-campaigns/campaign-details/${userId.value}`);
    const campaigns = response.data;

    // Kampanyaların her biri için detayları çek
    for (const campaign of campaigns) {
      const campaignDetails = await fetchCampaignDetails(campaign.id);
      campaign.details = campaignDetails;  // Kampanyaya detayları ekle
    }

    usedCampaigns.value = campaigns;
    console.log("Kullanılan kampanyalar:", usedCampaigns.value);
  } catch (error) {
    console.error('Kullanılan kampanyalar getirilemedi:', error);
  }
};

// Kampanyanın detaylarını getir
const fetchCampaignDetails = async (campaignId: number) => {
  try {
    const response = await axios.get(`http://localhost:8082/api/campaigns}`);
    return response.data;
  } catch (error) {
    console.error('Kampanya detayları getirilemedi:', error);
    return {};  // Hata durumunda boş bir obje döner
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
</style>
