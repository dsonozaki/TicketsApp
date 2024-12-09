package com.sonozaki.ticketsapp.di

import com.sonozaki.ticketsapp.domain.entities.TravelParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.MutableStateFlow

@InstallIn(ViewModelComponent::class)
@Module
class TicketModule {
    @Provides
    fun provideTicketParamsFlow():  MutableStateFlow<TravelParams> = MutableStateFlow(TravelParams())

}