package com.bez.newsfeedtabs.ui.screen_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUC,
    private val loadLastNewsClickedUC: LoadLastNewsClickedUC
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    init {
        loadUserInfo()
        loadLastNewsClicked()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.execute().collect { userInfo ->
                _uiState.value = _uiState.value.copy(
                    userName = userInfo.userName,
                    lastEntryTime = userInfo.lastEntryTime
                )
            }
        }
    }

    private fun loadLastNewsClicked() {
        viewModelScope.launch {
            loadLastNewsClickedUC.execute().collect { lastNewsTitle ->
                _uiState.value = _uiState.value.copy(
                    lastClickedNewsTitle = lastNewsTitle
                )
            }
        }
    }

    data class UiState(
        val userName: String = "",
        val lastEntryTime: String = "",
        val lastClickedNewsTitle: String? = null
    )
}
