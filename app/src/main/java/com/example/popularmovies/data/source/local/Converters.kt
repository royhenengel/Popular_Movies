package com.example.popularmovies.data.source.local

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun saveDate(value: Date?): Long? = value?.time

    @TypeConverter
    fun restoreDate(value: Long?): Date? = if (value != null) Date(value) else null
}