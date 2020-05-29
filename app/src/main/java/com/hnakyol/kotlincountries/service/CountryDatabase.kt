package com.hnakyol.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hnakyol.kotlincountries.model.Country

//@Database(entities = arrayOf(Country::class.java,Musician::class.java)) -> can insert more than one class in here

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase : RoomDatabase(){

    abstract fun countryDao(): CountryDao

    //Singleton

    companion object{

        @Volatile
        private var instance: CountryDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "countrydatabase"
        ).build()

    }
}