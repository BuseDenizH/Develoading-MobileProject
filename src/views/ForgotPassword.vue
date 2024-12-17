<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button @click="goBack" class="back-button">
                        <ion-icon :icon="arrowBackOutline" />
                        <ion-label>Geri</ion-label>
                    </ion-button>
                </ion-buttons>
                <ion-title>Şifre Yenileme</ion-title>
            </ion-toolbar>
        </ion-header>

        <ion-content>
            <div class="reset-container">
                <ion-item>
                    <ion-label position="stacked" color="danger">Email</ion-label>
                    <ion-input type="email" placeholder="Mail adresinizi giriniz" v-model="email" required></ion-input>
                </ion-item>
                <ion-button expand="block" @click="sendResetLink" color="danger" class="reset-button">
                    Kod Gönder
                </ion-button>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
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
} from '@ionic/vue';

// Reactive variable for email
const email = ref('');

const router = useRouter();

// Function to send the reset link
const sendResetLink = () => {
    console.log('Şifre yenileme bağlantısı gönderildi:', email.value);
    // Here you would normally call an API to send the reset link
    alert('Şifre yenileme bağlantısı gönderildi.');
    router.push('/verify-code'); // Redirect back to the login page after sending the link
};

// Navigation back to login page
const goBack = () => {
    console.log('Geri git');
    router.push({ name: 'Login' });
};
</script>

<style scoped>
.reset-container {
    padding: 20px;
}

.back-button {
    color: blue;
}

ion-item {
    --highlight-color-focused: red;
}

.reset-button {
    margin-top: 20px;
}
</style>