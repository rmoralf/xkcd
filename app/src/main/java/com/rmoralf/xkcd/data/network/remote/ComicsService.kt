package com.rmoralf.xkcd.data.network.remote

import com.rmoralf.xkcd.data.network.entities.ApiComic
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsService {
    @GET("/info.0.json")
    suspend fun getLatestComic(
    ): ApiComic

    @GET("/{comicId}/info.0.json")
    suspend fun getComic(
        @Path("comicId") comicId: Int
    ): ApiComic
}