package dev.kiko.qrcodegenerator.ui.screens.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen() {
    val context = LocalContext.current
    var scanResult by remember { mutableStateOf<String?>(null) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            // Разрешение получено, можно запускать сканер
        } else {
            Toast.makeText(
                context,
                "Для сканирования QR-кодов необходимо разрешение на использование камеры",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val scannerLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            // Просто используем результат напрямую без преобразований
            scanResult = result.contents
            
            // Если в результате все равно есть проблемы с кодировкой, можно попробовать разные варианты
            if (scanResult?.contains("?") == true) {
                try {
                    // Вариант 1: Преобразование через Windows-1251 (для кириллицы)
                    val bytes = result.contents.toByteArray(Charsets.ISO_8859_1)
                    scanResult = String(bytes, Charsets.UTF_8)
                    
                    // Если все равно есть вопросы, пробуем другие кодировки
                    if (scanResult?.contains("?") == true) {
                        // Вариант 2: Преобразование через Windows-1251 (часто используется для кириллицы)
                        scanResult = String(bytes, charset("windows-1251"))
                    }
                } catch (e: Exception) {
                    // В случае ошибки возвращаемся к исходному результату
                    scanResult = result.contents
                }
            }
        }
    }

    LaunchedEffect(key1 = cameraPermissionState) {
        if (cameraPermissionState.status != PermissionStatus.Granted) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Сканер QR-кодов",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (hasCameraPermission) {
            Button(
                onClick = {
                    scannerLauncher.launch(
                        ScanOptions()
                            .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                            .setPrompt("Наведите камеру на QR-код")
                            .setCameraId(0)
                            .setBeepEnabled(true)
                            .setBarcodeImageEnabled(true)
                            .setOrientationLocked(false)
                            .addExtra("CHARACTER_SET", "UTF-8")
                    )
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Начать сканирование")
            }
        } else {
            Button(
                onClick = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Разрешить доступ к камере")
            }
        }

        scanResult?.let { result ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Результат сканирования:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = {
                            scanResult = null
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(0.8f)
                    ) {
                        Text("Сканировать снова")
                    }
                }
            }
        }
    }
} 