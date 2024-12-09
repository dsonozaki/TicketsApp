package com.sonozaki.ticketsapp.di

import android.content.Context
import com.example.data.db.dao.OffersDAO
import com.example.data.db.dao.TicketOffersDAO
import com.example.data.db.dao.TicketsDAO
import com.example.data.mappers.OfferMapper
import com.example.data.mappers.TicketMapper
import com.example.data.mappers.TicketOfferMapper
import com.example.data.mappers.TravelParamsMapper
import com.example.data.network.OfferTicketsApiService
import com.example.data.network.OffersApiService
import com.example.data.network.TicketsApiService
import com.example.data.reporitories.CachedParamsRepositoryImpl
import com.example.data.reporitories.OfferRepositoryImpl
import com.example.data.reporitories.StartCityRepositoryImpl
import com.example.data.reporitories.TicketOfferRepositoryImpl
import com.example.data.reporitories.TicketRepositoryImpl
import com.example.data.serializers.TravelParamsSerializer
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import com.sonozaki.ticketsapp.domain.repositories.CachedParamsRepository
import com.sonozaki.ticketsapp.domain.repositories.OfferRepository
import com.sonozaki.ticketsapp.domain.repositories.StartCityRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketOfferRepository
import com.sonozaki.ticketsapp.domain.repositories.TicketRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTicketsRepository(ticketRepositoryImpl: TicketRepositoryImpl): TicketRepository

    @Binds
    @Singleton
    abstract fun bindOffersRepository(offersRepositoryImpl: OfferRepositoryImpl): OfferRepository

    @Binds
    @Singleton
    abstract fun bindTicketOfferRepository(ticketOfferRepositoryImpl: TicketOfferRepositoryImpl): TicketOfferRepository

    @Binds
    @Singleton
    abstract fun bindCachedParamsRepository(cachedParamsRepositoryImpl: CachedParamsRepositoryImpl): CachedParamsRepository

    @Binds
    @Singleton
    abstract fun bindStartCityRepository(startCityRepositoryImpl: StartCityRepositoryImpl): StartCityRepository

    companion object {
        @Provides
        @Singleton
        fun provideOffersRepository(offersApiService: OffersApiService, offersDAO: OffersDAO, offerMapper: OfferMapper, errorFlow: MutableSharedFlow<RequestResult.Error<List<Offer>>>): OfferRepositoryImpl = OfferRepositoryImpl(offersApiService,offersDAO, offerMapper, errorFlow)

        @Provides
        @Singleton
        fun provideTicketsRepository(ticketsApiService: TicketsApiService, ticketsDAO: TicketsDAO, ticketsMapper: TicketMapper, errorFlow: MutableSharedFlow<RequestResult.Error<List<Ticket>>>): TicketRepositoryImpl =
            TicketRepositoryImpl(ticketsApiService, ticketsDAO, ticketsMapper, errorFlow)

        @Provides
        @Singleton
        fun provideTicketOffersRepository(ticketOffersApiService: OfferTicketsApiService, ticketOffersDAO: TicketOffersDAO, ticketOfferMapper: TicketOfferMapper, errorFlow: MutableSharedFlow<RequestResult.Error<List<TicketOffer>>>): TicketOfferRepositoryImpl =
            TicketOfferRepositoryImpl(ticketOffersApiService,ticketOffersDAO,ticketOfferMapper, errorFlow)

        @Provides
        @Singleton
        fun provideStartCityRepository(@ApplicationContext context: Context): StartCityRepositoryImpl = StartCityRepositoryImpl(context)

        @Provides
        @Singleton
        fun provideCachedParamsRepository(@ApplicationContext context: Context, travelParamsSerializer: TravelParamsSerializer, travelParamsMapper: TravelParamsMapper): CachedParamsRepositoryImpl =
            CachedParamsRepositoryImpl(context,travelParamsSerializer,travelParamsMapper)

    }
}