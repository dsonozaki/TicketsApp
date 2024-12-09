package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.OfferDb
import kotlinx.coroutines.flow.Flow

@Dao
interface OffersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tickets: List<OfferDb>)

    @Query("SELECT * FROM offers")
    fun getOffers(): Flow<List<OfferDb>>

    @Query("DELETE FROM offers")
    suspend fun clear()
}