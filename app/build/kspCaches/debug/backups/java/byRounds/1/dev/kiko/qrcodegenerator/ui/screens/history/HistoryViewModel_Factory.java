package dev.kiko.qrcodegenerator.ui.screens.history;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.kiko.qrcodegenerator.data.repository.QrCodeRepository;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<QrCodeRepository> qrCodeRepositoryProvider;

  public HistoryViewModel_Factory(Provider<QrCodeRepository> qrCodeRepositoryProvider) {
    this.qrCodeRepositoryProvider = qrCodeRepositoryProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(qrCodeRepositoryProvider.get());
  }

  public static HistoryViewModel_Factory create(
      Provider<QrCodeRepository> qrCodeRepositoryProvider) {
    return new HistoryViewModel_Factory(qrCodeRepositoryProvider);
  }

  public static HistoryViewModel newInstance(QrCodeRepository qrCodeRepository) {
    return new HistoryViewModel(qrCodeRepository);
  }
}
