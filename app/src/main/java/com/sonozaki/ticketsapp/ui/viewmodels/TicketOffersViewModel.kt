package com.sonozaki.ticketsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.entities.Travel
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.usecases.GetTicketOffersUseCase
import com.sonozaki.ticketsapp.domain.usecases.UpdateTicketOffersUseCase
import com.sonozaki.ticketsapp.states.TicketOfferState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TicketOffersViewModel @Inject constructor(
    getTicketOffersUseCase: GetTicketOffersUseCase,
    private val updateTicketOffersUseCase: UpdateTicketOffersUseCase,
    @Named("date")
    private val _dateFlow: MutableStateFlow<Long>,
    private val _travelsFlow: MutableSharedFlow<Travel>,
    private val dataValidFlow: MutableSharedFlow<Boolean>,
    private var _travelParams: TravelParams?,
    sharingStarted: SharingStarted
) : ViewModel() {

    init {
        observeData()
    }

    val dateFlow = _dateFlow.asStateFlow()

    val travelParams get() = _travelParams

    var textSetupEnded = false

    val ticketOffersFlow = combine(getTicketOffersUseCase(), dataValidFlow) { result, valid ->
        if (valid) {
            TicketOfferState.Data(result)
        } else {
            TicketOfferState.Loading
        }
    }.stateIn(
        viewModelScope,
        sharingStarted,
        TicketOfferState.Loading
    )

    private fun observeData() {
        viewModelScope.launch {
            combine(_travelsFlow,_dateFlow) {
                    travel, date ->
                TravelParams(travel.startPoint,travel.endPoint,date)
            }.collect {
                _travelParams = it
                updateTicketOffersUseCase(it)
                dataValidFlow.emit(true)
            }
        }
    }

    fun updateDate(date: Long) {
        viewModelScope.launch {
            dataValidFlow.emit(false)
            _dateFlow.emit(date)
        }
    }

    fun updateTravelPoints(travelPoints: Travel) {
        viewModelScope.launch {
            dataValidFlow.emit(false)
            _travelsFlow.emit(travelPoints)
        }
    }
}