package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TicketDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("badge")
    @Expose
    val badge: String?,
    @SerializedName("price")
    @Expose
    val price: PriceDto,
    @SerializedName("provider_name")
    @Expose
    val providerName: String,
    @SerializedName("company")
    @Expose
    val company: String,
    @SerializedName("departure")
    @Expose
    val departure: DepartureDto,
    @SerializedName("arrival")
    @Expose
    val arrival: DepartureDto,
    @SerializedName("has_transfer")
    @Expose
    val hasTransfer: Boolean,
    @SerializedName("has_visa_transfer")
    @Expose
    val hasVisaTransfer: Boolean,
    @SerializedName("luggage")
    @Expose
    val luggage: LuggageDto,
    @SerializedName("hand_luggage")
    @Expose
    val handLuggage: HandLuggageDto,
    @SerializedName("is_returnable")
    @Expose
    val isReturnable: Boolean,
    @SerializedName("is_exchangable")
    @Expose
    val isExchangeable: Boolean,
    )