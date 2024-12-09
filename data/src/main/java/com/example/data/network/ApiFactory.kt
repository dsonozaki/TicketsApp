package com.example.data.network

import com.example.data.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    fun createRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createTicketsApiService(retrofit: Retrofit): TicketsApiService {
        return retrofit.create(TicketsApiService::class.java)
    }

    fun createOffersApiService(retrofit: Retrofit): OffersApiService {
        return retrofit.create(OffersApiService::class.java)
    }

    fun createOfferTicketsApiService(retrofit: Retrofit): OfferTicketsApiService {
        return retrofit.create(OfferTicketsApiService::class.java)
    }
}