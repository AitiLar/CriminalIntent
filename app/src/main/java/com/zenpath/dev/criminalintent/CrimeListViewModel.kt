package com.zenpath.dev.criminalintent

import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.util.Date

class CrimeListViewModel : ViewModel() {

    val  crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100){
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
            if(i % 2 != 0){
                crime.requiresPolice = true
            }
        }
    }

    fun convertDateToString(date: Date): String {
        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}