package com.sonozaki.ticketsapp.domain.usecases

import com.sonozaki.ticketsapp.domain.repositories.StartCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStartCityUseCase @Inject constructor(private val repository: StartCityRepository) {
    operator fun invoke(): Flow<String> = repository.getStartPoint()
}