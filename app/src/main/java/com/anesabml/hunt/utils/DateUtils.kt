package com.anesabml.hunt.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {

    private var dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun stringDateToCalendar(date: String): Calendar =
        Calendar.getInstance().apply {
            timeInMillis =
                dateFormatter.parse(date)!!.time
        }
}
