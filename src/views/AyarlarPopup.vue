<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="name-header">
          <h1>Ayarlar</h1>
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div class="options">
        <a href="/tabs/tab4" class="page-option">
          <ion-label>Gizlilik Politikası <span class="arrow">></span></ion-label>
        </a>
        <a href="/tabs/tab3" class="page-option">
          <ion-label>Bildirimler <span class="arrow">></span></ion-label>
        </a>
        <a href="/tabs/tab1" class="page-option">
          <ion-label>Bize Ulaşın <span class="arrow">></span></ion-label>
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

// Router instance
const router = useRouter();

// Kullanıcı bilgileri için reactive değişkenler
const userEmail = ref('');
const userName = ref('');

// Kullanıcı bilgilerini yükleme
onMounted(async () => {
  try {
    // Saklanan kullanıcı bilgilerini al
    const emailData = await SecureStoragePlugin.get({ key: 'userEmail' });
    const nameData = await SecureStoragePlugin.get({ key: 'userName' });

    // Değişkenlere ata
    userEmail.value = emailData.value || 'admin@admin.com';
    userName.value = nameData.value || 'İsim Soyisim';
  } catch (error) {
    console.error('Error loading user data:', error);
  }
});
</script>

<style>
.name-header {
  background-color: rgb(248, 79, 0);
  padding: 10px 0px;
  text-align: left;
  color: white;
  position: sticky;
  top: 0;
  z-index: 1;
}

.name-header h1 {
  margin-left: 30px;
  font-size: 30px; /* Ayarlar yazısı için boyut 26 (2 derece artırıldı) */
  font-weight: bold; /* Yazıyı kalın yap */
}

.options {
  margin-top: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0;
}

.page-option {
  display: flex;
  align-items: center;
  margin: 0;
  padding: 20px;
  width: 100%;
  border-width: 1px;
  border-style: groove;
  border-radius: 0;
  text-decoration: none;
  color: black;
  box-sizing: border-box;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2); /* Gölgelendirme daha belirgin hale getirildi */
}

.page-option ion-label {
  font-size: 22px; /* Diğer başlıklar için boyut 22 */
  font-weight: bold; /* Yazıyı kalın yap */
  text-align: left; /* Yazıyı sola hizala */
  display: flex;
  justify-content: space-between; /* Ok işaretini sağa yasla */
  align-items: center;
  width: 100%;
}

.arrow {
  margin-left: auto; /* Ok işaretini en sağa it */
  font-size: 24px;
}
</style>
