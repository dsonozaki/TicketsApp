package com.example.data.db.entities

import androidx.room.Embedded
import com.sonozaki.ticketsapp.domain.entities.Price

data class LuggageDb(
    val hasLuggage: Boolean,
    @Embedded(prefix = "price_") val price: Price?
)