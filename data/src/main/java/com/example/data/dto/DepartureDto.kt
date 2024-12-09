package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DepartureDto(
    @SerializedName("airport")
    @Expose
    val airport: String,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("town")
    @Expose
    val town: String
)