package com.example.data.mappers

import com.example.data.db.entities.LuggageDb
import com.example.data.dto.DepartureDto
import com.example.data.dto.HandLuggageDto
import com.example.data.dto.LuggageDto
import com.example.data.dto.PriceDto
import com.sonozaki.ticketsapp.domain.entities.Departure
import com.sonozaki.ticketsapp.domain.entities.HandLuggage
import com.sonozaki.ticketsapp.domain.entities.Luggage
import com.sonozaki.ticketsapp.domain.entities.Price

fun PriceDto.toPrice(): Price {
    return Price(value = this.value)
}

fun DepartureDto.toDeparture(): Departure {
    return Departure(
        town = this.town,
        date = this.date,
        airport = this.airport
    )
}

fun LuggageDto.toLuggageDb(): LuggageDb {
    return LuggageDb(
        hasLuggage = this.hasLuggage,
        price = this.price?.toPrice()
    )
}

fun LuggageDb.toLuggage(): Luggage {
    return Luggage(
        hasLuggage = this.hasLuggage,
        price = this.price
    )
}

fun HandLuggageDto.toHandLuggage(): HandLuggage {
    return HandLuggage(
        hasHandLuggage = this.hasHandLuggage,
        size = this.size
    )
}