package com.bez.newsfeedtabs.ui.screen_info

import com.bez.newsfeedtabs.utils.DataStoreManager
import com.bez.newsfeedtabs.utils.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUC  @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend fun execute(): Flow<UserInfo> = dataStoreManager.userInfoFlow
}
