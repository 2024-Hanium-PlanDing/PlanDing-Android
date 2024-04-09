package com.comst.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_datastore")

class UserDataStore @Inject constructor(
    private val context: Context
) {

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun setAccessToken(token: String) {
        context.dataStore.edit { pref ->
            pref[KEY_ACCESS_TOKEN] = token
        }
    }

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.map { it[KEY_ACCESS_TOKEN] }.firstOrNull()
    }

    suspend fun setRefreshToken(refreshToken: String) {
        context.dataStore.edit { pref ->
            pref[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.map { it[KEY_REFRESH_TOKEN] }.firstOrNull()
    }

    suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }
}