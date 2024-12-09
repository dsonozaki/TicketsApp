package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.repositories.OfferRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Get cached offers
 */
class GetOffersUseCase @Inject constructor(private val repository: OfferRepository) {
    operator fun invoke(): Flow<RequestResult<List<Offer>>> = repository.getOffers()
}