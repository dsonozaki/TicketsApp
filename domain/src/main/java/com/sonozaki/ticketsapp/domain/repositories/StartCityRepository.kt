package com.sonozaki.ticketsapp.domain.repositories

import kotlinx.coroutines.flow.Flow

interface StartCityRepository {
    fun getStartPoint(): Flow<String>
    suspend fun updateStartPoint(point: String)
}