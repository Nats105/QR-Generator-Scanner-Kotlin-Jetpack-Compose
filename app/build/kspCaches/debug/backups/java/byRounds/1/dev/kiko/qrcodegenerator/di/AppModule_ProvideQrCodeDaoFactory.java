package dev.kiko.qrcodegenerator.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.kiko.qrcodegenerator.data.local.AppDatabase;
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
public final class AppModule_ProvideQrCodeDaoFactory implements Factory<QrCodeDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public AppModule_ProvideQrCodeDaoFactory(Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public QrCodeDao get() {
    return provideQrCodeDao(appDatabaseProvider.get());
  }

  public static AppModule_ProvideQrCodeDaoFactory create(
      Provider<AppDatabase> appDatabaseProvider) {
    return new AppModule_ProvideQrCodeDaoFactory(appDatabaseProvider);
  }

  public static QrCodeDao provideQrCodeDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQrCodeDao(appDatabase));
  }
}
