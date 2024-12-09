package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketOfferRepository
import javax.inject.Inject
/**
 * Try to update ticket offers cache. If provided travelParams differ from stored in cache parameters repository, invalidate cache and load new data.
 */
class UpdateTicketOffersUseCase @Inject constructor(private val cachedParamsRepository: CachedParamsRepository, private val ticketOfferRepository: TicketOfferRepository) {
    suspend operator fun invoke(travelParams: TravelParams) {
        if (cachedParamsRepository.updateCachedOffersParams(travelParams)) {
            ticketOfferRepository.updateTicketOffers()
        }
    }
}