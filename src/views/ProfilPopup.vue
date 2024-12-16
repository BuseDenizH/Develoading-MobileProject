<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="name-header">
          <h1>Profil</h1> <!-- Ayarlar yazısı Profil olarak değiştirildi -->
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-card class="profile-card">
        <div class="profile-item">
          <span class="label">Ad Soyad:</span>
          <span class="value">{{ userName }}</span>
          <ion-button fill="clear" @click="editName" class="edit-button">Düzenle</ion-button> <!-- Ad Soyad Düzenle butonu sağ ortada -->
        </div>
      </ion-card>

      <ion-card class="profile-card">
        <div class="profile-item">
          <span class="label">Mail:</span>
          <span class="value">{{ userEmail }}</span>
          <ion-button fill="clear" @click="editEmail" class="edit-button">Düzenle</ion-button> <!-- Mail Düzenle butonu sağ ortada -->
        </div>
      </ion-card>

      <!-- Şifre Değiştir Butonu -->
      <ion-button expand="default" @click="sendRequest" class="backend-button">Şifre Değiştir</ion-button>
      
      <!-- Yanıt mesajı -->
      <p v-if="response">Gelen Yanıt: {{ response }}</p>
      <p v-if="error">Hata: {{ error }}</p>

      <!-- Ad Soyad Düzenleme Modalı -->
      <ion-modal :is-open="isNameModalOpen">
        <template #default>
          <ion-header>
            <ion-toolbar>
              <ion-title>Ad Soyad Düzenle</ion-title>
              <ion-buttons slot="end">
                <ion-button @click="closeNameModal">Kapat</ion-button>
              </ion-buttons>
            </ion-toolbar>
          </ion-header>
          <ion-content>
            <ion-item>
              <ion-label position="floating">Yeni Ad Soyad</ion-label>
              <ion-input v-model="newName"></ion-input>
            </ion-item>
            <ion-button expand="full" @click="saveName">Kaydet</ion-button>
          </ion-content>
        </template>
      </ion-modal>

      <!-- Mail Düzenleme Modalı -->
      <ion-modal :is-open="isEmailModalOpen">
        <template #default>
          <ion-header>
            <ion-toolbar>
              <ion-title>Mail Düzenle</ion-title>
              <ion-buttons slot="end">
                <ion-button @click="closeEmailModal">Kapat</ion-button>
              </ion-buttons>
            </ion-toolbar>
          </ion-header>
          <ion-content>
            <ion-item>
              <ion-label position="floating">Yeni Mail</ion-label>
              <ion-input v-model="newEmail" type="email"></ion-input>
            </ion-item>
            <ion-button expand="full" @click="saveEmail">Kaydet</ion-button>
          </ion-content>
        </template>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonButton, IonModal, IonLabel, IonInput, IonItem, IonButtons } from '@ionic/vue';
import axios from 'axios';

// Router instance
const router = useRouter();

// Kullanıcı bilgileri için reactive değişkenler
const userEmail = ref('');
const userName = ref('');
const newEmail = ref(''); // Yeni mail için değişken
const newName = ref(''); // Yeni ad soyad için değişken
const isEmailModalOpen = ref(false); // Mail düzenleme modal'ı durumu
const isNameModalOpen = ref(false); // Ad Soyad düzenleme modal'ı durumu
const response = ref<string | null>(null); // Backend yanıtı
const error = ref<string | null>(null); // Hata mesajı

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

// Ad Soyad düzenleme modal'ını aç
const editName = () => {
  newName.value = userName.value; // Mevcut ad soyadını input'a aktar
  isNameModalOpen.value = true; // Modal'ı aç
};

// E-posta düzenleme modal'ını aç
const editEmail = () => {
  newEmail.value = userEmail.value; // Mevcut e-posta adresini input'a aktar
  isEmailModalOpen.value = true; // Modal'ı aç
};

// Ad Soyad modal'ını kapat
const closeNameModal = () => {
  isNameModalOpen.value = false;
};

// Mail modal'ını kapat
const closeEmailModal = () => {
  isEmailModalOpen.value = false;
};

// Yeni ad soyad kaydetme
const saveName = async () => {
  try {
    // Yeni ad soyadını sakla
    await SecureStoragePlugin.set({ key: 'userName', value: newName.value });
    userName.value = newName.value; // Kullanıcıya yeni ad soyad değerini göster
    isNameModalOpen.value = false; // Modal'ı kapat
  } catch (error) {
    console.error('Error saving name:', error);
  }
};

// Yeni e-posta kaydetme
const saveEmail = async () => {
  try {
    // Yeni e-posta adresini sakla
    await SecureStoragePlugin.set({ key: 'userEmail', value: newEmail.value });
    userEmail.value = newEmail.value; // Kullanıcıya yeni e-posta değerini göster
    isEmailModalOpen.value = false; // Modal'ı kapat
  } catch (error) {
    console.error('Error saving email:', error);
  }
};

// Backend'e request gönderme fonksiyonu
const sendRequest = async () => {
  try {
    // Backend API URL'ini buraya yazın
    const res = await axios.get('https://api.example.com/data');
    response.value = res.data; // Gelen yanıtı response'a ata
  } catch (err) {
    error.value = 'Request sırasında bir hata oluştu'; // Hata mesajı
    console.error(err);
  }
};
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
  font-size: 30px; /* Profil yazısı için boyut 30 */
  font-weight: bold; /* Yazıyı kalın yap */
}

.profile-card {
  margin: 10px;
  padding: 20px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  text-align: left; /* Kart içeriğini sola dayalı yap */
}

.profile-item {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column; /* Butonları alt satıra almak için flex-direction'ı column yapıyoruz */
  align-items: flex-start; /* Etiketleri ve değerleri sola hizala */
  position: relative;
}

.profile-item .label {
  font-size: 16px;
  display: block; /* Yeni satıra geçmesi için */
  font-weight: bold;
  text-align: left; /* Etiketi sola dayalı yap */
}

.profile-item .value {
  font-size: 22px;
  color: rgb(70, 70, 70);
  text-align: left; /* Değeri sola dayalı yap */
}

.edit-button {
  position: absolute;
  top: 50%; /* Butonu dikeyde ortalayalım */
  right: 0; /* Butonu sağa bitişik yap */
  transform: translateY(-50%); /* Dikeyde tam ortalayabilmek için translateY kullanıyoruz */
}

/* Backend butonunun stilini değiştirdik */
.backend-button {
  margin-left: 20px; /* Butonu sola yaslamak için margin ekliyoruz */
  margin-top: 10px;  /* Butona üstten biraz boşluk ekliyoruz */
  width: auto; /* Butonun genişliğini otomatik olarak ayarlıyoruz */
  padding: 8px 16px; /* Butonun iç boşluğunu küçültüyoruz */
}
</style>
