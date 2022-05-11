package com.rmoralf.xkcd.domain.model

import com.rmoralf.xkcd.data.network.entities.ApiComic
import junit.framework.Assert.assertEquals
import org.junit.Test

class ComicTest {

    @Test
    fun `ApiComic translates into domain (Comic class)`(){
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

        val comic = apiComic.toDomain()

        assertEquals(apiComic.alt, comic.alt)
        assertEquals(apiComic.day, comic.day)
        assertEquals(apiComic.img, comic.img)
        assertEquals(apiComic.link, comic.link)
        assertEquals(apiComic.month, comic.month)
        assertEquals(apiComic.news, comic.news)
        assertEquals(apiComic.num, comic.num)
        assertEquals(apiComic.safe_title, comic.safe_title)
        assertEquals(apiComic.title, comic.title)
        assertEquals(apiComic.transcript, comic.transcript)
        assertEquals(apiComic.year, comic.year)
    }
}