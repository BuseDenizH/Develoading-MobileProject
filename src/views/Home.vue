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
        <hr class="line">
      </ion-toolbar>

      <div v-for="(campaign, index) in campaigns" :key="campaign.id" class="container">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
          <div class="click-icons">
            <ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp" />
            <ion-icon id="heart" :class="{ red: isHearted(index) }" aria-hidden="true" :icon="heart"
              @click="toggleHeart(index)" />
          </div>
        </div>
        <router-link :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: campaign.id } }"
          class="card-link">{{ campaign.detail }}</router-link>
        <hr class="inline">
        <div class="under-container">
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="calendarClearOutline" /> {{ campaign.beginDate.split('T')[0] }}</p>
            <p><ion-icon aria-hidden="true" :icon="hourglassOutline" /> {{ campaign.endDate.split('T')[0] }}</p>
          </div>
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="cardOutline" /> {{ getMappedCardName(campaign.cardId) }}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline" /> {{ getMappedCompanyName(campaign.companyId) }}
            </p>
          </div>
        </div>
      </div>

      <ion-toolbar>
        <ion-title class="title">Son Kampanyalar</ion-title>
        <hr class="line">
      </ion-toolbar>

      <div v-for="(campaign, index) in campaigns" :key="campaign.id" class="container">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
          <div class="click-icons">
            <ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp" />
            <ion-icon id="heart" :class="{ red: isHearted(index) }" aria-hidden="true" :icon="heart"
              @click="toggleHeart(index)" />
          </div>
        </div>
        <router-link :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: campaign.id } }"
          class="card-link">{{ campaign.detail }}</router-link>
        <hr class="inline">
        <div class="under-container">
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="calendarClearOutline" /> {{ campaign.beginDate.split('T')[0] }}</p>
            <p><ion-icon aria-hidden="true" :icon="hourglassOutline" /> {{ campaign.endDate.split('T')[0] }}</p>
          </div>
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="cardOutline" /> {{ getMappedCardName(campaign.cardId) }}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline" /> {{ getMappedCompanyName(campaign.companyId) }}
            </p>
          </div>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonRow, IonCol } from '@ionic/vue';
import { calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline, shareSocialSharp, heart } from 'ionicons/icons';
import { ref, onMounted } from 'vue';
import axios from 'axios';
import {
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
} from '@ionic/vue';

const campaigns = ref([]);
const hearts = ref(new Set());

onMounted(async () => {
  try {
    const response = await axios.get('http://18.153.153.139:8082/api/campaigns');
    campaigns.value = response.data;
  } catch (error) {
    console.error('Data could not be retrieved:', error);
  }
});

const cardIdMap = {
  1: "Axess",
  2: "CardFinans",
  3: "Bonus",
  4: "Maximum",
  5: "World",
  6: "Paraf",
};

const companyIdMap = {
  1: "Akaryakıt",
  2: "Giyim/Kozmetik",
  3: "Elektronik",
  4: "Yeme/İçme",
  5: "Turizm/Seyahat",
  6: "Telekomünikasyon",
  7: "Araç Kiralama",
};

// ID'yi metne dönüştürme fonksiyonu
const getMappedCardName = (cardId: number) => {
  return cardIdMap[cardId] || "Bilinmeyen Kart";
};

const getMappedCompanyName = (companyId: number) => {
  return companyIdMap[companyId] || "Bilinmeyen Şirket";
};

const toggleHeart = (index: number) => {
  if (hearts.value.has(index)) {
    hearts.value.delete(index);
  } else {
    hearts.value.add(index);
  }
};

const isHearted = (index: number) => hearts.value.has(index);

console.log(campaigns.value);
</script>

<style>
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
</style>
