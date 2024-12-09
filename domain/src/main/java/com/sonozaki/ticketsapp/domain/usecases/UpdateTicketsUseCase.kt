package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketRepository
import javax.inject.Inject

class UpdateTicketsUseCase @Inject constructor(private val cachedParamsRepository: CachedParamsRepository, private val ticketRepository: TicketRepository) {
    suspend operator fun invoke(travelParams: TravelParams) {
        if (cachedParamsRepository.updateCachedTicketsParams(travelParams)) {
            ticketRepository.updateTickets(travelParams)
        }
    }
}