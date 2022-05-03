package com.rmoralf.xkcd.domain.repository

import com.rmoralf.xkcd.domain.model.Comic
import com.rmoralf.xkcd.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    suspend fun getLatestComic(): Flow<Response<Comic>>
    suspend fun getComic(comicId: Int): Flow<Response<Comic>>
}