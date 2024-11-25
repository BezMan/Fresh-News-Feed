package com.bez.newsfeedtabs.ui.screen_info.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bez.newsfeedtabs.domain.usecase.GetUserInfoUC
import com.bez.newsfeedtabs.domain.usecase.LoadLastNewsClickedUC
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

    private val _uiState = MutableStateFlow(ScreenAUiState())
    val uiState: StateFlow<ScreenAUiState> get() = _uiState

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

    @Immutable
    data class ScreenAUiState(
        val userName: String = "User",
        val lastEntryTime: String = "Unknown",
        val lastClickedNewsTitle: String? = null
    )
}
