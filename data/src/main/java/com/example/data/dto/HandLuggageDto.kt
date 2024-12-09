package com.example.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HandLuggageDto(
    @SerializedName("has_hand_luggage")
    @Expose
    val hasHandLuggage: Boolean,
    @SerializedName("size")
    @Expose
    val size: String?
)