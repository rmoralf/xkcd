package com.rmoralf.xkcd.domain.model

import com.google.common.truth.Truth.assertThat
import com.rmoralf.xkcd.data.network.entities.ApiComic
import junit.framework.Assert.assertEquals
import org.junit.Test

class ComicTest {

    @Test
    fun `ApiComic translates into domain Comic class`(){
        val apiComic = ApiComic(
            alt = "AAAAAAA",
            day = "01",
            img = "img",
            link = "link",
            month = "02",
            news = "news",
            num = 123,
            safe_title = "safe_title",
            title = "title",
            transcript = "transcript",
            year = "2022"
        )

        val domainComic = Comic(
            alt = "AAAAAAA",
            day = "01",
            img = "img",
            link = "link",
            month = "02",
            news = "news",
            num = 123,
            safe_title = "safe_title",
            title = "title",
            transcript = "transcript",
            year = "2022"
        )

        val convertedComic = apiComic.toDomain()

        assertThat(convertedComic).isEqualTo(domainComic)
    }
}