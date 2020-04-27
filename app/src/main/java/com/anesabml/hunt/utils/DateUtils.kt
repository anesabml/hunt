package com.anesabml.hunt.utils

import java.text.SimpleDateFormat
import java.util.Calendar

object DateUtils {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

    fun stringDateToCalendar(date: String): Calendar =
        Calendar.getInstance().apply {
            timeInMillis =
                dateFormatter.parse(date)!!.time
        }
}
