package com.example.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonozaki.ticketsapp.domain.entities.Price

@Entity(tableName = "offers")
data class OfferDb(
    @PrimaryKey
    val id: Int,
    val title: String,
    val town: String,
    @Embedded(prefix = "price_")
    val price: Price,
)
