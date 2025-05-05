package dev.kiko.qrcodegenerator.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kiko.qrcodegenerator.data.models.HistoryState
import dev.kiko.qrcodegenerator.data.models.SavedQrCode
import dev.kiko.qrcodegenerator.data.repository.QrCodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val qrCodeRepository: QrCodeRepository
) : ViewModel() {

    private val _qrCodesState = MutableStateFlow(HistoryState(isLoading = true))
    val qrCodesState: StateFlow<HistoryState> = _qrCodesState.asStateFlow()

    init {
        loadQrCodes()
    }

    private fun loadQrCodes() {
        viewModelScope.launch {
            qrCodeRepository.getAllQrCodes()
                .catch { e ->
                    _qrCodesState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = e.message ?: "Ошибка загрузки данных"
                        )
                    }
                }
                .collect { qrCodes ->
                    _qrCodesState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            qrCodes = qrCodes,
                            error = null
                        )
                    }
                }
        }
    }

    fun deleteQrCode(qrCode: SavedQrCode) {
        viewModelScope.launch {
            qrCodeRepository.deleteQrCode(qrCode)
            // Обновление списка произойдет автоматически через Flow
        }
    }
} 