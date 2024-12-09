package com.example.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonozaki.ticketsapp.domain.entities.Price

@Entity(tableName = "ticket_offers")
data class TicketOfferDb(
    @PrimaryKey
    val id: Int,
    val title: String,
    val timeRange: String,
    @Embedded(prefix = "handLuggage_")
    val price: Price
)
