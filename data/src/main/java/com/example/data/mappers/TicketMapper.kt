package com.example.data.mappers

import com.example.data.db.entities.TicketDb
import com.example.data.dto.TicketDto
import com.example.data.dto.TicketsResponseDto
import com.sonozaki.ticketsapp.domain.entities.Ticket
import javax.inject.Inject

class TicketMapper @Inject constructor() {

    private fun mapDtoToDb(dto: TicketDto) = TicketDb(
        id = dto.id,
        badge = dto.badge,
        price = dto.price.toPrice(),
        providerName = dto.providerName,
        company = dto.company,
        departure = dto.departure.toDeparture(),
        arrival = dto.arrival.toDeparture(),
        hasTransfer = dto.hasTransfer,
        hasVisaTransfer = dto.hasVisaTransfer,
        luggage = dto.luggage.toLuggageDb(),
        handLuggage = dto.handLuggage.toHandLuggage(),
        isReturnable = dto.isReturnable,
        isExchangeable = dto.isExchangeable
    )

    fun mapDtoToDbList(dto: TicketsResponseDto): List<TicketDb> = dto.tickets.map {
        mapDtoToDb(it)
    }

    private fun mapDbToDomain(ticketDb: TicketDb) = Ticket(
        id = ticketDb.id,
        badge = ticketDb.badge,
        price = ticketDb.price,
        providerName = ticketDb.providerName,
        company = ticketDb.company,
        departure = ticketDb.departure,
        arrival = ticketDb.arrival,
        hasTransfer = ticketDb.hasTransfer,
        hasVisaTransfer = ticketDb.hasVisaTransfer,
        luggage = ticketDb.luggage.toLuggage(),
        handLuggage = ticketDb.handLuggage,
        isReturnable = ticketDb.isReturnable,
        isExchangeable = ticketDb.isExchangeable
    )

    fun mapDbToDomainList(db: List<TicketDb>): List<Ticket> = db.map {
        mapDbToDomain(it)
    }
}