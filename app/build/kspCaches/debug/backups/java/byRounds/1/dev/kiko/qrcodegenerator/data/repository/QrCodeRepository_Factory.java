package dev.kiko.qrcodegenerator.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.kiko.qrcodegenerator.data.local.QrCodeDao;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class QrCodeRepository_Factory implements Factory<QrCodeRepository> {
  private final Provider<QrCodeDao> qrCodeDaoProvider;

  public QrCodeRepository_Factory(Provider<QrCodeDao> qrCodeDaoProvider) {
    this.qrCodeDaoProvider = qrCodeDaoProvider;
  }

  @Override
  public QrCodeRepository get() {
    return newInstance(qrCodeDaoProvider.get());
  }

  public static QrCodeRepository_Factory create(Provider<QrCodeDao> qrCodeDaoProvider) {
    return new QrCodeRepository_Factory(qrCodeDaoProvider);
  }

  public static QrCodeRepository newInstance(QrCodeDao qrCodeDao) {
    return new QrCodeRepository(qrCodeDao);
  }
}
