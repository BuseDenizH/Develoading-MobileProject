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
      </ion-toolbar>
      <ion-toolbar>
        <ion-title class="name-header">
          <h1>Bize Ulaşın</h1>
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div class="contact-form-container">
        <div class="contact-form">
          <ion-item>
            <ion-textarea v-model="note" placeholder="Notunuzu buraya yazın..." rows="6"></ion-textarea>
          </ion-item>

          <ion-item>
            <ion-input v-model="email" type="email" placeholder="E-posta adresinizi girin"></ion-input>
          </ion-item>

          <ion-button expand="full" @click="sendMessage">Gönder</ion-button>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonLabel, IonButton, IonItem, IonInput, IonTextarea } from '@ionic/vue';
import { arrowBackOutline } from 'ionicons/icons';

// Router instance
const router = useRouter();

// Form verileri için reactive değişkenler
const note = ref('');
const email = ref('');

// Geri gitme fonksiyonu
const goBack = () => {
  router.back();
};

// Mesaj gönderme fonksiyonu
const sendMessage = () => {
  if (note.value && email.value) {
    console.log('Mesaj gönderildi:', note.value, email.value);
    // Burada mesaji göndermek için gerekli işlemler yapılabilir
  } else {
    console.error('Lütfen tüm alanları doldurun');
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
  margin-bottom: 0;
  font-size: 30px;
  font-weight: bold;
}

.contact-form-container {
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.contact-form {
  width: 100%;
  max-width: 600px;
  padding: 20px;
  border-radius: 10px;
  background-color: #f4f4f4;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.1);
}

.contact-form ion-item {
  margin-bottom: 20px;
}

.back-button {
  color: #0066ff; /* Mavi renk */
  font-size: 14px;
  margin-left: 5px;
}

.back-button ion-icon {
  margin-right: 5px;
}
</style>
