package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.entities.TravelParams
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
     * Load new data in cache. Must be called only if cache invalidated.
     */
    suspend fun updateTicketOffers()
}