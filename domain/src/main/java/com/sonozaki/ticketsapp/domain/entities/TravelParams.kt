package com.sonozaki.ticketsapp.domain.entities

data class TravelParams(
    val startCity: String= "",
    val endCity: String = "",
    val departureDate: Long = 0
)