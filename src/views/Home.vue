<template>
  <ion-page>

    <ion-header>
      <ion-toolbar>
        <ion-title>Anasayfa</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">

        <ion-toolbar>
          <ion-title class="title">Son Kampanyalar</ion-title>
          <hr class="line">
        </ion-toolbar>
      
      <div v-for="(item, index) in itemsSonKampanyalar" :key="index" class="container">
        <div class="images-container">
          <img :src="item.image" :alt="item.alt">
          <div class="click-icons">
            <a href="tabs/tab4"><ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp"/></a>
            <a href="tabs/tab4"><ion-icon id="bookmark" aria-hidden="true" :icon="bookmarkSharp"/></a>
            <a href="tabs/tab4"><ion-icon id="heart" aria-hidden="true" :icon="heart"/></a>
          </div>
        </div>
        <router-link :to="{ name: 'DetailsPopPage', params: { type: 'kampanyalar', id: index } }" class="card-link">{{ item.text }}</router-link>
        <hr class="inline">
        <div class="under-container">
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="calendarClearOutline"/>  {{item.dates.start}}</p>
            <p><ion-icon aria-hidden="true" :icon="hourglassOutline"/>  {{item.dates.end}}</p>
          </div>
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="cardOutline"/>  {{item.info.type}}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline"/>  {{item.info.category}}</p>
          </div>
        </div>
      </div>

        <ion-toolbar>
          <ion-title class="title">En Beğenilenler</ion-title>
          <hr class="line">
        </ion-toolbar>

      <div v-for="(item, index) in itemsEnBegenilenler" :key="index" class="container">
        <div class="images-container">
          <img :src="item.image" :alt="item.alt">
          <div class="click-icons">
            <a href="tabs/tab4"><ion-icon id="share" aria-hidden="true" :icon="shareSocialSharp"/></a>
            <a href="tabs/tab4"><ion-icon id="bookmark" aria-hidden="true" :icon="bookmarkSharp"/></a>
            <a href="tabs/tab4"><ion-icon id="heart" aria-hidden="true" :icon="heart"/></a>
          </div>
        </div>
        <router-link :to="{ name: 'DetailsPopPage', params: { type: 'begenilenler', id: index } }" class="card-link">{{ item.text }}</router-link>
        <hr class="inline">
        <div class="under-container">
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="calendarClearOutline"/>  {{item.dates.start}}</p>
            <p><ion-icon aria-hidden="true" :icon="hourglassOutline"/>  {{item.dates.end}}</p>
          </div>
          <div class="inner-info">
            <p><ion-icon aria-hidden="true" :icon="cardOutline"/>  {{item.info.type}}</p>
            <p><ion-icon aria-hidden="true" :icon="storefrontOutline"/>  {{item.info.category}}</p>
          </div>
        </div>
      </div>
      
    </ion-content>

  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonFab, IonFabButton, IonToolbar, IonTitle, IonContent, IonIcon, IonGrid, IonRow, IonCol, IonImg, } from '@ionic/vue';
import { calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline, shareSocialSharp, bookmarkSharp, heart, shareSocial } from 'ionicons/icons';

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

const allCompanies = [...itemsSonKampanyalar, ...itemsEnBegenilenler];

</script>

<style>
.title {
  padding: 20px 16px 5px 16px;
  text-align: left;
  color: rgb(248, 79, 0);
}

.line {
  background-color: rgb(248, 79, 0);
  margin-left: 16px;
  margin-right: 16px;
  height: 2px;
}

.inline {
  background-color: gray;
  margin-left: 16px;
  margin-right: 16px;
  height: 2px;
}

.container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  text-align: center;
  padding-top: 0px;
  margin-top: 16px;
  margin-left: 16px;
  margin-right: 16px;
  border-radius: 10px;
  border-style: groove;
  border-color: black;
  border-width: 2px;
  /*background-color: aqua;*/
}

.container img {
  width: 100%;
  height: auto;
  /*border-radius: 10px;*/
}

.images-container {
  position: relative;
  display: inline;
  width: 100%;
}

.click-icons {
  position: absolute;
  top: 0;
  right: 0;
  margin-right: 10px;
  font-size: 24px;
}

.click-icons a {
  text-decoration: none;
  color: rgb(50, 50, 50);
}

#share {
  margin-right: 10px;
  margin-top: 10px;
}

#bookmark {
  margin-right: 10px;
  margin-top: 10px;
}

#heart {
  margin-right: 10px;
  margin-top: 10px;
}

.container p {
  font-size: 16px;
  line-height: 22px;
  color: black;
  /*background-color: blueviolet;*/
  margin: 0 10px;
  width: 100%;
  padding: 10px 16px;
  font-weight: bold;
}

.card-link {
  font-size: 16px;
  line-height: 22px;
  color: black;
  /*background-color: blueviolet;*/
  margin: 0 10px;
  width: 100%;
  padding: 10px 16px;
  font-weight: bold;
  text-decoration: none;
}

.under-container {
  display: flex;
  width: 100%;
  margin-bottom: 16px;
}

.inner-info {
  /*background-color: rgb(255, 99, 203);*/
  width: 100%;
}

.inner-info p {
  /*background-color: yellow;*/
  width: max-content;
  color: black;
  font-weight: normal;
  padding: 0%;
  margin-top: 5px;
}

ion-icon {
  --ionicon-stroke-width: 40px;
}

</style>
