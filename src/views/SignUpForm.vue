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
            <ion-label>
              I agree to the 
              <span class="terms" @click="openTermsModal">Terms of Services</span> and 
              <span class="privacy" @click="openPrivacyModal">Privacy Policy</span>.
            </ion-label>
          </ion-item>

          <!-- Sign Up Button -->
          <ion-button expand="block" color="danger" :disabled="!agreeTerms" type="submit">Üye Ol</ion-button>
        </form>

        <!-- Already a Member -->
        <ion-row class="ion-justify-content-center">
          <ion-col size="auto" class="login-container">
            <ion-text color="dark" class="login-text">
              Zaten üye misin?
            </ion-text>
            <ion-button fill="clear" @click="goToLogin" class="login-link">
              Giriş Yap
            </ion-button>
          </ion-col>
        </ion-row>
      </ion-grid>

      <!-- Terms of Services Modal -->
      <ion-modal :is-open="isTermsModalOpen" @didDismiss="closeTermsModal">
        <ion-header>
          <ion-toolbar>
            <ion-title>Terms of Services</ion-title>
            <ion-buttons slot="end">
              <ion-button @click="closeTermsModal">Kapat</ion-button>
            </ion-buttons>
          </ion-toolbar>
        </ion-header>
        <ion-content>
           <p>Bu uygulamayı kullanarak, yalnızca yasal ve etik kurallara uygun davranacağınızı kabul edersiniz. Hesap güvenliğinizden siz sorumlusunuz. Uygulama, önceden haber vermeksizin değiştirilebilir veya sonlandırılabilir.</p>
        </ion-content>
      </ion-modal>

      <!-- Privacy Policy Modal -->
      <ion-modal :is-open="isPrivacyModalOpen" @didDismiss="closePrivacyModal">
        <ion-header>
          <ion-toolbar>
            <ion-title>Privacy Policy</ion-title>
            <ion-buttons slot="end">
              <ion-button @click="closePrivacyModal">Kapat</ion-button>
            </ion-buttons>
          </ion-toolbar>
        </ion-header>
        <ion-content>
          <p>Bu uygulama, kişisel verilerinizi koruma altına almayı taahhüt eder. Bilgileriniz yalnızca kayıt işlemleri ve uygulama özelliklerini sağlamak amacıyla kullanılır. Üçüncü taraflarla paylaşılmaz. </p>
        </ion-content>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import icon from '@/assets/icon.png';
import { 
  IonPage, IonHeader, IonToolbar, IonContent, IonImg, IonText, IonCheckbox, IonModal, IonButton, 
  IonLabel, IonInput, IonItem, IonGrid, IonRow, IonCol, IonButtons 
} from '@ionic/vue';

const router = useRouter();
const logo = icon;
const mail = ref('');
const password = ref('');
const confirmPassword = ref('');
const agreeTerms = ref(false);

// Modals state
const isTermsModalOpen = ref(false);
const isPrivacyModalOpen = ref(false);

// Functions for modals
const openTermsModal = () => {
  isTermsModalOpen.value = true;
};

const closeTermsModal = () => {
  isTermsModalOpen.value = false;
};

const openPrivacyModal = () => {
  isPrivacyModalOpen.value = true;
};

const closePrivacyModal = () => {
  isPrivacyModalOpen.value = false;
};

// Registration logic
const register = async () => {
  if (password.value !== confirmPassword.value) {
    alert('Şifreler eşleşmiyor. Lütfen kontrol edin.');
    return;
  }

  if (agreeTerms.value) {
    try {
      const response = await fetch('http://18.153.153.139:8082/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          mail: mail.value,
          password: password.value,
        }),
      });

      if (response.ok) {
        alert('Kayıt başarılı!');
        router.push({ name: 'Login' });
      } else {
        const errorMessage = await response.text();
        console.error(`Error: ${response.status} - ${errorMessage}`);
        alert(`Bu mail hesabı zaten kayıtlı.`);
        return;
      }
    } catch (error) {
      console.error('Kayıt başarısız:', error);
      alert('Bir hata meydana geldi.');
    }
  } else {
    alert('Lütfen gizlilik politikası ve kullanım şartlarını kabul ediniz.');
  }
};

// Navigate to Login page
const goToLogin = () => {
  router.push('/login');
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
  text-decoration: underline;
  cursor: pointer;
}

.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}

.login-text {
  display: inline-block;
}

.login-link {
  --color: var(--ion-color-primary);
  font-weight: bold;
  text-transform: none;
  margin: 0;
  padding: 0;
  height: auto;
  --padding-start: 4px;
  --padding-end: 4px;
}
</style>
