package com.sonozaki.ticketsapp.adapter.offer

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.sonozaki.ticketsapp.domain.entities.Offer
import javax.inject.Inject

class AsyncListOfferDifferAdapter @Inject constructor(
    diffCallback: OfferItemCallback,
    offerDelegate: AdapterDelegate<List<Offer>>
) : AsyncListDifferDelegationAdapter<Offer>(diffCallback) {
    init {
        delegatesManager.addDelegate(offerDelegate)
    }
}