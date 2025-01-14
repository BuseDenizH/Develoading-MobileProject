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
                    Şifreyi Güncelle
                </ion-button>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router'; // vue-router import edildi
import { arrowBackOutline } from 'ionicons/icons';
import { IonPage, IonHeader, IonToolbar, IonContent, IonIcon, IonItem, IonLabel, IonInput, IonButton } from '@ionic/vue';
import axios from 'axios';
import { SecureStoragePlugin } from 'capacitor-secure-storage-plugin';

const router = useRouter();
const route = useRoute();
const newPassword = ref('');
const confirmNewPassword = ref('');

const goBack = () => {
    router.push({ name: 'VerifyCode' });
};

const resetPassword = async () => {
    try {
        console.log(newPassword.value);
        console.log(confirmNewPassword.value);
        if (newPassword.value !== confirmNewPassword.value) {
            alert('Yeni şifreler eşleşmiyor.');
            return;
        }

        const userEmail = ref('');
        userEmail.value = route.query.email as string;
        console.log(userEmail.value);
        // API çağrısı yap
        const response = await axios.put(`http://18.153.153.139:8082/api/users/modifypassword/${userEmail.value}`, {
            newPassword: newPassword.value
        },
            {
                headers: {
                    'Content-Type': 'application/json',
                }
            });

        alert('Şifre başarıyla güncellendi.');
        router.push('/login'); // Başarılı işlem sonrası giriş sayfasına yönlendirme
    } catch (err) {
        alert('Şifre değiştirme sırasında bir hata oluştu.');
        console.error(err);
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
