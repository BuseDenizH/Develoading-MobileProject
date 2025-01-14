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
            <div class="verify-container">
                <p>{{ email }} adresine gelen kodu girin:</p>
                <ion-item>
                    <ion-input type="text" placeholder="6 haneli kodu girin" v-model="verificationCode"
                        required></ion-input>
                </ion-item>

                <ion-button expand="block" type="submit" color="danger" @click="verifyCode">
                    Kodu Doğrula
                </ion-button>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'; // vue-router import edildi
import { arrowBackOutline } from 'ionicons/icons';
import { IonPage, IonHeader, IonToolbar, IonContent, IonIcon, IonItem, IonLabel, IonInput, IonButton } from '@ionic/vue';

const router = useRouter();
const route = useRoute();

const email = ref('user@example.com'); // Kullanıcı e-posta adresi (örnek olarak)
const verificationCode = ref('');
const backendResetCode = ref('');

onMounted(() => {
    // Query parametrelerinden email ve resetCode'u al
    console.log('Route Query:', route.query); // Burada değerler görünüyor mu?
    email.value = route.query.email as string;
    backendResetCode.value = route.query.code as string;
});

const goBack = () => {
    router.push({ name: 'ForgotPassword' }); // Geri yönlendirme işlemi
};

const verifyCode = async () => {
    // Kod doğrulama işlemi
    if (verificationCode.value === backendResetCode.value) {
        alert('Kod doğrulandı!');
        router.push({
            name: 'ResetPassword',
            query: { email: email.value }
        });
    } else {
        alert('Yanlış giriş!');
        console.log(email.value);
        console.log(backendResetCode.value);
    }
};
</script>

<style scoped>
.verify-container {
    padding: 20px;
}

.back-button {
    color: blue;
}

ion-item {
    --highlight-color-focused: red;
}

.verified-button {
    margin-top: 20px;
}
</style>