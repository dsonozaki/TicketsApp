package com.sonozaki.ticketsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.SharingStarted

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {

    @Provides
    fun provideSharingStarted() : SharingStarted = SharingStarted.WhileSubscribed(TIMEOUT)

    companion object {
        private const val TIMEOUT = 5000L
    }
}