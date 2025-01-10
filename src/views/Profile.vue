<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="name-header">
          <h1>{{ userStore.userName }}</h1>
          <h4>{{ userStore.userEmail }}</h4>
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div class="options">
        <a @click.prevent="goToProfilePopup" class="page-option">
          <ion-icon :icon="personOutline" />
          <ion-label>Profil Bilgileri</ion-label>
        </a>
        <a href="/tabs/tab3" class="page-option">
          <ion-icon :icon="cardOutline" />
          <ion-label>Kartlarım</ion-label>
        </a>
        <a @click.prevent="goToFavoriler" class="page-option">
          <ion-icon :icon="heartOutline" />
          <ion-label>Favoriler</ion-label>
        </a>
             <a @click.prevent="goToKullanilanlar" class="page-option">
  <ion-icon :icon="basketOutline" />
  <ion-label>Kullanılanlar</ion-label>
</a>
        <a @click.prevent="goToAyarlarPopup" class="page-option">
          <ion-icon :icon="settingsOutline" />
          <ion-label>Ayarlar</ion-label>
        </a>
        <a @click.prevent="logout" class="page-option">
          <ion-icon :icon="logOutOutline" />
          <ion-label>Çıkış Yap</ion-label>
        </a>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import {
  personOutline,
  cardOutline,
  heartOutline,
  settingsOutline,
  logOutOutline,
  basketOutline
} from 'ionicons/icons';
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()

const goToProfilePopup = () => {
  router.push('/profilpopup');
};

const goToKullanilanlar = () => {
  router.push('/tabs/tab8');
};

const goToAyarlarPopup = () => {
  router.push('/ayarlarpopup');
};

const goToFavoriler = () => {
  router.push('/tabs/tab7');
};

// Router instance
const router = useRouter();

// Kullanıcı bilgileri için reactive değişkenler
const userEmail = ref('');
const userName = ref('');

// Kullanıcı bilgilerini yükleme
onMounted(async () => {
  try {
    const emailData = await SecureStoragePlugin.get({ key: 'userEmail' });
    const nameData = await SecureStoragePlugin.get({ key: 'userName' });

    userEmail.value = emailData.value || 'admin@admin.com';
    userName.value = nameData.value || 'İsim Soyisim';

    // Store'u güncelle
    userStore.updateUserInfo(userName.value, userEmail.value)
  } catch (error) {
    console.error('Error loading user data:', error);
  }
});

// Çıkış yapma fonksiyonu
const logout = async () => {
  try {
    // Saklanan kullanıcı bilgilerini temizle
    await SecureStoragePlugin.remove({ key: 'userEmail' });
    await SecureStoragePlugin.remove({ key: 'userName' });
    await SecureStoragePlugin.remove({ key: 'userId' });

    // Tarayıcıda yerel depolamayı (localStorage, sessionStorage) temizle
    localStorage.clear();
    sessionStorage.clear();

    // Kullanıcıyı giriş sayfasına yönlendir
    router.push('/');
  } catch (error) {
    console.error('Error during logout:', error);
  }
};
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
  margin: 20px;
  padding: 20px;
  border-width: 2px;
  border-style: groove;
  border-radius: 10px;
}

.page-option ion-label {
  margin-left: 50px;
  font-size: 20px;
}

.page-option ion-icon {
  font-size: 32px;
  margin-left: 20px;
}
</style>
