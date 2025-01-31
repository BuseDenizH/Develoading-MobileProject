<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-button router-link="/" class="back-button">
            <ion-icon :icon="arrowBackOutline" />
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

          <ion-button
              expand="block"
              type="submit"
              color="danger"
              class="login-button"
              @blur="clearFocusBeforeNavigation">
            Giriş Yap
          </ion-button>
        </form>

        <div class="forgot-password">
          <a @click.prevent="onForgotPassword">Şifremi Unuttum</a>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter, onBeforeRouteLeave } from 'vue-router';
import { arrowBackOutline } from 'ionicons/icons';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonContent,
  IonIcon,
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
  IonButtons,
  onIonViewWillLeave
} from '@ionic/vue';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';

// Router kullanımı
const router = useRouter();

// Reactive variables for email and password
const email = ref('admin@admin.com');
const password = ref('admin');

// Focus yönetimi için hooks
onBeforeRouteLeave((to, from, next) => {
  const activeElement = document.activeElement as HTMLElement;
  if (activeElement) {
    activeElement.blur();
  }
  next();
});

onIonViewWillLeave(() => {
  // Tüm focuslanabilir elementlerin focusunu kaldır
  const focusableElements = document.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');
  focusableElements.forEach(element => {
    if (element instanceof HTMLElement) {
      element.blur();
    }
  });
});

// Login işlemi öncesi focus temizleme
const clearFocusBeforeNavigation = () => {
  const activeElement = document.activeElement as HTMLElement;
  if (activeElement) {
    activeElement.blur();
  }
};

const onLogin = async () => {
  try {
    const response = await fetch('http://18.153.153.139:8082/api/users/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        mail: email.value,
        password: password.value,
      }),
    });

    if (response.ok) {
      const user = await response.json();

      // Kullanıcı bilgilerini güvenli bir şekilde sakla
      await SecureStoragePlugin.set({ key: 'userEmail', value: user.mail });
      await SecureStoragePlugin.set({ key: 'userName', value: user.name });
      await SecureStoragePlugin.set({ key: 'userId', value: user.id.toString() });

      console.log('Login successful:', user);
      alert(`Hoş geldiniz!`);

      // Yönlendirmeden önce focus'u temizle
      clearFocusBeforeNavigation();
      router.push('/tabs/tab1');
    } else {
      alert('Email veya şifre hatalı!');
    }
  } catch (error) {
    console.error('Error during login:', error);
    alert('Bir hata oluştu. Lütfen tekrar deneyiniz.');
  }
};

const saveUserId = async (id: string) => {
  await SecureStoragePlugin.set({ key: 'userId', value: id });
  console.log('User ID stored securely.');
};

// Forgot password method
const onForgotPassword = () => {
  clearFocusBeforeNavigation(); // Focus'u temizle
  console.log('Şifremi Unuttum tiklandi');
  router.push({ name: 'ForgotPassword' });
};

// Navigation method
const goBack = () => {
  clearFocusBeforeNavigation(); // Focus'u temizle
  console.log('Geri git');
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