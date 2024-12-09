package com.sonozaki.ticketsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonozaki.ticketsapp.domain.entities.TravelParams
import com.sonozaki.ticketsapp.domain.usecases.GetTicketsUseCase
import com.sonozaki.ticketsapp.domain.usecases.UpdateTicketsUseCase
import com.sonozaki.ticketsapp.states.TicketState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    getTicketsUseCase: GetTicketsUseCase,
    private val updateTicketsUseCase: UpdateTicketsUseCase,
    private val _travelParamsFlow: MutableStateFlow<TravelParams>,
    private val dataValidFlow: MutableSharedFlow<Boolean>,
    sharingStarted: SharingStarted
) : ViewModel() {

    val travelParamsFlow = _travelParamsFlow.asSharedFlow()

    val ticketDataFlow = combine(getTicketsUseCase(), dataValidFlow) { result, valid ->
        if (valid) {
            TicketState.Data(result)
        } else {
            TicketState.Loading
        }
    }.stateIn(
        viewModelScope,
        sharingStarted,
        TicketState.Loading
    )

    fun updateTravelParams(travelParams: TravelParams) {
        viewModelScope.launch {
            _travelParamsFlow.emit(travelParams)
            dataValidFlow.emit(false)
            updateTicketsUseCase(travelParams)
            dataValidFlow.emit(true)
        }
    }

}