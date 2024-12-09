package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.repositories.StartCityRepository
import javax.inject.Inject
/**
 * Save start city in cache
 */
class SetStartCityUseCase @Inject constructor(private val repository: StartCityRepository) {
    suspend operator fun invoke(point: String) {
        repository.updateStartPoint(point)
    }
}