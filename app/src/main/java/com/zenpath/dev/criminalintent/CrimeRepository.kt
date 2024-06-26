package com.zenpath.dev.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.zenpath.dev.criminalintent.database.CrimeDatabase
import java.lang.IllegalStateException
import java.util.UUID

class CrimeRepository private constructor(context: Context){

    private val database : CrimeDatabase = Room.databaseBuilder(
        /* context = */ context.applicationContext,
        /* klass = */ CrimeDatabase::class.java,
        /* name = */ DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object{
        private const val DATABASE_NAME = "crime-database"

        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}