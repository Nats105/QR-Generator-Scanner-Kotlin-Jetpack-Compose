package dev.kiko.qrcodegenerator.data.repository

import android.graphics.Bitmap
import dev.kiko.qrcodegenerator.data.local.QrCodeDao
import dev.kiko.qrcodegenerator.data.models.QrCodeEntity
import dev.kiko.qrcodegenerator.data.models.QrCodeType
import dev.kiko.qrcodegenerator.data.models.SavedQrCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QrCodeRepository @Inject constructor(
    private val qrCodeDao: QrCodeDao
) {
    fun getAllQrCodes(): Flow<List<SavedQrCode>> {
        return qrCodeDao.getAllQrCodes().map { entities ->
            entities.map { entity ->
                SavedQrCode(
                    id = entity.id,
                    type = entity.type,
                    content = entity.content,
                    bitmap = entity.imageBytes?.let { BitmapConverter().toBitmap(it) },
                    createdAt = entity.createdAt
                )
            }
        }
    }

    suspend fun saveQrCode(content: String, bitmap: Bitmap, type: QrCodeType) {
        val imageBytes = BitmapConverter().fromBitmap(bitmap)
        val qrCodeEntity = QrCodeEntity(
            type = type,
            content = content,
            imageBytes = imageBytes
        )
        qrCodeDao.insertQrCode(qrCodeEntity)
    }

    suspend fun deleteQrCode(qrCode: SavedQrCode) {
        qrCodeDao.deleteQrCodeById(qrCode.id)
    }
}

class BitmapConverter {
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = java.io.ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    fun toBitmap(byteArray: ByteArray): Bitmap? {
        return android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
} 