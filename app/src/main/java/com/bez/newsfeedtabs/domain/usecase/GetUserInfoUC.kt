package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.data.local.DataStoreManager
import com.bez.newsfeedtabs.data.local.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUC  @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend fun execute(): Flow<UserInfo> = dataStoreManager.userInfoFlow
}
