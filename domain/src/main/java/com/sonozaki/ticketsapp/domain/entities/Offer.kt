package com.sonozaki.ticketsapp.domain.entities

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price,
)

data class Price(
    val value: Int,
)
