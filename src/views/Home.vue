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
            <ion-icon
                id="share"
                aria-hidden="true"
                :icon="shareSocialSharp" />
            <ion-icon
                id="bookmark"
                :class="{ gray: isBookmarked(index) }"
                aria-hidden="true"
                :icon="bookmarkSharp"
                @click="toggleBookmark(index)" />
            <ion-icon
                id="heart"
                :class="{ red: isHearted(index) }"
                aria-hidden="true"
                :icon="heart"
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
            <p><ion-icon aria-hidden="true" :icon="cardOutline" /> {{ campaign.cardId }}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline" /> {{ campaign.companyId }}</p>
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
            <ion-icon
                id="share"
                aria-hidden="true"
                :icon="shareSocialSharp" />
            <ion-icon
                id="bookmark"
                :class="{ gray: isBookmarked(index) }"
                aria-hidden="true"
                :icon="bookmarkSharp"
                @click="toggleBookmark(index)" />
            <ion-icon
                id="heart"
                :class="{ red: isHearted(index) }"
                aria-hidden="true"
                :icon="heart"
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
            <p><ion-icon aria-hidden="true" :icon="cardOutline" /> {{ campaign.cardId }}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline" /> {{ campaign.companyId }}</p>
          </div>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonRow, IonCol } from '@ionic/vue';
import { calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline, shareSocialSharp, bookmarkSharp, heart } from 'ionicons/icons';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const campaigns = ref([]);
const hearts = ref(new Set());
const bookmarks = ref(new Set());

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8082/api/campaigns');
    campaigns.value = response.data;
  } catch (error) {
    console.error('Data could not be retrieved:', error);
  }
});

const toggleHeart = (index: number) => {
  if (hearts.value.has(index)) {
    hearts.value.delete(index);
  } else {
    hearts.value.add(index);
  }
};

const toggleBookmark = (index: number) => {
  if (bookmarks.value.has(index)) {
    bookmarks.value.delete(index);
  } else {
    bookmarks.value.add(index);
  }
};

const isHearted = (index: number) => hearts.value.has(index);
const isBookmarked = (index: number) => bookmarks.value.has(index);

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
