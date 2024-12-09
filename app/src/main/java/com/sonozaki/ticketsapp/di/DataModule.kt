package com.sonozaki.ticketsapp.di

import android.content.Context
import com.example.data.db.TicketsAppDatabase
import com.example.data.db.dao.OffersDAO
import com.example.data.db.dao.TicketOffersDAO
import com.example.data.db.dao.TicketsDAO
import com.example.data.mappers.OfferMapper
import com.example.data.mappers.TicketMapper
import com.example.data.mappers.TicketOfferMapper
import com.example.data.network.ApiFactory
import com.example.data.network.OfferTicketsApiService
import com.example.data.network.OffersApiService
import com.example.data.network.TicketsApiService
import com.example.data.serializers.TravelParamsSerializer
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = ApiFactory.createRetrofit()

    @Provides
    @Singleton
    fun provideTicketsApiService(retrofit: Retrofit): TicketsApiService = ApiFactory.createTicketsApiService(retrofit)

    @Provides
    @Singleton
    fun provideOfferApiService(retrofit: Retrofit): OffersApiService = ApiFactory.createOffersApiService(retrofit)

    @Provides
    @Singleton
    fun provideTicketOfferApiService(retrofit: Retrofit): OfferTicketsApiService = ApiFactory.createOfferTicketsApiService(retrofit)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): TicketsAppDatabase =
        TicketsAppDatabase.create(context)

    @Provides
    @Singleton
    fun provideTicketDao(database: TicketsAppDatabase): TicketsDAO = database.ticketsDao()

    @Provides
    @Singleton
    fun provideOffersDao(database: TicketsAppDatabase): OffersDAO = database.offersDao()

    @Provides
    @Singleton
    fun provideTicketOffersDao(database: TicketsAppDatabase): TicketOffersDAO = database.ticketOffersDao()

    @Provides
    @Singleton
    fun provideTicketOffersErrorFlow(): MutableSharedFlow<RequestResult.Error<List<TicketOffer>>> =
        MutableSharedFlow()

    @Provides
    @Singleton
    fun provideTicketErrorFlow(): MutableSharedFlow<RequestResult.Error<List<Ticket>>> =
        MutableSharedFlow()

    @Provides
    @Singleton
    fun provideOfferErrorFlow(): MutableSharedFlow<RequestResult.Error<List<Offer>>> =
        MutableSharedFlow()
}