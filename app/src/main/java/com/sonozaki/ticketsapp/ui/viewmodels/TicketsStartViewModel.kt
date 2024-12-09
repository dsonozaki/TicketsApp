package com.sonozaki.ticketsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonozaki.ticketsapp.domain.usecases.GetOffersUseCase
import com.sonozaki.ticketsapp.domain.usecases.GetStartCityUseCase
import com.sonozaki.ticketsapp.domain.usecases.SetStartCityUseCase
import com.sonozaki.ticketsapp.domain.usecases.UpdateOffersUseCase
import com.sonozaki.ticketsapp.states.OfferState
import com.sonozaki.ticketsapp.states.TicketOfferState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TicketsStartViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val getStartCityUseCase: GetStartCityUseCase,
    private val setStartCityUseCase: SetStartCityUseCase,
    private val updateOffersUseCase: UpdateOffersUseCase,
    @Named("startCityFlow") private val startCityFlow: MutableStateFlow<String?>,
    private val dataValidFlow: MutableSharedFlow<Boolean>,
    sharingStarted: SharingStarted
): ViewModel() {


    init {
        loadOffers()
    }

    val offersFlow = combine(getOffersUseCase(),dataValidFlow) {
            result, valid ->
        if (valid) {
            OfferState.Data(result)
        } else {
            OfferState.Loading
        }
    }.stateIn(
        viewModelScope,
        sharingStarted,
        OfferState.Loading
    )

    val startCity = startCityFlow.asStateFlow()

    fun loadOffers() {
        viewModelScope.launch {
            dataValidFlow.emit(false)
            updateOffersUseCase()
            dataValidFlow.emit(true)
        }
    }

    fun loadStartCity() {
        viewModelScope.launch {
            startCityFlow.emit(getStartCityUseCase().first())
        }
    }

    fun updateInitialCity(city: String) {
        viewModelScope.launch {
            setStartCityUseCase(city)
        }
    }
}