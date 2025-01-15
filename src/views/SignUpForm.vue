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
            <ion-input 
              type="email" 
              placeholder="Mail adresinizi giriniz" 
              v-model="mail" 
              required>
            </ion-input>
          </ion-item>
          <ion-text color="danger" v-if="emailError" class="error-message">
            {{ emailError }}
          </ion-text>

          <!-- Password Field -->
          <ion-item>
            <ion-label position="stacked" color="danger">Şifre</ion-label>
            <ion-input 
              type="password" 
              placeholder="Şifrenizi giriniz" 
              v-model="password" 
              required>
            </ion-input>
          </ion-item>

          <!-- Password Requirements -->
          <ion-list class="password-requirements" v-if="password">
            <ion-item lines="none">
              <ion-icon 
                :color="!passwordErrors.length ? 'success' : 'danger'" 
                :name="!passwordErrors.length ? 'checkmark-circle' : 'close-circle'"
                slot="start">
              </ion-icon>
              <ion-label :color="!passwordErrors.length ? 'success' : 'danger'">
                En az 8 karakter
              </ion-label>
            </ion-item>
            
            <ion-item lines="none">
              <ion-icon 
                :color="!passwordErrors.uppercase ? 'success' : 'danger'" 
                :name="!passwordErrors.uppercase ? 'checkmark-circle' : 'close-circle'"
                slot="start">
              </ion-icon>
              <ion-label :color="!passwordErrors.uppercase ? 'success' : 'danger'">
                En az bir büyük harf
              </ion-label>
            </ion-item>

            <ion-item lines="none">
              <ion-icon 
                :color="!passwordErrors.lowercase ? 'success' : 'danger'" 
                :name="!passwordErrors.lowercase ? 'checkmark-circle' : 'close-circle'"
                slot="start">
              </ion-icon>
              <ion-label :color="!passwordErrors.lowercase ? 'success' : 'danger'">
                En az bir küçük harf
              </ion-label>
            </ion-item>

            <ion-item lines="none">
              <ion-icon 
                :color="!passwordErrors.number ? 'success' : 'danger'" 
                :name="!passwordErrors.number ? 'checkmark-circle' : 'close-circle'"
                slot="start">
              </ion-icon>
              <ion-label :color="!passwordErrors.number ? 'success' : 'danger'">
                En az bir rakam
              </ion-label>
            </ion-item>

            <ion-item lines="none">
              <ion-icon 
                :color="!passwordErrors.special ? 'success' : 'danger'" 
                :name="!passwordErrors.special ? 'checkmark-circle' : 'close-circle'"
                slot="start">
              </ion-icon>
              <ion-label :color="!passwordErrors.special ? 'success' : 'danger'">
                En az bir özel karakter (@$!%*?&)
              </ion-label>
            </ion-item>
          </ion-list>

          <!-- Password Strength Indicator -->
          <ion-item lines="none" v-if="password">
            <ion-label>Şifre Gücü:</ion-label>
            <ion-progress-bar 
              :color="passwordStrengthColor" 
              :value="passwordStrength">
            </ion-progress-bar>
          </ion-item>

          <!-- Confirm Password Field -->
          <ion-item>
            <ion-label position="stacked" color="danger">Şifreyi Tekrar Girin</ion-label>
            <ion-input 
              type="password" 
              placeholder="Şifrenizi tekrar giriniz" 
              v-model="confirmPassword"
              required>
            </ion-input>
          </ion-item>
          <ion-text color="danger" v-if="passwordMatchError" class="error-message">
            Şifreler eşleşmiyor
          </ion-text>

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
          <p>Bu uygulama, kişisel verilerinizi koruma altına almayı taahhüt eder. Bilgileriniz yalnızca kayıt işlemleri ve uygulama özelliklerini sağlamak amacıyla kullanılır. Üçüncü taraflarla paylaşılmaz.</p>
        </ion-content>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import icon from '@/assets/icon.png';
import { 
  IonPage, IonHeader, IonToolbar, IonContent, IonImg, IonText, IonCheckbox, 
  IonModal, IonButton, IonLabel, IonInput, IonItem, IonGrid, IonRow, IonCol, 
  IonButtons, IonIcon, IonProgressBar, IonList 
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

// Validation states
const passwordErrors = ref({
  length: false,
  uppercase: false,
  lowercase: false,
  number: false,
  special: false
});

const emailError = ref('');
const passwordMatchError = ref(false);

// Watch for changes
watch(mail, (newValue) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  emailError.value = emailRegex.test(newValue) ? '' : 'Geçerli bir email adresi giriniz';
});

watch(password, (newValue) => {
  passwordErrors.value = {
    length: newValue.length < 8,
    uppercase: !/[A-Z]/.test(newValue),
    lowercase: !/[a-z]/.test(newValue),
    number: !/[0-9]/.test(newValue),
    special: !/[@$!%*?&]/.test(newValue)
  };
});

watch(confirmPassword, (newValue) => {
  passwordMatchError.value = newValue !== password.value;
});

// Password strength computation
const passwordStrength = computed(() => {
  let strength = 0;
  if (!passwordErrors.value.length) strength += 0.2;
  if (!passwordErrors.value.uppercase) strength += 0.2;
  if (!passwordErrors.value.lowercase) strength += 0.2;
  if (!passwordErrors.value.number) strength += 0.2;
  if (!passwordErrors.value.special) strength += 0.2;
  return strength;
});

const passwordStrengthColor = computed(() => {
  if (passwordStrength.value <= 0.4) return 'danger';
  if (passwordStrength.value <= 0.8) return 'warning';
  return 'success';
});

// Modal functions
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
  // Check for any validation errors
  if (emailError.value || 
      Object.values(passwordErrors.value).some(error => error) || 
      passwordMatchError.value) {
    alert('Lütfen tüm alanları doğru şekilde doldurunuz.');
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

.error-message {
  font-size: 0.8em;
  margin: 5px 0 0 16px;
  display: block;
}

.password-requirements {
  margin: 10px 0;
  font-size: 0.9em;
}

.password-requirements ion-item {
  --min-height: 30px;
}

.password-requirements ion-icon {
  font-size: 1.2em;
  margin-right: 8px;
}
</style>