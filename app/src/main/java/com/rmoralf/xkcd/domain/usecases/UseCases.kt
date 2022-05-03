package com.rmoralf.xkcd.domain.usecases

data class UseCases(
    //Retrofit2
    val getLatestComic: GetLatestComic,
    val getComic: GetComic
)