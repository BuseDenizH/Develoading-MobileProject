<template>
  <ion-content>
    <div class="signup-container">
      <!-- üst simge -->
      <img :src="logo" alt="logo" class="logo" />

      <!-- şirket adı -->
      <p class="company-name">Company Name</p>

      <!-- üye ol başlık açıklama -->
      <h1>Üye Ol</h1>
      <p class="welcome-text">Merhaba! Kampanya uygulamasına hoşgeldiniz.</p>

      <!-- email alanı -->
      <ion-item>
        <ion-label position="stacked" color="danger">Email</ion-label>
        <ion-input placeholder="Mail adresinizi giriniz" type="email"></ion-input>
      </ion-item>

      <!-- şifre alanı -->
      <ion-item>
        <ion-label position="stacked" color="danger">Şifre</ion-label>
        <ion-input placeholder="Şifrenizi giriniz" type="password"></ion-input>
      </ion-item>

      <!-- gizlilik ve kullanım şartları -->
      <ion-item lines="none">
        <ion-checkbox slot="start"></ion-checkbox>
        <label>I agree to the <span class="terms">Terms of Services</span> and <span class="privacy">Privacy
            Policy</span>.</label>
      </ion-item>

      <!-- üye ol butonu -->
      <ion-button expand="block" color="danger" @click="register">Üye Ol</ion-button>

      <!-- diğer kayıt seçenekleri -->
      <p class="or">ya da alttaki uygulamalardan biriyle üye ol</p>

      <!-- sosyal medya Butonları -->
      <div class="social-buttons">
        <ion-button color="primary">Facebook</ion-button>
        <ion-button color="dark">X</ion-button>
      </div>

      <!-- Zaten Üye misin -->
      <p class="already-member">Zaten üye misin?
        <router-link to="/login">Giriş Yap</router-link>
      </p>
    </div>
  </ion-content>
</template>

<script setup lang="ts">
import icon from '@/assets/icon.png';
import { useRouter } from 'vue-router';

const logo = icon;

const register = () => {
  // Kayıt işlemi tamamlandığında
  localStorage.setItem('userRegistered', 'true');
  const router = useRouter();
  router.push({ name: 'Login' });
};
</script>

<style scoped>
.signup-container {
  text-align: center;
  padding: 20px;
  max-width: 100%;
  box-sizing: border-box;
  margin: 0 auto;
}

.logo {
  width: 80px;
  margin-bottom: 10px;
}

.company-name {
  font-size: 16px;
  color: gray;
  margin-bottom: 10px;
}

h1 {
  font-size: 24px;
  margin-bottom: 10px;
}

.welcome-text {
  font-size: 14px;
  color: gray;
  margin-bottom: 20px;
}

ion-item {
  margin-bottom: 10px;
}

.terms,
.privacy {
  color: red;
}

ion-button {
  margin-top: 20px;
}

.or {
  margin: 20px 0;
  font-size: 12px;
  color: gray;
}

.social-buttons {
  display: flex;
  justify-content: center;
}

.social-buttons ion-button {
  margin: 0 5px;
}

.already-member {
  margin-top: 10px;
  font-size: 12px;
}
</style>
