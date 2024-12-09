package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TicketsResponseDto(
    @SerializedName("tickets")
    @Expose
    val tickets: List<TicketDto>
)