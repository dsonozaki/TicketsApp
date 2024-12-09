package com.sonozaki.ticketsapp.di

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.sonozaki.ticketsapp.adapter.offer.getOfferDelegate
import com.sonozaki.ticketsapp.adapter.ticket.getTicketDelegate
import com.sonozaki.ticketsapp.adapter.ticket.getTicketDelegateWithBadge
import com.sonozaki.ticketsapp.adapter.ticketOffer.getTicketOfferDelegate
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.Ticket
import com.sonozaki.ticketsapp.domain.entities.TicketOffer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class AdapterModule {

    @Provides
    fun provideOffersDelegate(): AdapterDelegate<List<Offer>> = getOfferDelegate()

    @Provides
    fun provideTicketOffersDelegate(): AdapterDelegate<List<TicketOffer>> = getTicketOfferDelegate()

    @Provides
    @Named("no badge")
    fun provideTicketDelegate(): AdapterDelegate<List<Ticket>> = getTicketDelegate()

    @Provides
    @Named("badge")
    fun provideTicketDelegateWithBadge(): AdapterDelegate<List<Ticket>> = getTicketDelegateWithBadge()
}