package com.example.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonozaki.ticketsapp.domain.entities.Departure
import com.sonozaki.ticketsapp.domain.entities.HandLuggage
import com.sonozaki.ticketsapp.domain.entities.Price


@Entity(tableName = "tickets")
data class TicketDb(
    @PrimaryKey
    val id: Int,
    val badge: String?,
    @Embedded(prefix = "price_")
    val price: Price,
    val providerName: String,
    val company: String,
    @Embedded(prefix = "departure_")
    val departure: Departure,
    @Embedded(prefix = "arrival_")
    val arrival: Departure,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    @Embedded(prefix = "luggage_")
    val luggage: LuggageDb,
    @Embedded(prefix = "handLuggage_")
    val handLuggage: HandLuggage,
    val isReturnable: Boolean,
    val isExchangeable: Boolean,
)