package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import kotlinx.coroutines.flow.Flow
/**
 * This repository stores the tickets cache. Cache invalidates if travel params provided .
 */
interface TicketRepository {
    /**
     * Get cached ticket offers
     */
    fun getTickets(): Flow<RequestResult<List<Ticket>>>
    /**
     * Load new data in cache. Must be called only if cache invalidated.
     */
    suspend fun updateTickets()
}