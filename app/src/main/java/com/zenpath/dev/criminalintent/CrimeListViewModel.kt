package com.zenpath.dev.criminalintent

import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.util.Date

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimesListLiveData = crimeRepository.getCrimes()
    fun convertDateToString(date: Date): String {
        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}