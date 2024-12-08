<template>
    <ion-page>
      
      <ion-header>
        <ion-toolbar>
          <ion-title class="header"><a href="tabs/tab1"><ion-icon :icon="arrowBack"/>Geri</a></ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-content :fullscreen="true">

        <div v-if="item">
            <div class="details-image">
                <img :src="item.image" :alt="item.alt" />
            </div>
            <h2 class="details-title">{{ item.text }}</h2>
            <div class="details-info">
                <p><ion-icon :icon="calendarClear" /> {{ item.dates.start }}</p> 
                <p><ion-icon :icon="hourglass" /> {{ item.dates.end }}</p>
                <p><ion-icon :icon="bookmark" />122 kişi kullandı</p>
                <p><ion-icon :icon="heart" />100 kişi beğendi</p>
                <p><ion-icon :icon="shareSocial" />Paylaş</p>
            </div>
            <div class="details-description">
                <h3>KATILMAK İÇİN</h3>
                <p>Kampanyadan faydalanmak ve data detaylı bilgi edinmek için "Detayı Görüntüle" butonuna tıklayarak websiteye göz at.</p>
                <h3>NASIL KAZANIRIM?</h3>
                <p>Kampanyanın şartlarını sağladığından emin ol. Kampanyaya katılım sağladıktan sonra "Kullandım" butonuna tıklayarak yerini almayı unutma!</p>
            </div>
            <div class="details-extra">
                <p> {{ item.info.type }}</p>
                <p> {{ item.info.category }}</p>
            </div>
            <div class="details-actions">
                <ion-button expand="block" color="orange" @click="goToLink(item.alt)"><p>Detayı Görüntüle</p></ion-button>
                <ion-button expand="block" color="orange"><p>Kullandım</p></ion-button>
            </div>
        </div>
        <div v-else>
            <p>Detay bulunamadı.</p>
        </div>

      </ion-content>

    </ion-page>
</template>
  
<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon } from '@ionic/vue';
import { calendarClear, bookmark, heart, shareSocial, arrowBack, hourglass } from 'ionicons/icons';

import { computed } from 'vue';
import { useRoute } from 'vue-router';

const itemsSonKampanyalar = [
  {
    image: "https://www.bonus.com.tr/assets/img/main_slider/qrb_300924.jpg",
    alt: "https://www.bonus.com.tr/",
    text: "Mobil temazsız veya QR ile ödemelerinize 300TL'ye varan bonus!",
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
    text: "Axess ile OPET'te 4 kez 800TL'ye 250TL'ye varan Chip-Para! ",
    dates: {
      start: "02.02.2024",
      end: "11.11.2024"
    },
    info: {
      type: "Axess",
      category: "Akaryakıt",
      name: "Axess Akaryakıt Kampanyası"
    }
  },
  {
    image: "https://hayatpay.com.tr/_next/image?url=http%3A%2F%2Fhp-fs%3A9000%2Fhp-images%2Fespressolab_olculu_%25C3%2587al%25C4%25B1%25C5%259Fma%2520Y%25C3%25BCzeyi%25201%2520kopya%25205%2520copy%25204.jpg&w=3840&q=75",
    alt: "https://hayatpay.com.tr/kampanya/81",
    text: "Hayat Pay ile Espressolab'den bedava kahve almak için hemen Hayat Pay uygulamasını indir, kahve kodunu kap!",
    dates: {
      start: "18.09.2024",
      end: "31.10.2024"
    },
    info: {
      type: "Tümü",
      category: "Yeme/İçme",
      name: "Hayat Pay Kahve Kampanyası"
    }
  }
]

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
]

const route = useRoute();
const item = computed(() => {
    const type = route.params.type;
    const id = Number(route.params.id);
    if (type === 'kampanyalar') {
        return itemsSonKampanyalar[id];
    } else if (type === 'begenilenler') {
        return itemsEnBegenilenler[id];
    }
    return null;
});

function goToLink(url) {
    if (url) {
        window.location.href = url;
    } else {
        alert("URL not found!");
    }
}

</script>

<style>

.header a {
    color: blue;
    font-size: 16px;
    text-decoration: none;
}

.details-title {
    font-weight: bold;
    margin: 10px;
    margin-top: 20px;
    text-align: center
}

.details-image {
    width: 100%;
    height: auto;
    display: flex;
    flex-direction: column;
}

.details-info ion-icon {
    width: 100%;
}

.details-info {
    margin-left: 20px;
    margin-right: 20px; 
    margin-top: 20px;
    display: flex;
}

.details-info p {
    font-size: 16px;
    margin: 10px;
    text-align: center;
    width: 100%;
}

.details-extra {
    display: flex;
    margin-left: 10px;
    margin-bottom: 15px;
}

.details-extra p {
    background-color: rgb(255, 188, 62);
    width:fit-content;
    margin: 10px;
    padding: 5px 10px;
    border-radius: 10px;
}

.details-description h3 {
    font-weight: bold;
}

.details-description {
    margin-right: 10px;
    margin-left: 20px;
    font-size: 16px;
}

.details-actions ion-button {
    background-color: rgb(248, 79, 0);
    border-radius: 5px;
    margin: 10px;
    text-transform: none;
}

.details-actions p {
    padding: 0px;
    margin: 10px;
    font-size: 20px;
    font-weight: bold;
} 

</style>
  