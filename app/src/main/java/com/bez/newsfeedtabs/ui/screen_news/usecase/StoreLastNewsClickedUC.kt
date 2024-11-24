package com.bez.newsfeedtabs.ui.screen_news.usecase

import com.bez.newsfeedtabs.utils.DataStoreManager
import javax.inject.Inject

class StoreLastNewsClickedUC @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend fun execute(newsTitle: String) {
        dataStoreManager.saveLastClickedNewsTitle(newsTitle)
    }
}
