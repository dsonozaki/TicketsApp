package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketRepository
import javax.inject.Inject
/**
 * Try to update tickets cache. If provided travelParams differ from stored in cache parameters repository, try to load data. If data loaded successfully, update cache parameters.
 */
class UpdateTicketsUseCase @Inject constructor(private val cachedParamsRepository: CachedParamsRepository, private val ticketRepository: TicketRepository) {
    suspend operator fun invoke(travelParams: TravelParams) {
        if (cachedParamsRepository.checkCachedTicketsParams(travelParams)) {
            if (ticketRepository.updateTickets())
                cachedParamsRepository.updateCachedTicketsParams(travelParams)
        }
    }
}