package com.bez.newsfeedtabs.ui.screen_info

import com.bez.newsfeedtabs.utils.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadLastNewsClickedUC  @Inject constructor(private val dataStoreManager: DataStoreManager) {
    fun execute(): Flow<String?> = dataStoreManager.lastClickedNewsTitleFlow
}
