package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import kotlinx.coroutines.flow.Flow

/**
 * This repository stores the ticket offers cache.
 */
interface TicketOfferRepository {
    /**
     * Get cached ticket offers
     */
    fun getTicketOffers(): Flow<RequestResult<List<TicketOffer>>>
    /**
     * Load new data in cache. Must be called only if data not found in cache.
     * @return true if data loaded successfully
     */
    suspend fun updateTicketOffers(): Boolean
}