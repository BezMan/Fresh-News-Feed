package com.bez.newsfeedtabs.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    private val dataStore: DataStore<Preferences> = context.dataStore

    private object PreferencesKeys {
        val userName = stringPreferencesKey("user_name")
        val lastEntryTime = stringPreferencesKey("last_entry_time")
        val lastClickedNewsTitle = stringPreferencesKey("last_clicked_news_title")
    }

    suspend fun saveUserInfo(userName: String, lastEntryTime: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.userName] = userName
            preferences[PreferencesKeys.lastEntryTime] = lastEntryTime
        }
    }

    suspend fun saveLastClickedNewsTitle(newsTitle: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.lastClickedNewsTitle] = newsTitle
        }
    }

    val userInfoFlow: Flow<UserInfo> = dataStore.data
        .map { preferences ->
            UserInfo(
                userName = preferences[PreferencesKeys.userName] ?: "Unknown",
                lastEntryTime = preferences[PreferencesKeys.lastEntryTime] ?: "Not available"
            )
        }

    val lastClickedNewsTitleFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.lastClickedNewsTitle]
        }
}

data class UserInfo(
    val userName: String,
    val lastEntryTime: String
)
