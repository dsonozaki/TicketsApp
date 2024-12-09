package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TravelParamsDto(
    val startCity: String= "",
    val endCity: String = "",
    val departureDate: Long = 0
)