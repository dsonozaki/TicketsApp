package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketOfferRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/**
 * Get cached ticket offers
 */
class GetTicketOffersUseCase @Inject constructor(private val cachedParamsRepository: CachedParamsRepository, private val ticketOfferRepository: TicketOfferRepository) {
    operator fun invoke(): Flow<RequestResult<List<TicketOffer>>> {
        return ticketOfferRepository.getTicketOffers()
    }
}