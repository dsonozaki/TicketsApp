package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.repositories.OfferRepository
import javax.inject.Inject

class UpdateOffersUseCase @Inject constructor(private val repository: OfferRepository) {
    suspend operator fun invoke() {
        repository.updateData()
    }
}