package com.university.androidchatbot.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.university.androidchatbot.data.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val username = stringPreferencesKey("username")
        val token = stringPreferencesKey("token")
    }

    val userPreferencesStream: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { mapUserPreferences(it) }

    suspend fun save(userPreferences: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.username] = userPreferences.username
            preferences[PreferencesKeys.token] = userPreferences.token
        }
    }

    private fun mapUserPreferences(preferences: Preferences) =
        UserPreferences(
            username = preferences[PreferencesKeys.username]  ?: "",
            token = preferences[PreferencesKeys.token]  ?: ""
        )
}