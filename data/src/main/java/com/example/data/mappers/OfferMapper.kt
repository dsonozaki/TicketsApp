package com.example.data.mappers

import com.example.data.db.entities.OfferDb
import com.example.data.dto.OfferDto
import com.example.data.dto.OffersResponseDto
import com.sonozaki.ticketsapp.domain.entities.Offer
import javax.inject.Inject

class OfferMapper @Inject constructor() {

    private fun mapDtoToDb(dto: OfferDto) = OfferDb(
        id = dto.id,
        title = dto.title,
        town = dto.town,
        price = dto.price.toPrice()
    )

    fun mapDtoToDbList(dto: OffersResponseDto): List<OfferDb> = dto.offers.map {
        mapDtoToDb(it)
    }

    private fun mapDbToDomain(db: OfferDb) = Offer(
        id = db.id,
        title = db.title,
        town = db.town,
        price = db.price
    )

    fun mapDbToDomainList(db: List<OfferDb>): List<Offer> = db.map {
        mapDbToDomain(it)
    }
}