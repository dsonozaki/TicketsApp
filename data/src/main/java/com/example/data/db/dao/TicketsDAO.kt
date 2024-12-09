package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.TicketDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tickets: List<TicketDb>)

    @Query("SELECT * FROM tickets")
    fun getTickets(): Flow<List<TicketDb>>

    @Query("DELETE FROM tickets")
    suspend fun clear()
}