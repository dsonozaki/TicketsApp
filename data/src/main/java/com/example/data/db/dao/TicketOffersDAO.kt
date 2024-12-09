package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.TicketOfferDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketOffersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tickets: List<TicketOfferDb>)

    @Query("SELECT * FROM ticket_offers")
    fun getTicketOffers(): Flow<List<TicketOfferDb>>

    @Query("DELETE FROM ticket_offers")
    suspend fun clear()
}