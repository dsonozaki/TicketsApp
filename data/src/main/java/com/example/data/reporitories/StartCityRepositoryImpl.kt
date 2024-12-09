package com.example.data.reporitories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sonozaki.ticketsapp.domain.repositories.StartCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StartCityRepositoryImpl(private val context: Context) : StartCityRepository {

    private val Context.startCityDataStore by preferencesDataStore(START_CITY_STORAGE)

    override fun getStartPoint(): Flow<String> =
        context.startCityDataStore.data.map { it[KEY_DATASTORE] ?: "" }

    override suspend fun updateStartPoint(point: String) {
        context.startCityDataStore.edit { it[KEY_DATASTORE] = point }
    }


    companion object {
        private const val START_CITY_STORAGE = "start_city.json"
        private const val CITY_KEY = "city_key"
        private val KEY_DATASTORE = stringPreferencesKey(CITY_KEY)
    }
}