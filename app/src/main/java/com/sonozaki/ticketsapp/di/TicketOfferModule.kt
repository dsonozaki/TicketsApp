package com.sonozaki.ticketsapp.di

import com.sonozaki.ticketsapp.domain.entities.Travel
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.utils.daysSinceUnixEpoch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module
class TicketOfferModule {
    @Provides
    fun provideTravelFlow(): MutableSharedFlow<Travel> = MutableSharedFlow()

    @Provides
    @Named("date")
    fun provideDateFlow(): MutableStateFlow<Long> = MutableStateFlow(daysSinceUnixEpoch())

    @Provides
    fun provideTravelParams(): TravelParams? = null

    @Provides
    fun provideEventFlow(): MutableStateFlow<Boolean> = MutableStateFlow(false)

    @Provides
    fun provideDataValidFlow(): MutableSharedFlow<Boolean> = MutableSharedFlow()
}