package com.rmoralf.xkcd.domain.usecases

import com.rmoralf.xkcd.domain.repository.ComicsRepository

class GetLatestComic(
    private val repository: ComicsRepository
) {
    suspend operator fun invoke() = repository.getLatestComic()
}