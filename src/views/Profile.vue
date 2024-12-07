<template>
  <ion-page>
    
    <ion-header>
      <ion-toolbar>
        <ion-title class="name-header"><h1>İsim Soyisim</h1><h4>admin@admin.com</h4></ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">

      <div class="options">
        <a href="/tabs/tab4" class="page-option">
          <ion-icon aria-hidden="true" :icon="personOutline" />
          <ion-label>Profil Bilgileri</ion-label>
        </a>
        <a href="/tabs/tab3" class="page-option">
          <ion-icon aria-hidden="true" :icon="cardOutline" />
          <ion-label>Kartlarım</ion-label>
        </a>
        <a href="/tabs/tab1" class="page-option">
          <ion-icon aria-hidden="true" :icon="heartOutline" />
          <ion-label>Favoriler</ion-label>
        </a>
        <a href="/tabs/tab4" class="page-option">
          <ion-icon aria-hidden="true" :icon="settingsOutline" />
          <ion-label>Ayarlar</ion-label>
        </a>
        <a href="/tabs/tab1" class="page-option">
          <ion-icon aria-hidden="true" :icon="logOutOutline" />
          <ion-label>Çıkış Yap</ion-label>
        </a>
      </div>

    </ion-content>

  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import { personOutline, cardOutline, heartOutline, settingsOutline, logOutOutline } from 'ionicons/icons';
</script>

<style>
.name-header {
background-color: rgb(248, 79, 0);
padding: 30px 0px;
text-align: left;
color: white;
}

.name-header h1 {
margin-left: 30px;
font-size: 30px;
font-weight: bolder;
}

.name-header h4 {
margin-left: 30px;
font-size: 20px;
font-weight: normal;
}

.options {
margin-top: 50px;
}

.options a {
color: black;
text-decoration: none;
}

.page-option {
display: flex;
/*background-color: aqua;*/
margin: 20px;
padding: 20px;
border-width: 2px;
border-style: groove;
border-radius: 10px;
}

.page-option ion-label {
/*background-color: brown;*/
margin-left: 50px;
font-size: 20px;
}

.page-option ion-icon {
font-size: 32px;
margin-left: 20px;
/*background-color: yellow;*/
}
</style>
