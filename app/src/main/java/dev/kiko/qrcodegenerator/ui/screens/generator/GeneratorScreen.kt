package dev.kiko.qrcodegenerator.ui.screens.generator

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import dev.kiko.qrcodegenerator.data.models.QrCodeType
import dev.kiko.qrcodegenerator.ui.icons.ValkyrieIcons
import dev.kiko.qrcodegenerator.ui.icons.QrCode
import dev.kiko.qrcodegenerator.ui.icons.Save
import dev.kiko.qrcodegenerator.utils.MediaStoreUtils

@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var generatedQrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var currentContent by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Текст", "URL", "Контакт", "Wi-Fi")
    
    // Использование кастомных иконок
    val tabIcons = listOf(
        ValkyrieIcons.QrCode,
        ValkyrieIcons.QrCode,
        ValkyrieIcons.QrCode,
        ValkyrieIcons.QrCode
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Генератор QR-кода",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { 
                        selectedTabIndex = index
                        generatedQrBitmap = null 
                    },
                    text = { Text(title) },
                    icon = { Icon(tabIcons[index], contentDescription = title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> TextQrGenerator(
                onGenerateQr = { content ->
                    currentContent = content
                    generatedQrBitmap = generateQrCode(content)
                    generatedQrBitmap?.let { bitmap ->
                        val type = getQrTypeFromIndex(selectedTabIndex)
                        viewModel.saveQrCode(content, bitmap, type)
                    }
                }
            )
            1 -> UrlQrGenerator(
                onGenerateQr = { url ->
                    currentContent = url
                    generatedQrBitmap = generateQrCode(url)
                    generatedQrBitmap?.let { bitmap ->
                        val type = getQrTypeFromIndex(selectedTabIndex)
                        viewModel.saveQrCode(url, bitmap, type)
                    }
                }
            )
            2 -> ContactQrGenerator(
                onGenerateQr = { vCardContent ->
                    currentContent = vCardContent
                    generatedQrBitmap = generateQrCode(vCardContent)
                    generatedQrBitmap?.let { bitmap ->
                        val type = getQrTypeFromIndex(selectedTabIndex)
                        viewModel.saveQrCode(vCardContent, bitmap, type)
                    }
                }
            )
            3 -> WifiQrGenerator(
                onGenerateQr = { wifiContent ->
                    currentContent = wifiContent
                    generatedQrBitmap = generateQrCode(wifiContent)
                    generatedQrBitmap?.let { bitmap ->
                        val type = getQrTypeFromIndex(selectedTabIndex)
                        viewModel.saveQrCode(wifiContent, bitmap, type)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        generatedQrBitmap?.let { bitmap ->
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.size(240.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val success = saveQrCodeToGallery(context, bitmap)
                                if (success) {
                                    Toast.makeText(context, "QR-код сохранен в галерею", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Ошибка при сохранении QR-кода", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(ValkyrieIcons.Save, contentDescription = "Save")
                            Text(text = "Сохранить в галерею", modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextQrGenerator(onGenerateQr: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { if (text.isNotEmpty()) onGenerateQr(text) },
            modifier = Modifier.align(Alignment.End),
            enabled = text.isNotEmpty()
        ) {
            Text("Создать QR-код")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlQrGenerator(onGenerateQr: (String) -> Unit) {
    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Введите URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { if (url.isNotEmpty()) onGenerateQr(url) },
            modifier = Modifier.align(Alignment.End),
            enabled = url.isNotEmpty()
        ) {
            Text("Создать QR-код")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactQrGenerator(onGenerateQr: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Телефон") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() || phone.isNotEmpty() || email.isNotEmpty()) {
                    val vCardContent = buildString {
                        append("BEGIN:VCARD\nVERSION:3.0\n")
                        if (name.isNotEmpty()) append("FN:$name\n")
                        if (phone.isNotEmpty()) append("TEL:$phone\n")
                        if (email.isNotEmpty()) append("EMAIL:$email\n")
                        append("END:VCARD")
                    }
                    onGenerateQr(vCardContent)
                }
            },
            modifier = Modifier.align(Alignment.End),
            enabled = name.isNotEmpty() || phone.isNotEmpty() || email.isNotEmpty()
        ) {
            Text("Создать QR-код")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiQrGenerator(onGenerateQr: (String) -> Unit) {
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isHidden by remember { mutableStateOf(false) }
    var encryptionType by remember { mutableStateOf("WPA") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = ssid,
            onValueChange = { ssid = it },
            label = { Text("SSID (Имя сети)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (ssid.isNotEmpty()) {
                    val wifiContent = "WIFI:S:$ssid;T:$encryptionType;P:$password;H:${if (isHidden) "true" else "false"};"
                    onGenerateQr(wifiContent)
                }
            },
            modifier = Modifier.align(Alignment.End),
            enabled = ssid.isNotEmpty()
        ) {
            Text("Создать QR-код")
        }
    }
}

fun generateQrCode(content: String): Bitmap? {
    return try {
        val qrCodeWriter = QRCodeWriter()
        val hints = HashMap<com.google.zxing.EncodeHintType, Any>()
        hints[com.google.zxing.EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[com.google.zxing.EncodeHintType.MARGIN] = 1
        val bitMatrix: BitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        
        bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}

fun saveQrCodeToGallery(context: Context, bitmap: Bitmap): Boolean {
    val mediaStoreUtils = MediaStoreUtils(context)
    return mediaStoreUtils.saveImageToGallery(bitmap, "QRCode_${System.currentTimeMillis()}")
}

fun getQrTypeFromIndex(index: Int): QrCodeType {
    return when (index) {
        0 -> QrCodeType.TEXT
        1 -> QrCodeType.URL
        2 -> QrCodeType.CONTACT
        3 -> QrCodeType.WIFI
        else -> QrCodeType.TEXT
    }
} 