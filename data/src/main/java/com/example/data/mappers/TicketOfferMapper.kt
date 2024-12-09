package com.example.data.mappers

import com.example.data.db.entities.TicketOfferDb
import com.example.data.dto.TicketOfferDto
import com.example.data.dto.TicketOffersResponseDto
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import javax.inject.Inject

class TicketOfferMapper @Inject constructor() {

    private fun mapDtoToDb(dto: TicketOfferDto) = TicketOfferDb(
        id = dto.id,
        title = dto.title,
        timeRange = dto.timeRange.joinToString(" "),
        price = dto.price.toPrice()
    )

    fun mapDtoToDbList(dto: TicketOffersResponseDto): List<TicketOfferDb> = dto.ticketsOffers.map {
        mapDtoToDb(it)
    }

    private fun mapDbToDomain(ticketOfferDb: TicketOfferDb) = TicketOffer(
        id = ticketOfferDb.id,
        title = ticketOfferDb.title,
        timeRange = ticketOfferDb.timeRange,
        price = ticketOfferDb.price
    )

    fun mapDbToDomainList(db: List<TicketOfferDb>): List<TicketOffer> = db.map {
        mapDbToDomain(it)
    }
}