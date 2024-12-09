package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TicketOfferDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("time_range")
    @Expose
    val timeRange: List<String>,
    @SerializedName("price")
    @Expose
    val price: PriceDto
)
