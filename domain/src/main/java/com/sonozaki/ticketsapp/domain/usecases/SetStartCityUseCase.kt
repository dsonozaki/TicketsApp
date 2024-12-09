package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.repositories.StartCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetStartCityUseCase @Inject constructor(private val repository: StartCityRepository) {
    suspend operator fun invoke(point: String) {
        repository.updateStartPoint(point)
    }
}