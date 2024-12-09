package com.example.data.mappers

import com.example.data.dto.TravelParamsDto
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import javax.inject.Inject

class TravelParamsMapper @Inject constructor() {
    fun mapDtoToDomain(dto: TravelParamsDto): TravelParams {
        return TravelParams(
            startCity = dto.startCity,
            endCity = dto.endCity,
            departureDate = dto.departureDate
        )
    }

    fun mapDomainToDto(domain: TravelParams): TravelParamsDto {
        return TravelParamsDto(
            startCity = domain.startCity,
            endCity = domain.endCity,
            departureDate = domain.departureDate
        )
    }
}