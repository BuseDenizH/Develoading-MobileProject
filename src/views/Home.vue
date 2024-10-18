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
        <img :src="item.image" :alt="item.alt">
        <p>{{ item.text }}</p>
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
        <img :src="item.image" :alt="item.alt">
        <p>{{ item.text }}</p>
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
import { calendarClearOutline, hourglassOutline, cardOutline, storefrontOutline } from 'ionicons/icons';

const itemsSonKampanyalar = [
  {
    image: "https://www.bonus.com.tr/assets/img/main_slider/qrb_300924.jpg",
    alt: "bonus",
    text: "Mobil temazsız veya QR ile ödemelerinize 300TL'ye varan bonus!",
    dates: {
      start: "01.01.2024",
      end: "12.12.2024"
    },
    info: {
      type: "Bonus",
      category: "Genel"
    }
  },
  {
    image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgBJKXghhZtAlmcQ3p62QL23zCvrt7sXLcPA&s",
    alt: "axess",
    text: "Axess ile OPET'te 4 kez 800TL'ye 250TL'ye varan Chip-Para! ",
    dates: {
      start: "02.02.2024",
      end: "11.11.2024"
    },
    info: {
      type: "Axess",
      category: "Opet"
    }
  },
  {
    image: "https://hayatpay.com.tr/_next/image?url=http%3A%2F%2Fhp-fs%3A9000%2Fhp-images%2Fespressolab_olculu_%25C3%2587al%25C4%25B1%25C5%259Fma%2520Y%25C3%25BCzeyi%25201%2520kopya%25205%2520copy%25204.jpg&w=3840&q=75",
    alt: "hayat",
    text: "Hayat Pay ile Espressolab'den bedava kahve almak için hemen Hayat Pay uygulamasını indir, kahve kodunu kap!",
    dates: {
      start: "18.09.2024",
      end: "31.10.2024"
    },
    info: {
      type: "Tümü",
      category: "Espressolab"
    }
  }
]

const itemsEnBegenilenler = [
  {
    image: "https://fintechtime.com/wp-content/uploads/2018/11/Vakifbank-yeni-kart-2018.jpg",
    alt: "visa",
    text: "Visa Private Kredi Kartı ile Hediye Kahve Kazan!",
    dates: {
      start: "01.10.2024",
      end: "31.10.2024"
    },
    info: {
      type: "Vakıfbank",
      category: "Espressolab"
    }
  },
  {
    image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgBJKXghhZtAlmcQ3p62QL23zCvrt7sXLcPA&s",
    alt: "axess",
    text: "Axess ile OPET'te 4 kez 800TL'ye 250TL'ye varan Chip-Para! ",
    dates: {
      start: "02.02.2024",
      end: "11.11.2024"
    },
    info: {
      type: "Axess",
      category: "Opet"
    }
  }
]
</script>

<style>
.title {
  padding: 20px 16px 5px 16px;
  text-align: left;
  color: red;
}

.line {
  background-color: rgb(255, 0, 0);
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
