package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.db.dao.OffersDAO
import com.example.data.db.dao.TicketOffersDAO
import com.example.data.db.dao.TicketsDAO
import com.example.data.db.entities.OfferDb
import com.example.data.db.entities.TicketDb
import com.example.data.db.entities.TicketOfferDb

@Database(entities = [TicketDb::class, OfferDb::class, TicketOfferDb::class], version = 1)
abstract class TicketsAppDatabase : RoomDatabase() {
    abstract fun ticketsDao(): TicketsDAO
    abstract fun offersDao(): OffersDAO
    abstract fun ticketOffersDao(): TicketOffersDAO

    companion object {
        private const val DB_NAME = "app_database"
        fun create(context: Context): TicketsAppDatabase {
            return Room.databaseBuilder(
                context,
                TicketsAppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}