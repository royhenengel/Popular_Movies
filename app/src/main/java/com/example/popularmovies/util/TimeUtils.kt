package com.example.popularmovies.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val PATTERN_API_DATE = "yyyy-MM-dd"

fun dateFromString(dateString: String?, format: String = PATTERN_API_DATE): Date? {

    return if (dateString != null) {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())

        try {
            dateFormat.parse(dateString)
        }
        catch (e: ParseException){
            e.printStackTrace()
            null
        }

    }
    else null

}

fun dateAsString(date: Date?, format: String = PATTERN_API_DATE) : String? {

    return if (date != null){
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.format(date)
    }
    else null
}