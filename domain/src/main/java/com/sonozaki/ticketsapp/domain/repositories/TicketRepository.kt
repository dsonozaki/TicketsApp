package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun getTickets(): Flow<RequestResult<List<Ticket>>>
    suspend fun updateTickets(travelParams: TravelParams)
}