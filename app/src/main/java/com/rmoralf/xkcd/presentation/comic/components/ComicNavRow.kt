package com.rmoralf.xkcd.presentation.comic.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmoralf.xkcd.R
import com.rmoralf.xkcd.presentation.comic.ComicViewModel

@Preview
@Composable
fun ComicNavRow(
    viewModel: ComicViewModel = hiltViewModel()
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        IconButton(onClick = { viewModel.getFirstComic() }) {
            Icon(
                painterResource(id = R.drawable.outline_first_page_24),
                stringResource(id = R.string.first_comic)
            )

        }
        IconButton(onClick = { viewModel.getPreviousComic() }) {
            Icon(
                painterResource(id = R.drawable.baseline_chevron_left_24),
                stringResource(id = R.string.previous)
            )
        }
        IconButton(onClick = { viewModel.getRandomComic() }) {
            Icon(
                painterResource(id = R.drawable.baseline_shuffle_24),
                stringResource(id = R.string.random)
            )
        }
        IconButton(onClick = { viewModel.getNextComic() }) {
            Icon(
                painterResource(id = R.drawable.baseline_chevron_right_24),
                stringResource(id = R.string.next)
            )
        }
        IconButton(onClick = { viewModel.getLatestComic() }) {
            Icon(
                painterResource(id = R.drawable.baseline_last_page_24),
                stringResource(id = R.string.latest_comic)
            )
        }
    }

}