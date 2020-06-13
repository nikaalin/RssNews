package com.example.rssnews.ui.main.article

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