<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="name-header">
          <h1>Profil</h1>
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-card class="profile-card">
        <div class="profile-item">
          <span class="label">Ad Soyad:</span>
          <span class="value">{{ userName }}</span>
          <ion-button fill="clear" @click="editName" class="edit-button">Düzenle</ion-button>
        </div>
      </ion-card>

      <ion-card class="profile-card">
        <div class="profile-item">
          <span class="label">Mail:</span>
          <span class="value">{{ userEmail }}</span>
          <ion-button fill="clear" @click="editEmail" class="edit-button">Düzenle</ion-button>
        </div>
      </ion-card>

      <!-- Şifre Değiştir Butonu -->
      <ion-button expand="default" class="backend-button" @click="openPasswordModal">Şifre Değiştir</ion-button>

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

      <!-- Şifre Değiştirme Modalı -->
      <ion-modal :is-open="isPasswordModalOpen">
        <template #default>
          <ion-header>
            <ion-toolbar>
              <ion-title>Şifre Değiştir</ion-title>
              <ion-buttons slot="end">
                <ion-button @click="closePasswordModal">Kapat</ion-button>
              </ion-buttons>
            </ion-toolbar>
          </ion-header>
          <ion-content>
            <ion-item>
              <ion-label position="floating">Eski Şifre</ion-label>
              <ion-input v-model="oldPassword" type="password"></ion-input>
            </ion-item>
            <ion-item>
              <ion-label position="floating">Yeni Şifre</ion-label>
              <ion-input v-model="newPassword" type="password"></ion-input>
            </ion-item>
            <ion-item>
              <ion-label position="floating">Yeni Şifre Tekrar</ion-label>
              <ion-input v-model="confirmPassword" type="password"></ion-input>
            </ion-item>
            <ion-button expand="full" @click="savePassword">Kaydet</ion-button>
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
const newEmail = ref('');
const newName = ref('');
const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');
const isEmailModalOpen = ref(false);
const isNameModalOpen = ref(false);
const isPasswordModalOpen = ref(false);
const response = ref<string | null>(null);
const error = ref<string | null>(null);

// Kullanıcı bilgilerini yükleme
onMounted(async () => {
  try {
    const emailData = await SecureStoragePlugin.get({ key: 'userEmail' });
    const nameData = await SecureStoragePlugin.get({ key: 'userName' });

    userEmail.value = emailData.value || 'admin@admin.com';
    userName.value = nameData.value || 'İsim Soyisim';

    const res = await axios.get(`http://localhost:8082/api/users/${userEmail.value}`);
    const fullName = `${res.data.name} ${res.data.surname}`;

    userName.value = fullName;

    await SecureStoragePlugin.set({ key: 'userName', value: fullName });
  } catch (error) {
    console.error('Error loading user data:', error);
  }
});

// Modal açma ve kapatma işlemleri
const editName = () => {
  newName.value = userName.value;
  isNameModalOpen.value = true;
};

const editEmail = () => {
  newEmail.value = userEmail.value;
  isEmailModalOpen.value = true;
};

const openPasswordModal = () => {
  isPasswordModalOpen.value = true;
};

const closeNameModal = () => {
  isNameModalOpen.value = false;
};

const closeEmailModal = () => {
  isEmailModalOpen.value = false;
};

const closePasswordModal = () => {
  isPasswordModalOpen.value = false;
  oldPassword.value = '';
  newPassword.value = '';
  confirmPassword.value = '';
};

// Kaydetme işlemleri
const saveName = async () => {
  try {
    const res = await axios.put(`http://localhost:8082/api/users/update/${userEmail.value}`, {
      name: newName.value.split(' ')[0],
      surname: newName.value.split(' ')[1] || '',
    });

    const updatedName = res.data.name || '';
    const updatedSurname = res.data.surname || '';

    userName.value = `${updatedName} ${updatedSurname}`;
    response.value = 'Ad ve soyad başarıyla güncellendi.';
    isNameModalOpen.value = false;

    await SecureStoragePlugin.set({
      key: 'userName',
      value: `${updatedName} ${updatedSurname}`,
    });
  } catch (err) {
    error.value = 'Ad ve soyad değiştirme sırasında bir hata oluştu.';
    console.error(err);
  }
};

const saveEmail = async () => {
  try {
    const res = await axios.put(
      `http://localhost:8082/api/users/updatemail/${userEmail.value}`,
      null,
      { params: { newMail: newEmail.value } }
    );

    userEmail.value = res.data.mail;
    isEmailModalOpen.value = false;
    response.value = 'E-posta başarıyla güncellendi.';
  } catch (err) {
    error.value = 'E-posta değiştirme sırasında bir hata oluştu.';
    console.error(err);
  }
};

const savePassword = async () => {
  try {
    if (newPassword.value !== confirmPassword.value) {
      error.value = 'Yeni şifreler uyuşmuyor.';
      return;
    }

    const res = await axios.put(`http://localhost:8082/api/users/updatepassword/${userEmail.value}`, {
      oldPassword: oldPassword.value,
      newPassword: newPassword.value,
    });

    response.value = 'Şifre başarıyla değiştirildi.';
    isPasswordModalOpen.value = false;
  } catch (err) {
    error.value = 'Şifre değiştirme sırasında bir hata oluştu.';
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
  font-size: 30px;
  font-weight: bold;
}

.profile-card {
  margin: 10px;
  padding: 20px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.profile-item {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: relative;
}

.profile-item .label {
  font-size: 16px;
  font-weight: bold;
}

.profile-item .value {
  font-size: 22px;
  color: rgb(70, 70, 70);
}

.edit-button {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
}

.backend-button {
  margin-left: 20px;
  margin-top: 10px;
  width: auto;
  padding: 8px 16px;
}
</style>
