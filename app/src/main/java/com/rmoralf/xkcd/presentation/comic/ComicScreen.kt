package com.rmoralf.xkcd.presentation.comic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmoralf.xkcd.core.utils.Utils.Companion.printError
import com.rmoralf.xkcd.domain.model.Response.*
import com.rmoralf.xkcd.presentation.comic.components.ComicDetails
import com.rmoralf.xkcd.presentation.comic.components.ComicNavRow
import com.rmoralf.xkcd.presentation.comic.components.ComicSearchDialog
import com.rmoralf.xkcd.presentation.comic.components.ComicTopBar
import com.rmoralf.xkcd.presentation.components.ProgressBar

@Composable
fun ComicScreen(
    viewModel: ComicViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ComicTopBar()
        }
    ) {
        if (viewModel.openDialogState.value) {
            ComicSearchDialog()
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (val comicResponse = viewModel.state.value) {
                    is Loading -> ProgressBar()
                    is Success -> {
                        val comic = comicResponse.data
                        viewModel.currentComicId = comic.num
                        viewModel.currentImageUrl = comic.img
                        ComicDetails(comic = comic)
                    }
                    is Error -> printError(comicResponse.message)
                }
            }

            ComicNavRow()
        }
    }
}