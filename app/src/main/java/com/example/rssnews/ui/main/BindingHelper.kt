package com.example.rssnews.ui.main

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
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

@BindingAdapter("html")
fun TextView.setHtml(htmlBody: String) {
    this.text = HtmlCompat.fromHtml(htmlBody, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST)
    this.movementMethod = LinkMovementMethod.getInstance()
}



