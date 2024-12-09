package com.sonozaki.ticketsapp.di

import com.sonozaki.ticketsapp.utils.CyrillicInputFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class FragmentsModule {

    @Provides
    fun provideInputFilter(): CyrillicInputFilter {
        return CyrillicInputFilter()
    }
}