package com.sonozaki.ticketsapp.domain.repositories

import kotlinx.coroutines.flow.Flow
/**
 * This repository stores the start city.
 */
interface StartCityRepository {
    /**
     * Get start city
     */
    fun getStartPoint(): Flow<String>
    /**
     * Update start city
     */
    suspend fun updateStartPoint(point: String)
}