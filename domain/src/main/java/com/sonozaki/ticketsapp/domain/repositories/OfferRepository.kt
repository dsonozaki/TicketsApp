package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import kotlinx.coroutines.flow.Flow

/**
 * This repository stores the offers cache. Cache invalidates automatically when app restart.
 */
interface OfferRepository {
    /**
     * Get cached offers
     */
    fun getOffers(): Flow<RequestResult<List<Offer>>>

    /**
     * Try to load new data in cache. Does nothing if cache is still valid.
     */
    suspend fun updateData()
}