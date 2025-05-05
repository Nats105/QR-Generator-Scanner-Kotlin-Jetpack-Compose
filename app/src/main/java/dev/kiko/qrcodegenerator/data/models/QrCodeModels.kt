package dev.kiko.qrcodegenerator.data.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory

enum class QrCodeType {
    TEXT,
    URL,
    CONTACT,
    WIFI
}

@Entity(tableName = "qr_codes")
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: QrCodeType,
    val content: String,
    val imageBytes: ByteArray?,
    val createdAt: Long = System.currentTimeMillis()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QrCodeEntity

        if (id != other.id) return false
        if (type != other.type) return false
        if (content != other.content) return false
        if (imageBytes != null) {
            if (other.imageBytes == null) return false
            if (!imageBytes.contentEquals(other.imageBytes)) return false
        } else if (other.imageBytes != null) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + (imageBytes?.contentHashCode() ?: 0)
        result = 31 * result + createdAt.hashCode()
        return result
    }
}

data class SavedQrCode(
    val id: Long,
    val type: QrCodeType,
    val content: String,
    val bitmap: Bitmap?,
    val createdAt: Long
)

data class HistoryState(
    val qrCodes: List<SavedQrCode> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class QrCodeTypeConverter {
    @TypeConverter
    fun fromQrCodeType(type: QrCodeType): String {
        return type.name
    }

    @TypeConverter
    fun toQrCodeType(value: String): QrCodeType {
        return QrCodeType.valueOf(value)
    }
}

class BitmapConverter {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        return if (byteArray == null) null
        else BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
} 