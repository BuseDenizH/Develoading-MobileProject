<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-button @click="goBack" class="back-button">
            <ion-icon aria-hidden="true" :icon="arrowBackOutline" />
            <ion-label>Geri</ion-label>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
      <ion-toolbar>
        <ion-title class="name-header">
          <h1>Gizlilik Politikası</h1>
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div class="policy-content">
        <h2>Gizlilik Politikası</h2>
        <p><strong>Son Güncelleme:</strong> 14.01.2025</p>
        <p>Campagnify olarak, gizliliğinize büyük önem veriyoruz ve kişisel verilerinizi korumak için gerekli tüm önlemleri alıyoruz. Bu Gizlilik Politikası, uygulamamızı kullanırken hangi verileri topladığımızı, nasıl kullandığımızı ve bu verilerin güvenliğini nasıl sağladığımızı açıklar.</p>

        <h3>1. Toplanan Veriler</h3>
        <p>Kullanıcıların kayıt olması ve hizmetlerimizi kullanabilmesi için aşağıdaki kişisel verileri topluyoruz:</p>
        <ul>
          <li><strong>Zorunlu Veriler:</strong></li>
          <ul>
            <li>E-posta Adresi</li>
            <li>Şifre (şifreler şifrelenerek saklanır)</li>
          </ul>
          <li><strong>Opsiyonel Veriler:</strong></li>
          <ul>
            <li>İletişim Tercihleri</li>
            <li>Kullanıcı Geri Bildirimleri</li>
          </ul>
        </ul>
        <p>Ayrıca, uygulamanın kullanımına bağlı olarak otomatik olarak toplanan bazı veriler şunlardır:</p>
        <ul>
          <li>Cihaz Bilgileri (cihaz türü, işletim sistemi)</li>
          <li>Kullanım Verileri (ziyaret edilen sayfalar, tıklamalar)</li>
        </ul>

        <h3>2. Verilerin Kullanım Amaçları</h3>
        <p>Topladığımız kişisel veriler aşağıdaki amaçlar için kullanılmaktadır:</p>
        <ul>
          <li>Kullanıcı hesabı oluşturmak ve giriş işlemlerini gerçekleştirmek</li>
          <li>Kampanyalar ve içerikler hakkında bilgilendirme sağlamak</li>
          <li>Kullanıcı deneyimini iyileştirmek</li>
          <li>Yasal yükümlülüklerimizi yerine getirmek</li>
        </ul>

        <h3>3. Üçüncü Taraflarla Veri Paylaşımı</h3>
        <p>Kişisel verileriniz izniniz olmaksızın üçüncü taraflarla paylaşılmamaktadır. Ancak aşağıdaki durumlarda veri paylaşımı söz konusu olabilir:</p>
        <ul>
          <li>Yasal zorunluluklar nedeniyle yetkili mercilerle</li>
          <li>Veri işleyici hizmet sağlayıcıları (örneğin, bulut depolama hizmetleri)</li>
        </ul>
        <p>Bu hizmet sağlayıcılarla paylaşılan veriler yalnızca belirtilen amaçlar doğrultusunda kullanılmaktadır.</p>

        <h3>4. Verilerin Korunması</h3>
        <p>Verilerinizi yetkisiz erişim, değiştirme, ifşa ve yok olmaya karşı korumak için güçlü güvenlik önlemleri uyguluyoruz:</p>
        <ul>
          <li>Şifreleme (şifrelerin güvenli bir şekilde saklanması)</li>
          <li>Güvenli sunucular ve bağlantılar</li>
          <li>Düzenli güvenlik testleri</li>
        </ul>

        <h3>5. Çerez Politikasi</h3>
        <p>Uygulama içi deneyiminizi geliştirmek için çerezler kullanabiliriz. Çerezler, kullanıcı tercihlerinizi hatırlamamıza ve kullanım istatistiklerini analiz etmemize yardımcı olur. Çerezleri tarayıcı ayarlarınızdan yönetebilirsiniz.</p>

        <h3>6. Haklarınız</h3>
        <p>Kullanıcılar olarak aşağıdaki haklara sahipsiniz:</p>
        <ul>
          <li>Kişisel verilere erişim</li>
          <li>Düzeltme veya silme talebi</li>
          <li>İşleme kısıtlaması talebi</li>
          <li>Veri taşınabilirliği</li>
          <li>İtiraz hakkı</li>
        </ul>
        <p>Bu haklarınızı kullanmak için <a href="mailto:aerenarslan@outlook.com">aerenarslan@outlook.com</a> ile iletişime geçebilirsiniz.</p>

        <h3>7. Gizlilik Politikasındaki Değişiklikler</h3>
        <p>Bu Gizlilik Politikası, güncellenebilir. Herhangi bir önemli değişiklik durumunda kullanıcılarımıza bildirimde bulunacağız.</p>

        <h3>8. İletişim Bilgileri</h3>
        <p>Gizlilik politikamız hakkında sorularınız varsa bizimle iletişime geçebilirsiniz:</p>
        <p><strong>E-posta:</strong> <a href="mailto:aerenarslan@outlook.com">aerenarslan@outlook.com</a></p>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { arrowBackOutline } from "ionicons/icons";

const router = useRouter();

const goBack = () => {
  router.back();
};
</script>

<style>
.name-header {
  background-color: rgb(248, 79, 0);
  padding: 10px 0px;
  text-align: left;
  color: white;
  position: sticky;
  top: 0;
  z-index: 1;
}

.name-header h1 {
  margin-left: 30px;
  margin-bottom: 0;
  font-size: 30px;
  font-weight: bold;
}

.policy-content {
  padding: 20px;
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.back-button {
  color: #0066ff;
  font-size: 14px;
  margin-left: 5px;
}

.back-button ion-icon {
  margin-right: 5px;
}
</style>
