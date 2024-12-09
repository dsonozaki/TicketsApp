package com.example.data.mappers

import com.example.data.db.entities.LuggageDb
import com.example.data.db.entities.OfferDb
import com.example.data.db.entities.TicketDb
import com.example.data.dto.DepartureDto
import com.example.data.dto.HandLuggageDto
import com.example.data.dto.LuggageDto
import com.example.data.dto.OfferDto
import com.example.data.dto.OffersResponseDto
import com.example.data.dto.PriceDto
import com.example.data.dto.TicketDto
import com.example.data.dto.TicketsResponseDto
import com.sonozaki.ticketsapp.domain.entities.Departure
import com.sonozaki.ticketsapp.domain.entities.HandLuggage
import com.sonozaki.ticketsapp.domain.entities.Luggage
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.Price
import com.sonozaki.ticketsapp.domain.entities.Ticket
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