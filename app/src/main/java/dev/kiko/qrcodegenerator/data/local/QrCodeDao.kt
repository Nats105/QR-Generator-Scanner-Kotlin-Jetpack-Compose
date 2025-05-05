package dev.kiko.qrcodegenerator.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kiko.qrcodegenerator.data.models.QrCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {
    @Query("SELECT * FROM qr_codes ORDER BY createdAt DESC")
    fun getAllQrCodes(): Flow<List<QrCodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQrCode(qrCode: QrCodeEntity): Long

    @Delete
    suspend fun deleteQrCode(qrCode: QrCodeEntity)

    @Query("DELETE FROM qr_codes WHERE id = :id")
    suspend fun deleteQrCodeById(id: Long)
} 