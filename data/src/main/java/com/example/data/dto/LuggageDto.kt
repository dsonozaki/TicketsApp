package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LuggageDto(
    @SerializedName("has_luggage")
    @Expose
    val hasLuggage: Boolean,
    @SerializedName("price")
    @Expose
    val price: PriceDto?
)