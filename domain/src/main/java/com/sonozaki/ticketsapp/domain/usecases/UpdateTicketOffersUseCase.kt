package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketOfferRepository
import javax.inject.Inject

class UpdateTicketOffersUseCase @Inject constructor(private val cachedParamsRepository: CachedParamsRepository, private val ticketOfferRepository: TicketOfferRepository) {
    suspend operator fun invoke(travelParams: TravelParams) {
        if (cachedParamsRepository.updateCachedOffersParams(travelParams)) {
            ticketOfferRepository.updateTicketOffers(travelParams)
        }
    }
}