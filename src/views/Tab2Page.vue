<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Deneme</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Tab 2</ion-title>
        </ion-toolbar>
      </ion-header>

      <div class="container">
        <img src="https://www.theinsuranceemporium.co.uk/blog/wp-content/uploads/2023/09/image-10.png" alt="horse">
        <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Asperiores totam officiis doloremque quibusdam aliquid? Quisquam.</p>
      </div>
      <div class="container">
        <img src="https://b1157417.smushcdn.com/1157417/wp-content/uploads/2024/07/cow-close-up-of-dairy-cattle-825x550.jpg?lossy=1&strip=1&webp=0" alt="cow">
        <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Mollitia, ratione eum. Iste quisquam tempora blanditiis.</p>
      </div>
      <div class="container">
        <img src="https://media.4-paws.org/c/7/2/e/c72ed88142663ae07df3a3d7a1f9a0b831e15f0c/VIER_PFOTEN_2020-10-24_00191-3000x1569-1200x628.jpg" alt="sheep">
        <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Mollitia, ratione eum. Iste quisquam tempora blanditiis.</p>
      </div>
      <div class="container">
        <img src="https://www.theinsuranceemporium.co.uk/blog/wp-content/uploads/2023/09/image-10.png" alt="horse">
        <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Mollitia, ratione eum. Iste quisquam tempora blanditiis.</p>
      </div><div class="container">
        <img src="https://www.theinsuranceemporium.co.uk/blog/wp-content/uploads/2023/09/image-10.png" alt="horse">
        <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Mollitia, ratione eum. Iste quisquam tempora blanditiis.</p>
      </div>

    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
</script>

<style scoped>
.container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  text-align: center;
  padding-top: 16px;
  margin-top: 16px;
  margin-left: 16px;
  margin-right: 16px;
  border-radius: 10px;
  background-color: aqua;
}

.container img {
  width: 300px;
  height: auto;
  border-radius: 10px;
}

.container p {
  font-size: 16px;
  line-height: 22px;
  color: white;
  background-color: blueviolet;
  margin: 10px;
  width: 100%;
  padding: 5px 16px;
  font-weight: bold;
}
</style>
