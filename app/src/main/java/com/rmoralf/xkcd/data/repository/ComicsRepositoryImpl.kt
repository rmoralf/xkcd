package com.rmoralf.xkcd.data.repository

import com.rmoralf.xkcd.data.network.remote.ComicsService
import com.rmoralf.xkcd.domain.model.Response.*
import com.rmoralf.xkcd.domain.model.toDomain
import com.rmoralf.xkcd.domain.repository.ComicsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class ComicsRepositoryImpl @Inject constructor(
    private val service: ComicsService
) : ComicsRepository {
    override suspend fun getLatestComic() = flow {
        try {
            emit(Loading)
            val comic = service
                .getLatestComic()
                .toDomain()
            emit(Success(comic))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    override suspend fun getComic(comicId: Int) = flow {
        try {
            emit(Loading)
            val comic = service
                .getComic(comicId = comicId)
                .toDomain()
            emit(Success(comic))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }
}