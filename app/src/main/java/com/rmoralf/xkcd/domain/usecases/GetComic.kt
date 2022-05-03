package com.rmoralf.xkcd.domain.usecases

import com.rmoralf.xkcd.domain.repository.ComicsRepository

class GetComic(
    private val repository: ComicsRepository
) {
    suspend operator fun invoke(comicId: Int) = repository.getComic(comicId = comicId)
}