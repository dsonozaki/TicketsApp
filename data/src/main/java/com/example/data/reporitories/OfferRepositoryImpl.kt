package com.example.data.reporitories

import com.example.data.db.dao.OffersDAO
import com.example.data.dto.OffersResponseDto
import com.example.data.mappers.OfferMapper
import com.example.data.network.OffersApiService
import com.example.data.network.safeApiCall
import com.sonozaki.ticketsapp.domain.entities.Offer
import com.sonozaki.ticketsapp.domain.entities.RequestResult
import com.sonozaki.ticketsapp.domain.repositories.OfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class OfferRepositoryImpl(
    private val offersApiService: OffersApiService,
    private val offersDAO: OffersDAO,
    private val offerMapper: OfferMapper,
    //flow of api request errors
    private val errorFlow: MutableSharedFlow<RequestResult.Error<List<Offer>>>
) : OfferRepository {
    //Cache validity flag. Validates automatically after first data loading.
    private var loaded: Boolean = false


    override suspend fun updateData() {
        if (!loaded) {
            offersDAO.clear()
            val offers = safeApiCall { offersApiService.getOffers() }
            when (offers) {
                is RequestResult.Data<OffersResponseDto> -> {
                    offersDAO.insert(offerMapper.mapDtoToDbList(offers.data))
                    loaded = true
                }

                is RequestResult.Error<OffersResponseDto> -> errorFlow.emit(
                    RequestResult.Error(
                        offers.errorText
                    )
                )
            }
        }
    }

    override fun getOffers(): Flow<RequestResult<List<Offer>>> {
        //return merged flows of data from database and data from error flow
        return merge(
            offersDAO.getOffers().filter { it.isNotEmpty() }
                .map { RequestResult.Data(offerMapper.mapDbToDomainList(it)) },
            errorFlow
        )
    }
}