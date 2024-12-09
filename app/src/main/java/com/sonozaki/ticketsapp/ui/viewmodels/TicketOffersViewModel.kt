package com.sonozaki.ticketsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TicketOffersViewModel @Inject constructor(
    getTicketOffersUseCase: GetTicketOffersUseCase,
    private val updateTicketOffersUseCase: UpdateTicketOffersUseCase,
    //Flow of travel dates. Initialized with present day, present time.
    @Named("date")
    private val _dateFlow: MutableStateFlow<Long>,
    //Flow of travel destinations
    private val _travelsFlow: MutableSharedFlow<Travel>,
    //A flow that stores information about the validity of the data. Data invalidates when new data loading starts.
    private val dataValidFlow: MutableSharedFlow<Boolean>,
    //Parameters of travel for passing to next screen
    private var _travelParams: TravelParams?,
    sharingStarted: SharingStarted
) : ViewModel() {

    init {
        observeData()
    }

    val dateFlow = _dateFlow.asStateFlow()

    val travelParams get() = _travelParams

    //variable for avoiding unnecessary text setup
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
            combine(_travelsFlow, _dateFlow) { travel, date ->
                TravelParams(travel.startPoint, travel.endPoint, date)
            }.collect {
                //if travel destinations or departure date changes, save changes to variable, update data and validate data
                _travelParams = it
                updateTicketOffersUseCase(it)
                dataValidFlow.emit(true)
            }
        }
    }

    /**
     * Change date and invalidate data
     */
    fun updateDate(date: Long) {
        viewModelScope.launch {
            dataValidFlow.emit(false)
            _dateFlow.emit(date)
        }
    }

    /**
     * Change travel destinations and invalidate data
     */
    fun updateTravelPoints(travelPoints: Travel) {
        viewModelScope.launch {
            dataValidFlow.emit(false)
            _travelsFlow.emit(travelPoints)
        }
    }
}