package dev.kiko.qrcodegenerator.ui.screens.generator

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kiko.qrcodegenerator.data.models.QrCodeType
import dev.kiko.qrcodegenerator.data.repository.QrCodeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratorViewModel @Inject constructor(
    private val qrCodeRepository: QrCodeRepository
) : ViewModel() {

    fun saveQrCode(content: String, bitmap: Bitmap, type: QrCodeType) {
        viewModelScope.launch {
            qrCodeRepository.saveQrCode(content, bitmap, type)
        }
    }
} 