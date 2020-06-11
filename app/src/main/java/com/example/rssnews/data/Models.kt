package com.example.rssnews.data

import java.time.LocalDateTime

data class Article(
    val id: Int? = null,
    val title: String,
    val link: String,
    val creator: String,

    val categories: List<String>,
    val description: String,

    val date: LocalDateTime
)





