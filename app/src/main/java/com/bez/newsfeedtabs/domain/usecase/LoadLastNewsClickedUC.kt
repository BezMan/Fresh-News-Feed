package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.data.local.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadLastNewsClickedUC  @Inject constructor(private val dataStoreManager: DataStoreManager) {
    fun execute(): Flow<String?> = dataStoreManager.lastClickedNewsTitleFlow
}
