package dev.kiko.qrcodegenerator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kiko.qrcodegenerator.data.models.QrCodeEntity
import dev.kiko.qrcodegenerator.data.models.QrCodeTypeConverter
import dev.kiko.qrcodegenerator.data.models.BitmapConverter

@Database(
    entities = [QrCodeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(QrCodeTypeConverter::class, BitmapConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QrCodeDao
} 