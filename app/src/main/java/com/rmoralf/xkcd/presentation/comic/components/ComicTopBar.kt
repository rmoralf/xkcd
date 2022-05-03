package com.rmoralf.xkcd.presentation.comic.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmoralf.xkcd.R
import com.rmoralf.xkcd.core.utils.Utils.Companion.openExplanation
import com.rmoralf.xkcd.core.utils.Utils.Companion.shareImage
import com.rmoralf.xkcd.presentation.comic.ComicViewModel

@Preview
@Composable
fun ComicTopBar(
    viewModel: ComicViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val localUriHandler = LocalUriHandler.current

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.topbar_title))
        },
        actions = {
            IconButton(onClick = {
                viewModel.openDialogState.value = true
            }) {
                Icon(Icons.Default.Search, stringResource(id = R.string.search))
            }
            IconButton(onClick = {
                shareImage(context, viewModel.currentImageUrl, viewModel.currentComicId)
            }) {
                Icon(Icons.Default.Share, stringResource(id = R.string.search))
            }
            IconButton(onClick = {
                openExplanation(localUriHandler, viewModel.currentComicId)
            }) {
                Icon(Icons.Default.Info, stringResource(id = R.string.search))
            }
        }
    )
}