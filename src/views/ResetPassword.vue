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
            </ion-toolbar>
        </ion-header>

        <ion-content>
            <div class="reset-container">
                <ion-item>
                    <ion-label position="stacked" color="danger">Yeni Şifre</ion-label>
                    <ion-input type="password" placeholder="Yeni şifre giriniz" v-model="newPassword"
                        required></ion-input>
                </ion-item>

                <ion-item>
                    <ion-label position="stacked" color="danger">Yeni Şifre Tekrar</ion-label>
                    <ion-input type="password" placeholder="Yeni şifreyi tekrar giriniz" v-model="confirmNewPassword"
                        required></ion-input>
                </ion-item>

                <ion-button expand="block" type="submit" color="danger" @click="resetPassword">
                    Şifreyi Yenile
                </ion-button>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router'; // vue-router import edildi
import { arrowBackOutline } from 'ionicons/icons';
import { IonPage, IonHeader, IonToolbar, IonContent, IonIcon, IonItem, IonLabel, IonInput, IonButton } from '@ionic/vue';

const router = useRouter();
const newPassword = ref('');
const confirmNewPassword = ref('');

const goBack = () => {
    router.push({ name: 'VerifyCode' }); // Geri yönlendirme işlemi
};

const resetPassword = () => {
    if (newPassword.value === confirmNewPassword.value) {
        alert('Şifre başarıyla değiştirildi.');
        router.push('/login'); // Şifre başarıyla değiştirildiğinde kullanıcıyı giriş sayfasına yönlendir
    } else {
        alert('Yeni şifreler eşleşmiyor.');
    }
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