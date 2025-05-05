package dev.kiko.qrcodegenerator.ui.screens.generator;

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
public final class GeneratorViewModel_Factory implements Factory<GeneratorViewModel> {
  private final Provider<QrCodeRepository> qrCodeRepositoryProvider;

  public GeneratorViewModel_Factory(Provider<QrCodeRepository> qrCodeRepositoryProvider) {
    this.qrCodeRepositoryProvider = qrCodeRepositoryProvider;
  }

  @Override
  public GeneratorViewModel get() {
    return newInstance(qrCodeRepositoryProvider.get());
  }

  public static GeneratorViewModel_Factory create(
      Provider<QrCodeRepository> qrCodeRepositoryProvider) {
    return new GeneratorViewModel_Factory(qrCodeRepositoryProvider);
  }

  public static GeneratorViewModel newInstance(QrCodeRepository qrCodeRepository) {
    return new GeneratorViewModel(qrCodeRepository);
  }
}
