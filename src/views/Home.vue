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
            <a href="tabs/tab4"><ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp" /></a>
            <a href="tabs/tab4"><ion-icon id="bookmark" aria-hidden="true" :icon="bookmarkSharp" /></a>
            <a href="tabs/tab4"><ion-icon id="heart" aria-hidden="true" :icon="heart" /></a>
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

      <!--  -->

      <ion-toolbar>
        <ion-title class="title">Son Kampanyalar</ion-title>
        <hr class="line">
      </ion-toolbar>

      <div v-for="(campaign, index) in campaigns" :key="campaign.id" class="container">
        <div class="images-container">
          <img :src="campaign.image" :alt="campaign.alt">
          <div class="click-icons">
            <a href="tabs/tab4"><ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp" /></a>
            <a href="tabs/tab4"><ion-icon id="bookmark" aria-hidden="true" :icon="bookmarkSharp" /></a>
            <a href="tabs/tab4"><ion-icon id="heart" aria-hidden="true" :icon="heart" /></a>
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

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8082/api/campaigns');
    campaigns.value = response.data;
  } catch (error) {
    console.error('Data could not be retrieved:', error);
  }
});

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
  /*background-color: aqua;*/
}

.container img {
  width: 100%;
  height: auto;
  /*border-radius: 10px;*/
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

.click-icons a {
  text-decoration: none;
  color: rgb(50, 50, 50);
}

#share {
  margin-right: 10px;
  margin-top: 10px;
}

#bookmark {
  margin-right: 10px;
  margin-top: 10px;
}

#heart {
  margin-right: 10px;
  margin-top: 10px;
}

.container p {
  font-size: 16px;
  line-height: 22px;
  color: black;
  /*background-color: blueviolet;*/
  margin: 0 10px;
  width: 100%;
  padding: 10px 16px;
  font-weight: bold;
}

.card-link {
  font-size: 16px;
  line-height: 22px;
  color: black;
  /*background-color: blueviolet;*/
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
  /*background-color: rgb(255, 99, 203);*/
  width: 100%;
}

.inner-info p {
  /*background-color: yellow;*/
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