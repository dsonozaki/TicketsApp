package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TicketOffersResponseDto(
    @SerializedName("tickets_offers")
    @Expose
    val ticketsOffers: List<TicketOfferDto>,
)