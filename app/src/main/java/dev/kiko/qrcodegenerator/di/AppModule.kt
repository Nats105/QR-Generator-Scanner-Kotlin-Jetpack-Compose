package dev.kiko.qrcodegenerator.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kiko.qrcodegenerator.data.local.AppDatabase
import dev.kiko.qrcodegenerator.data.local.QrCodeDao
import dev.kiko.qrcodegenerator.data.repository.QrCodeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "qrcode_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQrCodeDao(appDatabase: AppDatabase): QrCodeDao {
        return appDatabase.qrCodeDao()
    }

    @Provides
    @Singleton
    fun provideQrCodeRepository(qrCodeDao: QrCodeDao): QrCodeRepository {
        return QrCodeRepository(qrCodeDao)
    }
} 