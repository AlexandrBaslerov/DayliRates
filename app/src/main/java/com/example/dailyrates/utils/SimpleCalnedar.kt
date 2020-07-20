package com.example.dailyrates.utils

import com.example.dailyrates.BuildConfig
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*

val simpleCalendarModule = module {
    single { SimpleCalendar(get()) }
    single { SimpleDateFormat(BuildConfig.DATE_PATTERN_FOR_API, Locale.getDefault()) }
}

class SimpleCalendar(private val simpleDateFormat: SimpleDateFormat) {
    fun today(): String = simpleDateFormat.format(Calendar.getInstance().time)
    fun tomorrow(): String =
        simpleDateFormat.format(Calendar.getInstance().apply { add(Calendar.DATE, 1) }.time)

    fun yesterday(): String =
        simpleDateFormat.format(Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time)

    fun getDefaultDailyDates(): List<String> = listOf(
        today(), tomorrow(), yesterday()
    )
}