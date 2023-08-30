package com.rmoralf.xkcd.domain.model

import com.rmoralf.xkcd.data.network.entities.ApiComic

data class Comic(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    val num: Int,
    val safeTitle: String,
    val title: String,
    val transcript: String,
    val year: String
)

fun ApiComic.toDomain() = Comic(
    alt = alt,
    day = day,
    img = img,
    link = link,
    month = month,
    news = news,
    num = num,
    safeTitle = safe_title,
    title = title,
    transcript = transcript,
    year = year
)