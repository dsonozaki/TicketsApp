package com.sonozaki.ticketsapp.domain.repositories

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import kotlinx.coroutines.flow.Flow

interface TicketOfferRepository {
    fun getTicketOffers(): Flow<RequestResult<List<TicketOffer>>>
    suspend fun updateTicketOffers(travelParams: TravelParams)
}