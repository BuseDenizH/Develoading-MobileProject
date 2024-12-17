<template>
  <ion-page>
    <ion-content>
      <ion-grid class="signup-container">
        <!-- Logo -->
        <ion-row class="ion-justify-content-center">
          <ion-col size="auto">
            <ion-img :src="logo" alt="logo" class="logo" />
          </ion-col>
        </ion-row>

        <!-- Company Name -->
        <ion-row class="ion-justify-content-center">
          <ion-col size="auto">
            <ion-text color="medium" class="company-name">campagnify</ion-text>
          </ion-col>
        </ion-row>

        <!-- Sign Up Heading -->
        <ion-row class="ion-justify-content-center">
          <ion-col size="auto">
            <ion-text color="dark" class="signup-title">Üye Ol</ion-text>
          </ion-col>
        </ion-row>

        <ion-row class="ion-justify-content-center">
          <ion-col size="auto">
            <ion-text color="medium" class="welcome-text">Merhaba! Kampanya uygulamasına hoşgeldiniz.</ion-text>
          </ion-col>
        </ion-row>

        <form @submit.prevent="register">
          <!-- Email Field -->
          <ion-item>
            <ion-label position="stacked" color="danger">Email</ion-label>
            <ion-input type="email" placeholder="Mail adresinizi giriniz" v-model="mail" required></ion-input>
          </ion-item>

          <!-- Password Field -->
          <ion-item>
            <ion-label position="stacked" color="danger">Şifre</ion-label>
            <ion-input type="password" placeholder="Şifrenizi giriniz" v-model="password" required></ion-input>
          </ion-item>

          <!-- Confirm Password Field -->
          <ion-item>
            <ion-label position="stacked" color="danger">Şifreyi Tekrar Girin</ion-label>
            <ion-input type="password" placeholder="Şifrenizi tekrar giriniz" v-model="confirmPassword"
              required></ion-input>
          </ion-item>

          <!-- Terms and Privacy Agreement -->
          <ion-item lines="none">
            <ion-checkbox slot="start" v-model="agreeTerms"></ion-checkbox>
            <ion-label>I agree to the <span class="terms">Terms of Services</span> and <span class="privacy">Privacy
                Policy</span>.</ion-label>
          </ion-item>

          <!-- Sign Up Button -->
          <ion-button expand="block" color="danger" :disabled="!agreeTerms" type="submit">Üye Ol</ion-button>
        </form>

        <!-- Other Sign-Up Options -->
        <ion-row class="ion-justify-content-center">
          <ion-col size="auto">
            <ion-text color="medium" class="or-text">ya da alttaki uygulamalardan biriyle üye ol</ion-text>
          </ion-col>
        </ion-row>

        <!-- Social Media Buttons -->
        <ion-row class="social-buttons ion-justify-content-center">
          <ion-button color="primary">Facebook</ion-button>
          <ion-button color="dark">X</ion-button>
        </ion-row>

        <!-- Already a Member -->
        <ion-row class="ion-justify-content-center">
          <ion-col size="auto">
            <ion-text color="dark">
              Zaten üye misin?
              <router-link to="/login">Giriş Yap</router-link>
            </ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import icon from '@/assets/icon.png';
import { IonPage, IonHeader, IonToolbar, IonContent, IonIcon, IonItem, IonLabel, IonInput, IonButton, IonGrid, IonRow, IonCol, IonImg, IonText, IonCheckbox } from '@ionic/vue';

const router = useRouter();
const logo = icon;
const mail = ref('');
const password = ref('');
const confirmPassword = ref(''); // Şifreyi tekrar girmek için yeni bir değişken
const agreeTerms = ref(false);

const register = async () => {
  if (password.value !== confirmPassword.value) {
    alert('Şifreler eşleşmiyor. Lütfen kontrol edin.');
    return;
  }

  if (agreeTerms.value) {
    try {
      debugger
      const response = await fetch('http://localhost:8082/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          mail: mail.value,
          password: password.value,
        }),
      });
      debugger
      if (response.ok) {
        alert('Kayıt başarılı!');
        router.push({ name: 'Login' });
      } else {
        const errorMessage = await response.text();
        console.error(`Error: ${response.status} - ${errorMessage}`);
        alert(`Bu mail hesabı zaten kayıtlı. ${response.status}`);
        return;
      }
    } catch (error) {
      console.error('Kayıt başarısız:', error);
      debugger
      alert('Bir hata meydana geldi.');
    }
  } else {
    alert('Lütfen gizlilik politikası ve kullanım şartlarını kabul ediniz.');
  }
};

</script>

<style scoped>
.signup-container {
  padding: 20px;
}

.logo {
  width: 80px;
  margin-bottom: 10px;
}

.company-name,
.signup-title,
.welcome-text,
.or-text {
  text-align: center;
}

.terms,
.privacy {
  color: red;
}

.social-buttons {
  margin-top: 10px;
}

.back-button {
  color: blue;
}
</style>