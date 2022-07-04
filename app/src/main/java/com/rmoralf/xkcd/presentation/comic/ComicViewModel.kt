package com.rmoralf.xkcd.presentation.comic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmoralf.xkcd.domain.model.Comic
import com.rmoralf.xkcd.domain.model.Response
import com.rmoralf.xkcd.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = mutableStateOf<Response<Comic>>(Response.Loading)
    val state: State<Response<Comic>> = _state

    var openDialogState = mutableStateOf(false)

    var latestComicId = 0
    var currentComicId = -1
    var currentImageUrl = ""

    init {
        getLatestComic()
    }

    fun getLatestComic() {
        if (currentComicId != latestComicId) {
            viewModelScope.launch {
                useCases.getLatestComic().collect { response ->
                    _state.value = response

                    if (response is Response.Success)
                        latestComicId = response.data.num
                }
            }
        }
    }

    fun findComic(comicId: Int) = getComic(comicId)
    fun getFirstComic() = getComic(1)
    fun getPreviousComic() = getComic(currentComicId - 1)
    fun getRandomComic() = getComic((1..latestComicId).random())
    fun getNextComic() = getComic(currentComicId + 1)

    private fun getComic(comicId: Int) {
        if (comicId != currentComicId && comicId in 1..latestComicId) {
            viewModelScope.launch {
                useCases.getComic(comicId).collect { response ->
                    _state.value = response
                }
            }
        }
    }

    fun isComicNumberValid(comicId: Int) = comicId in 1..latestComicId
}