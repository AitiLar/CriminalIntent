package com.zenpath.dev.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.Format
import java.util.Date
import java.util.UUID
@Entity
data class Crime(@PrimaryKey val  id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false,
                 var requiresPolice: Boolean = false)