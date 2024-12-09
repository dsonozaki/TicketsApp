package com.sonozaki.ticketsapp.di

import com.sonozaki.ticketsapp.states.OfferState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module
class OfferModule {

    @Provides
    @Named("startCityFlow")
    fun provideStartCityFlow(): MutableStateFlow<String?> = MutableStateFlow(null)

    @Provides
    fun provideOfferStateFlow(): MutableStateFlow<OfferState> = MutableStateFlow(OfferState.Loading)

}