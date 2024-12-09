package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("town")
    @Expose
    val town: String,
    @SerializedName("price")
    @Expose
    val price: PriceDto,
)

data class PriceDto(
    @SerializedName("value")
    @Expose
    val value: Int,
)
