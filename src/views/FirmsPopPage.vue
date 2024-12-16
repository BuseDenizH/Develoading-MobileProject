<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <!-- Back butonu düzenlendi -->
          <ion-button @click="goBack" class="back-button">
            <ion-icon :icon="arrowBackOutline" aria-hidden="true" />
            <ion-label>Geri</ion-label>
          </ion-button>
        </ion-buttons>
        <ion-title>{{ category }} Şirketleri</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div v-if="filteredCompanies.length > 0">
        <div v-for="(company, index) in filteredCompanies" :key="index" class="firm-card">
          <img :src="company.image" :alt="company.alt" />
          <h3>{{ company.info.name }}</h3>
          <ion-button expand="block" color="orange" class="go-link-button" @click="goToLink(company.alt)">
            Siteye Git
          </ion-button>
        </div>
      </div>
      <div v-else>
        <p>Bu kategoriye ait şirket bulunamadı.</p>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup>
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonButton } from '@ionic/vue';
import { arrowBackOutline } from 'ionicons/icons';
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const category = route.params.category;

const itemsSonKampanyalar = [
  {
    image: "https://www.bonus.com.tr/assets/img/main_slider/qrb_300924.jpg",
    alt: "https://www.bonus.com.tr/",
    text: "Mobil temassız veya QR ile ödemelerinize 300TL'ye varan bonus!",
    dates: {
      start: "01.01.2024",
      end: "12.12.2024"
    },
    info: {
      type: "Bonus",
      category: "Giyim/Kozmetik",
      name: "Bonus Kampanyası"
    }
  },
  {
    image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgBJKXghhZtAlmcQ3p62QL23zCvrt7sXLcPA&s",
    alt: "https://www.axess.com.tr/axess/kampanyadetay/8/20152/axess-ile-opette-4-kez-800-tlye-250-tlye-varan-chip-para",
    text: "Axess ile OPET'te 4 kez 800TL'ye 250TL'ye varan Chip-Para!",
    dates: {
      start: "02.02.2024",
      end: "11.11.2024"
    },
    info: {
      type: "Axess",
      category: "Akaryakıt",
      name: "Axess Akaryakıt Kampanyası"
    }
  }
];

const itemsEnBegenilenler = [
  {
    image: "https://fintechtime.com/wp-content/uploads/2018/11/Vakifbank-yeni-kart-2018.jpg",
    alt: "https://milplus.com.tr/kampanyalar/milplus-platinum-ile-espressolabde-kahve-hediye",
    text: "Visa Private Kredi Kartı ile Hediye Kahve Kazan!",
    dates: {
      start: "01.10.2024",
      end: "31.10.2024"
    },
    info: {
      type: "Vakıfbank",
      category: "Yeme/İçme",
      name: "Vakıfbank Kahve Kampanyası"
    }
  },
  {
    image: "https://www.maximum.com.tr/contentmanagement/PublishingImages/kampanyagorselleri/bireysel/Elektronik_580x460.jpg",
    alt: "https://www.maximum.com.tr/kampanyalar/elektronik-parekende-sektoru-indirim-firsati",
    text: "Elektronik Perakende Sektörü İndirim Fırsatı!",
    dates: {
      start: "02.02.2024",
      end: "11.11.2024"
    },
    info: {
      type: "Maximum",
      category: "Elektronik",
      name: "Maximum Elektronik Kampanyası"
    }
  }
];

const allCompanies = [...itemsSonKampanyalar, ...itemsEnBegenilenler];

const filteredCompanies = computed(() =>
    allCompanies.filter((company) => company.info.category === category)
);

function goToLink(url) {
  if (url) {
    window.location.href = url;
  } else {
    alert('Bağlantı bulunamadı!');
  }
}

function goBack() {
  router.push('/tabs/tab2');
}
</script>

<style scoped>
.back-button {
  display: flex;
  align-items: center;
  color: blue;
  margin-left: 10px;
}

.firm-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.firm-card img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.firm-card h3 {
  font-size: 1.2em;
  text-align: center;
  margin-top: 10px;
}

.go-link-button {
  background-color: rgb(248, 79, 0);
  border-radius: 5px;
  margin: 10px;
  text-transform: none;
  padding: 0px;
  font-size: 20px;
}
</style>
