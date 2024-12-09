package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import kotlinx.coroutines.flow.Flow

/**
 * This repository stores the tickets cache.
 */
interface TicketRepository {
    /**
     * Get cached ticket offers
     */
    fun getTickets(): Flow<RequestResult<List<Ticket>>>
    /**
     * Load new data in cache. Must be called only if data not found in cache.
     * @return true if data loaded successfully
     */
    suspend fun updateTickets(): Boolean
}