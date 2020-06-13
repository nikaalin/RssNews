package com.example.rssnews.ui.main

import androidx.databinding.BindingConversion
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@BindingConversion
fun convertDateTimeToString(dateTime: LocalDateTime): String =
    dateTime.format(
        DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.MEDIUM,
            FormatStyle.SHORT
        )
    )

@BindingConversion
fun convertListToString(list: List<String>): String =
    list.reduce { total, next -> "$total, $next" }.trimEnd(',', ' ')