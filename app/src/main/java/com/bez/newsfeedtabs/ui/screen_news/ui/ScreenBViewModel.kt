package com.bez.newsfeedtabs.ui.screen_news.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bez.newsfeedtabs.ui.screen_news.usecase.StoreLastNewsClickedUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenBViewModel @Inject constructor(
    private val storeLastNewsClickedUC: StoreLastNewsClickedUC
) : ViewModel() {

    fun saveNewsItemTitle(newsTitle: String) {
        viewModelScope.launch {
            storeLastNewsClickedUC.execute(newsTitle)
        }
    }
}
