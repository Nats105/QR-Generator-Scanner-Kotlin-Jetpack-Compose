package dev.kiko.qrcodegenerator.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.kiko.qrcodegenerator.data.local.QrCodeDao;
import dev.kiko.qrcodegenerator.data.repository.QrCodeRepository;
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
public final class AppModule_ProvideQrCodeRepositoryFactory implements Factory<QrCodeRepository> {
  private final Provider<QrCodeDao> qrCodeDaoProvider;

  public AppModule_ProvideQrCodeRepositoryFactory(Provider<QrCodeDao> qrCodeDaoProvider) {
    this.qrCodeDaoProvider = qrCodeDaoProvider;
  }

  @Override
  public QrCodeRepository get() {
    return provideQrCodeRepository(qrCodeDaoProvider.get());
  }

  public static AppModule_ProvideQrCodeRepositoryFactory create(
      Provider<QrCodeDao> qrCodeDaoProvider) {
    return new AppModule_ProvideQrCodeRepositoryFactory(qrCodeDaoProvider);
  }

  public static QrCodeRepository provideQrCodeRepository(QrCodeDao qrCodeDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQrCodeRepository(qrCodeDao));
  }
}
