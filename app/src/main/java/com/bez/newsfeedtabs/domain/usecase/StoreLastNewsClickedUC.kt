package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.data.local.DataStoreManager
import javax.inject.Inject

class StoreLastNewsClickedUC @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend fun execute(newsTitle: String) {
        dataStoreManager.saveLastClickedNewsTitle(newsTitle)
    }
}
