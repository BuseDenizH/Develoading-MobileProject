import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router';
import {
  IonicVue,
  IonButtons
} from '@ionic/vue';

/* Core CSS required for Ionic components to work properly */
import '@ionic/vue/css/core.css';
import '@ionic/vue/css/normalize.css';
import '@ionic/vue/css/structure.css';
import '@ionic/vue/css/typography.css';
import '@ionic/vue/css/padding.css';
import '@ionic/vue/css/float-elements.css';
import '@ionic/vue/css/text-alignment.css';
import '@ionic/vue/css/text-transformation.css';
import '@ionic/vue/css/flex-utils.css';
import '@ionic/vue/css/display.css';
import '@ionic/vue/css/palettes/dark.system.css';
import './theme/variables.css';

import { defineCustomElements } from '@ionic/pwa-elements/loader';

const pinia = createPinia()

const app = createApp(App)
app.component('ion-buttons', IonButtons)

app
    .use(IonicVue)
    .use(router)
    .use(pinia);

// Promise'i işle
(async () => {
  await defineCustomElements(window);
  await router.isReady();
  app.mount('#app');
})();