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
    </ion-header>

    <ion-content>
      <div class="login-container">
        <form @submit.prevent="onLogin">
          <ion-item>
            <ion-label position="stacked" color="danger">Email</ion-label>
            <ion-input type="email" placeholder="Mail adresinizi giriniz" v-model="email" required></ion-input>
          </ion-item>

          <ion-item>
            <ion-label position="stacked" color="danger">Şifre</ion-label>
            <ion-input type="password" placeholder="Şifrenizi giriniz" v-model="password" required></ion-input>
          </ion-item>

          <ion-button expand="block" type="submit" color="danger" class="login-button">
            Giriş Yap
          </ion-button>
        </form>

        <div class="forgot-password">
          <a href="#" @click.prevent="onForgotPassword">Şifremi Unuttum</a>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router'; // vue-router import edildi
import { arrowBackOutline } from 'ionicons/icons';
import { IonPage, IonHeader, IonToolbar, IonContent, IonIcon, IonItem, IonLabel, IonInput, IonButton } from '@ionic/vue';
// Router kullanımı
const router = useRouter();



// Reactive variables for email and password
const email = ref('admin@admin.com');
const password = ref('admin');

// Login method
const onLogin = () => {
  if (email.value === 'admin@admin.com' && password.value === 'admin') {
    // Giriş bilgileri doğruysa yönlendir
    router.push('/tabs/tab1');
  } else {
    // Yanlış giriş bilgisi
    console.log('Hatalı email veya şifre');
    alert('Yanlış email veya şifre girdiniz');
  }
};

// Forgot password method
const onForgotPassword = () => {
  console.log('Şifremi Unuttum tiklandi');
};

// Navigation method (this would require an actual implementation)
const goBack = () => {
  console.log('Geri git');
  localStorage.removeItem('userRegistered')
  router.push({ name: 'SignUp' });
};
</script>

<style scoped>
.login-container {
  padding: 20px;
}

.back-button {
  color: blue;
}

ion-item {
  --highlight-color-focused: red;
}

.login-button {
  margin-top: 20px;
}

.forgot-password {
  margin-top: 15px;
  text-align: center;
}

.forgot-password a {
  color: red;
  text-decoration: none;
}

.forgot-password a:hover {
  text-decoration: underline;
}
</style>
